package com.my.orderservice.service;

import com.my.coreservice.global.common.BaseException;
import com.my.orderservice.client.ProductServiceFeignClient;
import com.my.orderservice.domain.Wish;
import com.my.orderservice.domain.WishProduct;
import com.my.orderservice.dto.Wish.GetWishProductResDTO;
import com.my.orderservice.dto.Wish.PatchWishProductReqDTO;
import com.my.orderservice.dto.Wish.PostWishProductReqDTO;
import com.my.orderservice.dto.client.GetProductResDTO;
import com.my.orderservice.repository.WishProductRepository;
import com.my.orderservice.repository.WishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.my.coreservice.global.common.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WishService {
    private final WishRepository wishRepository;
    private final WishProductRepository wishProductRepository;
    private final ProductServiceFeignClient productServiceFeignClient;

    @Transactional
    public String addWish(Long memberId) {
        Wish wish = Wish.builder()
                .memberId(memberId)
                .build();

        wishRepository.save(wish);

        return "장바구니가 생성되었습니다.";
    }

    /**
     * 장바구니 항목 추가 API
     */
    @Transactional
    public String postWishProduct(Long memberId, PostWishProductReqDTO postWishProductReqDTO) {
        Wish wish = wishRepository.findByMemberId(memberId).orElseThrow(() -> new BaseException(WISH_INVALID_ID));

        WishProduct wishProduct = postWishProductReqDTO.toEntity(wish, postWishProductReqDTO);
        wishProductRepository.save(wishProduct);

        return "장바구니 추가가 완료되었습니다.";
    }

    /**
     * 장바구니 항목 수량 변경 API
     * */
    @Transactional
    public String patchWishProduct(Long memberId, PatchWishProductReqDTO patchWishProductReqDTO) {
        WishProduct wishProduct = wishProductRepository.findById(patchWishProductReqDTO.getWishProductId()).orElseThrow(() -> new BaseException(WISHPRODUCT_INVALID_ID));

        // 권한 확인
        if (wishProduct.getWish().getMemberId() == memberId){
            wishProduct.update(patchWishProductReqDTO.getCount());
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return "수량 변경이 완료되었습니다.";
    }

    /**
     * 장바구니 항목 삭제 API
     * */
    @Transactional
    public String deleteWishProduct(Long memberId, Long wishProductId) {
        WishProduct wishProduct = wishProductRepository.findById(wishProductId).orElseThrow(() -> new BaseException(WISHPRODUCT_INVALID_ID));

        // 권한 확인
        if (wishProduct.getWish().getMemberId() == memberId){
            wishProductRepository.deleteById(wishProductId);
        }
        else {
            throw new BaseException(AUTHORITY_INVALID);
        }

        return "장바구니 항목 삭제가 완료되었습니다.";
    }

    /**
     * 장바구니 리스트 조회 API
     */
    public List<GetWishProductResDTO> getWishProducts(Long memberId) {
        Wish wish = wishRepository.findByMemberId(memberId).orElseThrow(() -> new BaseException(WISH_INVALID_ID));
        List<WishProduct> wishProducts = wish.getWishProducts();

        List<GetWishProductResDTO> getWishProductResDTOS = new ArrayList<>();

        for (WishProduct wishProduct : wishProducts) {
            GetProductResDTO getProductResDTO = productServiceFeignClient.getInProduct(wishProduct.getProductId());
            int price = getProductResDTO.getPrice() * wishProduct.getCount();
            getWishProductResDTOS.add(GetWishProductResDTO.toDTO(getProductResDTO, wishProduct, price));
        }

        return  getWishProductResDTOS;
    }

}
