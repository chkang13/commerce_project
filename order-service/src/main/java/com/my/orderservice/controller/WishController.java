package com.my.orderservice.controller;

import com.my.coreservice.global.common.BaseResponse;
import com.my.orderservice.dto.Wish.GetWishProductResDTO;
import com.my.orderservice.dto.Wish.PatchWishProductReqDTO;
import com.my.orderservice.dto.Wish.PostWishOrderReqDTO;
import com.my.orderservice.dto.Wish.PostWishProductReqDTO;
import com.my.orderservice.service.OrderService;
import com.my.orderservice.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;
    private final OrderService orderService;

    @PostMapping("/{memberId}")
    private String addWish(@PathVariable("memberId") Long memberId) {
        String addWishRes = wishService.addWish(memberId);

         return addWishRes;
    }

    /**
     * 장바구니 항목 추가 API
     *
     * @param postWishProductReqDTO 상품 정보
     * @return String
     */
    @PostMapping
    private BaseResponse<String> postWishProduct(@RequestHeader Long memberId, @RequestBody PostWishProductReqDTO postWishProductReqDTO) {
        String postWishProductRes = wishService.postWishProduct(memberId, postWishProductReqDTO);

        return new BaseResponse<>(postWishProductRes);
    }

    /**
     * 장바구니 항목 수량 변경 API
     *
     * @return String
     */
    @PatchMapping()
    private BaseResponse<String> patchWishProduct(@RequestHeader Long memberId, @RequestBody PatchWishProductReqDTO patchWishProductReqDTO) {
        String patchWishProductRes = wishService.patchWishProduct(memberId, patchWishProductReqDTO);

        return new BaseResponse<>(patchWishProductRes);
    }

    /**
     * 장바구니 항목 삭제 API
     *
     * @return String
     */
    @DeleteMapping("/{wishProductId}")
    private BaseResponse<String> deleteWishProduct(@RequestHeader Long memberId, @PathVariable("wishProductId") Long wishProductId) {
        String deleteWishProductRes = wishService.deleteWishProduct(memberId, wishProductId);

        return new BaseResponse<>(deleteWishProductRes);
    }

    /**
     * 장바구니 리스트 조회 API
     *
     * @return List<GetWishProductResDTO>
     */
    @GetMapping
    private BaseResponse<List<GetWishProductResDTO>> getWishProducts(@RequestHeader Long memberId) {
        List<GetWishProductResDTO> getWishProductResDTOS = wishService.getWishProducts(memberId);

        return new BaseResponse<>(getWishProductResDTOS);
    }

    /**
     * 장바구니에서 주문 추가 API
     *
     * @param postWishOrderReqDTO 주문 정보
     * @return String
     */
    @PostMapping("/orders")
    private BaseResponse<String> postWishOrder(@RequestHeader Long memberId, @RequestBody PostWishOrderReqDTO postWishOrderReqDTO) {
        String postWishOrderRes = orderService.postWishOrder(memberId, postWishOrderReqDTO);

        return new BaseResponse<>(postWishOrderRes);
    }


}
