package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OptimisticLockStockService implements StockService{
    private final StockRepository stockRepository;
    @Override
    public void decrease(Long productId, Long quantity) {
        Stock stock = stockRepository.findByProductIdWithOptimisticLock(productId);
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
