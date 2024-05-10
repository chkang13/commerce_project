package com.my.stockservice.dto;

import com.my.stockservice.domain.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostStockReqDTO {
    private Long productId;
    private int stock;

    @Builder
    public PostStockReqDTO(Long productId, int stock) {
        this.productId = productId;
        this.stock = stock;
    }

    public static Stock toEntity(Long productId, int stock) {
        return Stock.builder()
                .productId(productId)
                .stock(stock)
                .build();
    }

}
