package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;
import com.my.commerce.common.BasicException;
import com.my.commerce.dto.Order.GetOrderResDTO;
import com.my.commerce.dto.Order.PostOrderReqDTO;
import com.my.commerce.service.OrderService;
import com.my.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문 추가 API
     *
     * @param postOrderReqDTO 주문 정보
     * @return String
     */
    @PostMapping
    private BaseResponse<String> postOrder(Principal principal, @RequestBody PostOrderReqDTO postOrderReqDTO) throws BasicException {
        String postOrderRes = orderService.postOrder(principal, postOrderReqDTO);

        return new BaseResponse<>(postOrderRes);
    }

    /**
     * 주문 목록 조회 API
     *
     * @param issueId 조회할 이슈 아이디
     */

    /**
     * 주문 조회 API
     *
     * @param orderId 조회할 주문 아이디
     */
    @GetMapping("/{orderId}")
    private BaseResponse<GetOrderResDTO> getOrders(Principal principal, @PathVariable("orderId") Long orderId) throws BasicException{
        GetOrderResDTO getOrderResDTO = orderService.getOrders(principal, orderId);

        return new BaseResponse<>(getOrderResDTO);
    }


}
