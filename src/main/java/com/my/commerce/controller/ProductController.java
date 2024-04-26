package com.my.commerce.controller;

import com.my.commerce.common.BaseResponse;
import com.my.commerce.dto.Product.GetProductResDTO;
import com.my.commerce.dto.Product.PostProductReqDTO;
import com.my.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    /**
     * 상품 추가 API
     *
     * @param postProductReqDTO 상품 정보 DTO
     * @return String
     */
    @PostMapping()
    public BaseResponse<String> addProduct(@RequestBody PostProductReqDTO postProductReqDTO) {
        String addProductRes = productService.addProduct(postProductReqDTO);

        return new BaseResponse<>(addProductRes);
    }

    /**
     * 상품 리스트 조회 API
     *
     * @return List<GetProductResDTO>
     */
    @GetMapping()
    public BaseResponse<List<GetProductResDTO>> getProductList () {
        List<GetProductResDTO> getProductResDTOS = productService.getProductList();

        return new BaseResponse<>(getProductResDTOS);
    }

    /**
     * 상품 상세 조회 API
     *
     * @param productId
     * @return GetProductResDTO
     */
    @GetMapping("/{productId}")
    public BaseResponse<GetProductResDTO> getProduct (@PathVariable("productId") Long productId) {
        GetProductResDTO getProductResDTO = productService.getProduct(productId);

        return new BaseResponse<>(getProductResDTO);
    }
}
