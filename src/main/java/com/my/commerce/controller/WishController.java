package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;
import com.my.commerce.dto.Wish.GetWishProductResDTO;
import com.my.commerce.dto.Wish.PatchWishProductReqDTO;
import com.my.commerce.dto.Wish.PostWishProductReqDTO;
import com.my.commerce.service.WishService;
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
     * 장바구니 리스트 조회 API
     *
     * @return List<GetWishProductResDTO>
     */
    @GetMapping
    private BaseResponse<List<GetWishProductResDTO>> getWishProducts(Principal principal) {
        List<GetWishProductResDTO> getWishProductResDTOS = wishService.getWishProducts(principal);

        return new BaseResponse<>(getWishProductResDTOS);
    }


}
