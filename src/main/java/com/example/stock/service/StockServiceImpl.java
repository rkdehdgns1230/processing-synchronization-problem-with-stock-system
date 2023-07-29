package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void decrease(Long id, Long quantity){
        Stock findStock = stockRepository.findById(id).orElseThrow();
        findStock.decrease(quantity);
        stockRepository.saveAndFlush(findStock);
    }
}
