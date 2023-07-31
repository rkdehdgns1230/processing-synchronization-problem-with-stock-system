package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
//@Transactional
//@RequiredArgsConstructor
public class StockServiceImpl  {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    //    @Override
    public synchronized void decrease(Long id, Long quantity){
        Stock findStock = stockRepository.findById(id).orElseThrow();
        findStock.decrease(quantity);
        stockRepository.saveAndFlush(findStock);
    }
}
