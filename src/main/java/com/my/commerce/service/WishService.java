package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.common.BasicException;
import com.my.commerce.domain.*;
import com.my.commerce.dto.Order.PostOrderProductReqDTO;
import com.my.commerce.dto.Order.PostOrderReqDTO;
import com.my.commerce.dto.Wish.PostWishProductReqDTO;
import com.my.commerce.repository.MemberRepository;
import com.my.commerce.repository.ProductRepository;
import com.my.commerce.repository.WishProductRepository;
import com.my.commerce.repository.WishRepository;
import com.my.commerce.util.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

import static com.my.commerce.common.BaseResponseStatus.PRODUCT_INVALID_ID;
import static com.my.commerce.common.BaseResponseStatus.SERVER_ERROR;

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
    public String postWishProduct(Principal principal, PostWishProductReqDTO postWishProductReqDTO) throws BasicException {
        try {
            Optional<Wish> wish = wishRepository.findByMemberId(Long.valueOf(principal.getName()));
            Optional<Product> product = productRepository.findById(postWishProductReqDTO.getProductId());

            if (product.isPresent() && product.get().getStatus() == 1) {
                WishProduct wishProduct = postWishProductReqDTO.toEntity(wish.get(), product.get(), postWishProductReqDTO.getCount());
                wishProductRepository.save(wishProduct);
            }
            else {
                throw new BaseException(PRODUCT_INVALID_ID);
            }

            return "장바구니 추가가 완료되었습니다.";

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

}
