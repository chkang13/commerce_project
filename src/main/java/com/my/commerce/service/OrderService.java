package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.domain.*;
import com.my.commerce.dto.Order.*;
import com.my.commerce.repository.MemberRepository;
import com.my.commerce.repository.OrderProductRepository;
import com.my.commerce.repository.OrderRepository;
import com.my.commerce.repository.ProductRepository;
import com.my.commerce.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.my.commerce.common.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 추가 API
     */
    @Transactional
    public String postOrder(Principal principal, PostOrderReqDTO postOrderReqDTO) {
        // 주문 만들기
        Optional<Member> member = memberRepository.findById(Long.valueOf(principal.getName()));

        Orders orders = postOrderReqDTO.toEntity(member.get(), postOrderReqDTO.getTotal(), OrderStatus.ORDERED);
        orderRepository.save(orders);

        // 주문 상품 만들기
        for (PostOrderProductReqDTO postOrderProductReqDTO : postOrderReqDTO.getOrderProducts()) {
            Product product = productRepository.findById(postOrderProductReqDTO.getProductId()).orElseThrow(() -> new BaseException(PRODUCT_INVALID_ID));
            if (product.getStatus() == 1) {
                OrderProduct orderProduct = postOrderProductReqDTO.toEntity(orders, product, postOrderProductReqDTO);
                orderProductRepository.save(orderProduct);

                // 재고 줄이기
                product.updateStock(product.getStock() - postOrderProductReqDTO.getCount());
            }
            else {
                throw new BaseException(PRODUCT_INVALID_ID);
            }
        }

        return "주문이 완료되었습니다.";
    }

    /**
     * 주문 리스트 조회 API
     */
    public List<GetOrdersResDTO> getOrderList(Principal principal) {
        List<GetOrdersResDTO> getOrdersResDTOS = new ArrayList<>();
        List<Orders> orders = orderRepository.findByMemberId(Long.parseLong(principal.getName()));

        for (Orders order : orders) {
            getOrdersResDTOS.add(GetOrdersResDTO.toDTO(order));
        }

        return  getOrdersResDTOS;
    }

    /**
     * 주문 조회 API
     */
    public GetOrderResDTO getOrders(Principal principal, Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));
        GetOrderResDTO getOrderResDTO;

        if (order.getMember().getId() == Long.parseLong(principal.getName())) {
            List<OrderProduct> orderProducts = order.getOrderProducts();
            List<GetOrderProductResDTO> getOrderProductResDTOS = new ArrayList<>();

            for (OrderProduct orderProduct : orderProducts) {
                getOrderProductResDTOS.add(GetOrderProductResDTO.toDTO(orderProduct, orderProduct.getProduct()));
            }

            getOrderResDTO = GetOrderResDTO.toDTO(order, getOrderProductResDTOS);
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return getOrderResDTO;
    }

    /**
     * 주문 상태 변경 API
     */
    @Transactional
    public void updateStatus() {
        List<Orders> orders = orderRepository.findAll();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis()); // 현재 시간
        LocalDate currentDate = currentTime.toLocalDateTime().toLocalDate();

        for (Orders order : orders) {
            LocalDate orderDate = order.getCreatedAt().toLocalDateTime().toLocalDate();

            // 날짜 차이
            long daysDifference = orderDate.until(currentDate, ChronoUnit.DAYS);

            if (daysDifference == 1) {
                order.updateOrderStatus(OrderStatus.DELIVER);
            } else if (daysDifference == 2) {
                order.updateOrderStatus(OrderStatus.DELIVERED);
            }
        }
    }

    /**
     * 주문 취소 API
     */
    @Transactional
    public String cancelOrder (Principal principal, Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));

        if (order.getMember().getId() == Long.parseLong(principal.getName())) {
            // 배송중 이전까지만
            if (order.getStatus().equals(OrderStatus.ORDERED)) {
                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    // 재고 늘리기
                    orderProduct.getProduct().updateStock(orderProduct.getProduct().getStock() + orderProduct.getCount());
                }
            }
            else {
                throw new BaseException(ORDER_INVALID_CANCEL);
            }
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        order.updateOrderStatus(OrderStatus.CANCELED);
        return "주문 취소되었습니다.";
    }

    /**
     * 주문 반품 신청 API
     */
    @Transactional
    public String refundOrder (Principal principal, Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));

        if (order.getMember().getId() == Long.parseLong(principal.getName())) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); // 현재 시간
            LocalDate currentDate = currentTime.toLocalDateTime().toLocalDate();

            LocalDate orderDate = order.getCreatedAt().toLocalDateTime().toLocalDate();
            long daysDifference = orderDate.until(currentDate, ChronoUnit.DAYS);

            // 배송완료 후 1일 후가 지나면 반품 불가
            if (daysDifference <= 3 && order.getStatus().equals(OrderStatus.DELIVERED)) {
                order.updateOrderStatus(OrderStatus.REFUND);
            } else {
                throw new BaseException(ORDER_INVALID_REFUND);
            }
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return "반품 신청 되었습니다.";
    }

    /**
     * 주문 반품 처리 API
     */
    @Transactional
    public void updateRefund() {
        List<Orders> orders = orderRepository.findAll();

        for (Orders order : orders) {
            if (order.getStatus().equals(OrderStatus.REFUND)) {
                // 재고 늘리기
                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    orderProduct.getProduct().updateStock(orderProduct.getProduct().getStock() + orderProduct.getCount());
                }

                order.updateOrderStatus(OrderStatus.REFUNDED);
            }
        }
    }

}
