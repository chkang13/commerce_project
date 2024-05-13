package com.my.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.coreservice.global.common.BaseException;
import com.my.orderservice.client.ProductServiceFeignClient;
import com.my.orderservice.client.StockServiceFeignClient;
import com.my.orderservice.domain.OrderProduct;
import com.my.orderservice.domain.Orders;
import com.my.orderservice.dto.Order.*;
import com.my.orderservice.dto.client.GetProductResDTO;
import com.my.orderservice.kafka.OrderKafkaProducer;
import com.my.orderservice.kafka.dto.StockHandleDTO;
import com.my.orderservice.kafka.dto.StockHandleDTOS;
import com.my.orderservice.kafka.dto.WriteOrderMessage;
import com.my.orderservice.repository.*;
import com.my.orderservice.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.my.coreservice.global.common.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductServiceFeignClient productServiceFeignClient;
    private final StockServiceFeignClient stockServiceFeignClient;
    private final OrderKafkaProducer orderKafkaProducer;

    /**
     * 주문 추가 API
     */
    @Transactional
    public String postOrder(Long memberId, PostOrderReqDTO postOrderReqDTO) throws JsonProcessingException {
        // 주문 만들기
        Orders orders = postOrderReqDTO.toEntity(memberId, postOrderReqDTO.getTotal(), OrderStatus.PAYMENT);
        orderRepository.save(orders);

        // 재고 줄이기 통신을 위해 저장
        StockHandleDTOS stockHandleDTOS;
        List<StockHandleDTO> stockHandleDTOList = new ArrayList<>();

        // 주문 상품 만들기
        for (PostOrderProductReqDTO postOrderProductReqDTO : postOrderReqDTO.getOrderProducts()) {
            OrderProduct orderProduct = postOrderProductReqDTO.toEntity(orders, postOrderProductReqDTO);
            orderProductRepository.save(orderProduct);

            // 재고 줄이기 통신을 위해 저장
            stockHandleDTOList.add(StockHandleDTO.toDTO(postOrderProductReqDTO.getProductId(), postOrderProductReqDTO.getCount()));
        }



        stockHandleDTOS = StockHandleDTOS.toDTO(stockHandleDTOList);

        // 재고 서비스로 전달
        orderKafkaProducer.updateStock(stockHandleDTOS, orders.getId());

        // 재고 서비스로 전달
        //stockServiceFeignClient.reduceStock(stockHandleDTOS);

        return "결제 준비가 완료되었습니다.";
    }

    /**
     * 결제 상태 변경 API
     */
    @Transactional
    public void updatePayment() throws JsonProcessingException {
        List<Orders> orders = orderRepository.findAll();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis()); // 현재 시간
        LocalDateTime currentDateTime = currentTime.toLocalDateTime();

        for (Orders order : orders) {
            // 재고 늘리기 통신을 위해 저장
            StockHandleDTOS stockHandleDTOS;
            List<StockHandleDTO> stockHandleDTOList = new ArrayList<>();

            LocalDateTime orderDateTime = order.getCreatedAt().toLocalDateTime();

            // 날짜 차이
            long minusBetween = ChronoUnit.MINUTES.between(currentDateTime, orderDateTime);

            if (minusBetween >= 5) {

                for (OrderProduct orderProduct : order.getOrderProducts()) {

                    stockHandleDTOList.add(StockHandleDTO.toDTO(orderProduct.getProductId(), orderProduct.getCount()));

                }

                // 재고 서비스로 전달
                stockHandleDTOS = StockHandleDTOS.toDTO(stockHandleDTOList);
                // stockServiceFeignClient.increaseStock(stockHandleDTOS);
                orderKafkaProducer.updateStock(stockHandleDTOS, order.getId());
                order.updateOrderStatus(OrderStatus.PAYBACK);
            }
        }
    }

    @Transactional
    public void deletePayment(final WriteOrderMessage writeOrderMessage) {
        Orders order = orderRepository.findById(writeOrderMessage.orderId()).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));
        order.updateOrderStatus(OrderStatus.PAYBACK);
    }


    /**
     * 주문 완성 API
     */
    @Transactional
    public String patchOrder (Long memberId, Long orderId) throws JsonProcessingException {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));

        if (order.getMemberId() == memberId) {
            // 결제 준비 상태에서만
            if (order.getStatus().equals(OrderStatus.PAYMENT)) {
                // 결제 중 20% 이탈
                if (new Random().nextInt(100) < 80) {
                    order.updateOrderStatus(OrderStatus.ORDERED);
                }
                else {
                    // 재고 늘리기 통신을 위해 저장
                    StockHandleDTOS stockHandleDTOS;
                    List<StockHandleDTO> stockHandleDTOList = new ArrayList<>();

                    for (OrderProduct orderProduct : order.getOrderProducts()) {

                        stockHandleDTOList.add(StockHandleDTO.toDTO(orderProduct.getProductId(), orderProduct.getCount()));

                    }

                    // 재고 서비스로 전달
                    stockHandleDTOS = StockHandleDTOS.toDTO(stockHandleDTOList);
                    //stockServiceFeignClient.increaseStock(stockHandleDTOS);
                    orderKafkaProducer.updateStock(stockHandleDTOS, order.getId());

                    throw new BaseException(ORDER_CANCELED_PAYMENT);
                }
            }
            else {
                throw new BaseException(ORDER_INVALID_PAYMENT);
            }
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return "주문 완료 되었습니다.";
    }

//    /**
//     * 장바구니 주문 추가 API
//     */
//    @Transactional
//    public String postWishOrder(Long memberId, PostWishOrderReqDTO postWishOrderReqDTO) {
//        // 주문 만들기
//        Orders orders = postWishOrderReqDTO.toEntity(memberId, postWishOrderReqDTO.getTotal(), OrderStatus.PAYMENT);
//        orderRepository.save(orders);
//
//        // 주문 상품 만들기
//        for (PostOrderWIshProductReqDTO postOrderWIshProductReqDTO : postWishOrderReqDTO.getOrderProducts()) {
//            OrderProduct orderProduct = postOrderWIshProductReqDTO.toEntity(orders, postOrderWIshProductReqDTO);
//            orderProductRepository.save(orderProduct);
//
//            // 장바구니 항목 삭제
//            wishProductRepository.deleteById(postOrderWIshProductReqDTO.getWishProductId());
//
//            // 재고 줄이기
//            //product.updateStock(product.getStock() - postOrderWIshProductReqDTO.getCount());
//        }
//
//        return "주문이 완료되었습니다.";
//    }

    /**
     * 주문 리스트 조회 API
     */
    public List<GetOrdersResDTO> getOrderList(Long memberId) {
        List<GetOrdersResDTO> getOrdersResDTOS = new ArrayList<>();
        List<Orders> orders = orderRepository.findByMemberId(memberId);

        for (Orders order : orders) {
            getOrdersResDTOS.add(GetOrdersResDTO.toDTO(order));
        }

        return  getOrdersResDTOS;
    }

    /**
     * 주문 조회 API
     */
    public GetOrderResDTO getOrders(Long memberId, Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));
        GetOrderResDTO getOrderResDTO;

        if (order.getMemberId() == memberId) {
            List<OrderProduct> orderProducts = order.getOrderProducts();
            List<GetOrderProductResDTO> getOrderProductResDTOS = new ArrayList<>();

            for (OrderProduct orderProduct : orderProducts) {
                GetProductResDTO getProductResDTO = productServiceFeignClient.getInProduct(orderProduct.getProductId());
                getOrderProductResDTOS.add(GetOrderProductResDTO.toDTO(orderProduct, getProductResDTO));
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
    public String cancelOrder (Long memberId, Long orderId) throws JsonProcessingException {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));

        if (order.getMemberId() == memberId) {
            // 배송중 이전까지만
            if (order.getStatus().equals(OrderStatus.ORDERED)) {
                // 재고 늘리기 통신을 위해 저장
                StockHandleDTOS stockHandleDTOS;
                List<StockHandleDTO> stockHandleDTOList = new ArrayList<>();

                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    stockHandleDTOList.add(StockHandleDTO.toDTO(orderProduct.getProductId(), orderProduct.getCount()));

                }
                // 재고 서비스로 전달
                stockHandleDTOS = StockHandleDTOS.toDTO(stockHandleDTOList);
                // stockServiceFeignClient.increaseStock(stockHandleDTOS);
                orderKafkaProducer.updateStock(stockHandleDTOS, order.getId());

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
    public String refundOrder (Long memberId, Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new BaseException(ORDER_INVALID_ID));

        if (order.getMemberId() == memberId) {
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
    public void updateRefund() throws JsonProcessingException {
        List<Orders> orders = orderRepository.findAll();

        for (Orders order : orders) {
            if (order.getStatus().equals(OrderStatus.REFUND)) {
                // 재고 늘리기 통신을 위해 저장
                StockHandleDTOS stockHandleDTOS;
                List<StockHandleDTO> stockHandleDTOList = new ArrayList<>();

                for (OrderProduct orderProduct : order.getOrderProducts()) {
                    stockHandleDTOList.add(StockHandleDTO.toDTO(orderProduct.getProductId(), orderProduct.getCount()));

                }
                // 재고 서비스로 전달
                stockHandleDTOS = StockHandleDTOS.toDTO(stockHandleDTOList);
                //stockServiceFeignClient.increaseStock(stockHandleDTOS);
                orderKafkaProducer.updateStock(stockHandleDTOS, order.getId());

                order.updateOrderStatus(OrderStatus.REFUNDED);
            }
        }
    }

}
