package com.my.orderservice.service;

import com.my.coreservice.global.common.BaseException;
import com.my.orderservice.domain.Product;
import com.my.orderservice.dto.Product.GetProductResDTO;
import com.my.orderservice.dto.Product.PostProductReqDTO;
import com.my.orderservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.my.coreservice.global.common.BaseResponseStatus.PRODUCT_INVALID_ID;

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
    public String addProduct(PostProductReqDTO postProductReqDTO) {
        Product product = postProductReqDTO.toEntity(postProductReqDTO, 1);
        productRepository.save(product);

        return "상품 추가 성공하였습니다.";
    }

    /**
     * 상품 리스트 조회 API
     */
    public List<GetProductResDTO> getProductList() {
        List<GetProductResDTO> getProductResDTOS = new ArrayList<>();
        List<Product> products = productRepository.findAll();

        for (Product product : products) {
            getProductResDTOS.add(GetProductResDTO.toDTO(product));
        }

        return  getProductResDTOS;
    }

    /**
     * 상품 상세 조회 API
     */
    public GetProductResDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BaseException(PRODUCT_INVALID_ID));

        if (product.getStatus() == 1) {
            GetProductResDTO getProductResDTO = GetProductResDTO.toDTO(product);

            return getProductResDTO;
        } else {
            throw new BaseException(PRODUCT_INVALID_ID);
        }
    }

}
