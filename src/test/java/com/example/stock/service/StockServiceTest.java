package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    void before(){
        Stock stock = new Stock(1L, 100L);
        stockRepository.save(stock);
    }

    @AfterEach
    void after(){
        stockRepository.deleteAll();
    }

    @Test
    void stock_decrease(){
        //given
        //when
        stockService.decrease(1L, 1L);
        Stock stock = stockRepository.findById(1L).orElseThrow();

        //then
        Assertions.assertThat(stock.getQuantity()).isEqualTo(99L);
    }
}
