package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;
import com.my.commerce.common.BasicException;
import com.my.commerce.dto.Wish.PostWishProductReqDTO;
import com.my.commerce.service.WishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

    /**
     * 장박구니 항목 추가 API
     *
     * @param postWishProductReqDTO 상품 정보
     * @return String
     */
    @PostMapping
    private BaseResponse<String> postWishProduct(Principal principal, @RequestBody PostWishProductReqDTO postWishProductReqDTO) throws BasicException {
        String postWishProductRes = wishService.postWishProduct(principal, postWishProductReqDTO);

        return new BaseResponse<>(postWishProductRes);
    }


}
