package com.my.productservice.controller;

import com.my.coreservice.global.common.BaseResponse;
import com.my.productservice.dto.Wish.GetWishProductResDTO;
import com.my.productservice.dto.Wish.PatchWishProductReqDTO;
import com.my.productservice.dto.Wish.PostWishOrderReqDTO;
import com.my.productservice.dto.Wish.PostWishProductReqDTO;
import com.my.productservice.service.OrderService;
import com.my.productservice.service.WishService;
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

    /**
     * 장바구니 항목 추가 API
     *
     * @param postWishProductReqDTO 상품 정보
     * @return String
     */
    @PostMapping
    private BaseResponse<String> postWishProduct(Principal principal, @RequestBody PostWishProductReqDTO postWishProductReqDTO) {
        String postWishProductRes = wishService.postWishProduct(principal, postWishProductReqDTO);

        return new BaseResponse<>(postWishProductRes);
    }

    /**
     * 장바구니 항목 수량 변경 API
     *
     * @return String
     */
    @PatchMapping()
    private BaseResponse<String> patchWishProduct(Principal principal, @RequestBody PatchWishProductReqDTO patchWishProductReqDTO) {
        String patchWishProductRes = wishService.patchWishProduct(principal, patchWishProductReqDTO);

        return new BaseResponse<>(patchWishProductRes);
    }

    /**
     * 장바구니 항목 삭제 API
     *
     * @return String
     */
    @DeleteMapping("/{wishProductId}")
    private BaseResponse<String> deleteWishProduct(Principal principal, @PathVariable("wishProductId") Long wishProductId) {
        String deleteWishProductRes = wishService.deleteWishProduct(principal, wishProductId);

        return new BaseResponse<>(deleteWishProductRes);
    }

    /**
     * 장바구니 리스트 조회 API
     *
     * @return List<GetWishProductResDTO>
     */
    @GetMapping
    private BaseResponse<List<GetWishProductResDTO>> getWishProducts(Principal principal) {
        List<GetWishProductResDTO> getWishProductResDTOS = wishService.getWishProducts(principal);

        return new BaseResponse<>(getWishProductResDTOS);
    }

    /**
     * 장바구니에서 주문 추가 API
     *
     * @param postWishOrderReqDTO 주문 정보
     * @return String
     */
    @PostMapping("/orders")
    private BaseResponse<String> postWishOrder(Principal principal, @RequestBody PostWishOrderReqDTO postWishOrderReqDTO) {
        String postWishOrderRes = orderService.postWishOrder(principal, postWishOrderReqDTO);

        return new BaseResponse<>(postWishOrderRes);
    }


}
