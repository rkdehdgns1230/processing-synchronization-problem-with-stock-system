package com.example.stock.facade;

import com.example.stock.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService stockService;

    public void decrease(Long productId, Long quantity) throws InterruptedException {
        // 정상 업데이트 될 때까지 50ms 텀을 두고, 반복 실행
        while(true){
            try{
                stockService.decrease(productId, quantity);
                break;
            }
            catch (Exception e){
                Thread.sleep(50);
            }
        }
    }
}
