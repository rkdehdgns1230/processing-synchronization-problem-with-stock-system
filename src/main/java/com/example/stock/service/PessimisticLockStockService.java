package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService implements StockService{
    private final StockRepository stockRepository;

    @Override
    @Transactional
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithPessimisticLock(productId);

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
