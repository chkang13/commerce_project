package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.domain.*;
import com.my.commerce.dto.Wish.GetWishProductResDTO;
import com.my.commerce.dto.Wish.PatchWishProductReqDTO;
import com.my.commerce.dto.Wish.PostWishProductReqDTO;
import com.my.commerce.repository.ProductRepository;
import com.my.commerce.repository.WishProductRepository;
import com.my.commerce.repository.WishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.my.commerce.common.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WishService {
    private final WishRepository wishRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductRepository productRepository;

    /**
     * 장바구니 항목 추가 API
     */
    @Transactional
    public String postWishProduct(Principal principal, PostWishProductReqDTO postWishProductReqDTO) {
        Wish wish = wishRepository.findByMemberId(Long.parseLong(principal.getName())).orElseThrow(() -> new BaseException(WISH_INVALID_ID));
        Product product = productRepository.findById(postWishProductReqDTO.getProductId()).orElseThrow(() -> new BaseException(PRODUCT_INVALID_ID));

        if (product.getStatus() == 1) {
            WishProduct wishProduct = postWishProductReqDTO.toEntity(wish, product, postWishProductReqDTO.getCount());
            wishProductRepository.save(wishProduct);
        }
        else {
            throw new BaseException(PRODUCT_INVALID_ID);
        }

        return "장바구니 추가가 완료되었습니다.";
    }

    /**
     * 장바구니 항목 수량 변경 API
     * */
    @Transactional
    public String patchWishProduct(Principal principal, PatchWishProductReqDTO patchWishProductReqDTO) {
        WishProduct wishProduct = wishProductRepository.findById(patchWishProductReqDTO.getWishProductId()).orElseThrow(() -> new BaseException(WISHPRODUCT_INVALID_ID));

        // 권한 확인
        if (wishProduct.getWish().getMember().getId() == Long.parseLong(principal.getName())){
            wishProduct.update(patchWishProductReqDTO.getCount());
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return "수량 변경이 완료되었습니다.";
    }

    /**
     * 장바구니 리스트 조회 API
     */
    public List<GetWishProductResDTO> getWishProducts(Principal principal) {
        Wish wish = wishRepository.findByMemberId(Long.parseLong(principal.getName())).orElseThrow(() -> new BaseException(WISH_INVALID_ID));
        List<WishProduct> wishProducts = wish.getWishProducts();

        List<GetWishProductResDTO> getWishProductResDTOS = new ArrayList<>();

        for (WishProduct wishProduct : wishProducts) {
            int price = wishProduct.getProduct().getPrice() * wishProduct.getCount();
            getWishProductResDTOS.add(GetWishProductResDTO.toDTO(wishProduct.getProduct(), wishProduct, price));
        }

        return  getWishProductResDTOS;
    }

}
