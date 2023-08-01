package com.example.stock.facade;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NamedLockStockFacadeTest {
    @Autowired
    OptimisticLockStockFacade optimisticLockStockFacade;
    @Autowired
    StockRepository stockRepository;

    private static final Long STOCK_PRODUCT_ID = 1L;

    @BeforeEach
    void beforeEach(){
        Stock stock = new Stock(STOCK_PRODUCT_ID, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void afterEach(){
        stockRepository.deleteAll();
    }

    @Test
    void 동시에_여러개의_요청() throws InterruptedException {
        //given
        int threadCount = 100;
        // 비동기 실행 작업을 단순하게 사용 가능하도록 제공하는 Java API
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        CountDownLatch latch = new CountDownLatch(threadCount); // count 100으로 설정

        //when
        // 100건의 요청 전송
        for(int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try {
                    optimisticLockStockFacade.decrease(STOCK_PRODUCT_ID, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally{
                    latch.countDown(); // count 1씩 감소
                }
            });
        }
        latch.await(); // count 0될 때 까지 대기

        //then
        Stock stock = stockRepository.findByProductId(STOCK_PRODUCT_ID);
        assertThat(stock.getQuantity()).isEqualTo(0L); // 실패
    }
}