package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.common.BasicException;
import com.my.commerce.domain.*;
import com.my.commerce.dto.Member.PostMemberReqDTO;
import com.my.commerce.dto.Order.GetOrderProductResDTO;
import com.my.commerce.dto.Order.GetOrderResDTO;
import com.my.commerce.dto.Order.PostOrderProductReqDTO;
import com.my.commerce.dto.Order.PostOrderReqDTO;
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
    public String postOrder(Principal principal, PostOrderReqDTO postOrderReqDTO) throws BasicException {
        try {
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
                }
                else {
                    throw new BaseException(PRODUCT_INVALID_ID);
                }
            }

            return "주문이 완료되었습니다.";

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

    /**
     * 주문 조회 API
     */
    public GetOrderResDTO getOrders(Principal principal, Long orderId) throws BasicException{
        try {
            Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));
            GetOrderResDTO getOrderResDTO;

            if (order.getMember().getId() == Long.parseLong(principal.getName())) {
                List<OrderProduct> orderProducts = order.getOrderProducts();
                List<GetOrderProductResDTO> getOrderProductResDTOS = new ArrayList<>();

                for (OrderProduct orderProduct : orderProducts) {
                    getOrderProductResDTOS.add(GetOrderProductResDTO.toDTO(orderProduct, orderProduct.getProduct()));
                }

                getOrderResDTO = GetOrderResDTO.toDTO(order,getOrderProductResDTOS);
            }
            else {
                throw new BaseException(AUTHORITY_INVALID);
            }

            return getOrderResDTO;

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

}
