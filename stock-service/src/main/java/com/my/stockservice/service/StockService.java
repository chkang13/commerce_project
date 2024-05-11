package com.my.stockservice.service;

import com.my.coreservice.global.common.BaseException;
import com.my.stockservice.domain.Stock;
import com.my.stockservice.dto.PostStockReqDTO;
import com.my.stockservice.kafka.dto.StockHandleDTO;
import com.my.stockservice.kafka.dto.StockHandleDTOS;
import com.my.stockservice.kafka.dto.WriteStockMessage;
import com.my.stockservice.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.my.coreservice.global.common.BaseResponseStatus.STOCK_INVALID_STOCK;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public String addStock(Long productId, int count) {
        Stock stock = PostStockReqDTO.toEntity(productId,count);
        stockRepository.save(stock);

        return "상품 재고가 추가되었습니다.";
    }

    @Transactional
    public String reduceStock(StockHandleDTOS stockHandleDTOS) {
        List<StockHandleDTO> stockHandleDTOList = stockHandleDTOS.getStockList();

        for (StockHandleDTO stockHandleDTO : stockHandleDTOList) {
            Stock stock = stockRepository.findByProductId(stockHandleDTO.getProductId()).orElseThrow(() -> new BaseException(STOCK_INVALID_STOCK));;
            stock.update(stock.getStock() - stockHandleDTO.getCount());
        }

        return "상품 재고가 감소되었습니다.";
    }

    @Transactional
    public String increaseStock(StockHandleDTOS stockHandleDTOS) {
        List<StockHandleDTO> stockHandleDTOList = stockHandleDTOS.getStockList();

        for (StockHandleDTO stockHandleDTO : stockHandleDTOList) {
            Stock stock = stockRepository.findByProductId(stockHandleDTO.getProductId()).orElseThrow(() -> new BaseException(STOCK_INVALID_STOCK));;
            stock.update(stock.getStock() + stockHandleDTO.getCount());
        }

        return "상품 재고가 증가되었습니다.";
    }

    public void reduceStock2(final WriteStockMessage writeStockMessage) {
        for (StockHandleDTO stockHandleDTO : writeStockMessage.stockHandleDTOS().getStockList()) {
            log.info(String.valueOf(stockHandleDTO.getProductId()));
            Stock stock = stockRepository.findByProductId(stockHandleDTO.getProductId()).orElseThrow(() -> new BaseException(STOCK_INVALID_STOCK));;
            stock.update(stock.getStock() - stockHandleDTO.getCount());
        }
    }

}
