package com.my.orderservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.my.coreservice.global.common.BaseResponse;
import com.my.orderservice.dto.Order.GetOrderResDTO;
import com.my.orderservice.dto.Order.GetOrdersResDTO;
import com.my.orderservice.dto.Order.PostOrderReqDTO;
import com.my.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 추가 API(결제 준비)
     *
     * @param postOrderReqDTO 주문 정보
     * @return String
     */
    @PostMapping
    private BaseResponse<String> postOrder(@RequestHeader Long memberId, @RequestBody PostOrderReqDTO postOrderReqDTO) throws JsonProcessingException {
        String postOrderRes = orderService.postOrder(memberId, postOrderReqDTO);

        return new BaseResponse<>(postOrderRes);
    }

    /**
     * 주문 완성 API(결제 시도)
     *
     * @param orderId 시도할 주문 아이디
     */
    @PatchMapping("/payment/{orderId}")
    private BaseResponse<String> patchOrder (@RequestHeader Long memberId, @PathVariable("orderId") Long orderId) throws JsonProcessingException {
        String patchRes = orderService.patchOrder(memberId, orderId);

        return new BaseResponse<>(patchRes);
    }


    /**
     * 주문 목록 조회 API
     *
     * @return List<GetOrdersResDTO> 조회할 이슈 아이디
     */

    @GetMapping()
    public BaseResponse<List<GetOrdersResDTO>> getOrderList (@RequestHeader Long memberId) {
        List<GetOrdersResDTO> getOrdersResDTOS = orderService.getOrderList(memberId);

        return new BaseResponse<>(getOrdersResDTOS);
    }


    /**
     * 주문 조회 API
     *
     * @param orderId 조회할 주문 아이디
     */
    @GetMapping("/{orderId}")
    private BaseResponse<GetOrderResDTO> getOrders(@RequestHeader Long memberId, @PathVariable("orderId") Long orderId) {
        GetOrderResDTO getOrderResDTO = orderService.getOrders(memberId, orderId);

        return new BaseResponse<>(getOrderResDTO);
    }

    /**
     * 주문 취소 API
     *
     * @param orderId 취소할 주문 아이디
     */
    @PatchMapping("/{orderId}")
    private BaseResponse<String> cancelOrder (@RequestHeader Long memberId, @PathVariable("orderId") Long orderId) throws JsonProcessingException {
        String cancelRes = orderService.cancelOrder(memberId, orderId);

        return new BaseResponse<>(cancelRes);
    }

    /**
     * 주문 반품 신청 API
     *
     * @param orderId 반품할 주문 아이디
     */
    @PatchMapping("/refund/{orderId}")
    private BaseResponse<String> refundOrder (@RequestHeader Long memberId, @PathVariable("orderId") Long orderId) {
        String refundRes = orderService.refundOrder(memberId, orderId);

        return new BaseResponse<>(refundRes);
    }


}
