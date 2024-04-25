package com.my.commerce.service;

import com.my.commerce.common.BaseException;
import com.my.commerce.common.BasicException;
import com.my.commerce.domain.Member;
import com.my.commerce.domain.Product;
import com.my.commerce.domain.Wish;
import com.my.commerce.dto.Member.PostMemberReqDTO;
import com.my.commerce.dto.Product.GetProductResDTO;
import com.my.commerce.dto.Product.PostProductReqDTO;
import com.my.commerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.my.commerce.common.BaseResponseStatus.PRODUCT_INVALID_ID;
import static com.my.commerce.common.BaseResponseStatus.SERVER_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 상품 추가 API
     */
    @Transactional
    public String addProduct(PostProductReqDTO postProductReqDTO) throws BasicException {
        try {
            Product product = postProductReqDTO.toEntity(postProductReqDTO, 1);
            productRepository.save(product);

            return "상품 추가 성공하였습니다.";

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

    /**
     * 상품 리스트 조회 API
     */
    public List<GetProductResDTO> getProductList() throws BasicException{
        try {
            List<GetProductResDTO> getProductResDTOS = new ArrayList<>();
            List<Product> products = productRepository.findAll();

            for (Product product : products) {
                getProductResDTOS.add(GetProductResDTO.toDTO(product));
            }

            return  getProductResDTOS;

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

    /**
     * 상품 상세 조회 API
     */
    public GetProductResDTO getProduct(Long productId) throws BasicException{
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new BaseException(PRODUCT_INVALID_ID));

            if (product.getStatus() == 1) {
                GetProductResDTO getProductResDTO = GetProductResDTO.toDTO(product);

                return getProductResDTO;
            } else {
                throw new BaseException(PRODUCT_INVALID_ID);
            }

        } catch (Exception e) {
            throw new BasicException(SERVER_ERROR);
        }
    }

}
