package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity){
        try{
            lockRepository.getLock(productId.toString());
            stockService.decrease(productId, quantity);
        } finally {
            lockRepository.releaseLock(productId.toString());
        }
    }
}
