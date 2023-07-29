package com.example.stock.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    public Stock() {
    }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity(){
        return this.quantity;
    }

    public void decrease(Long quantity){
        if(this.quantity < quantity){
            throw new IllegalStateException("재고 수량이 부족합니다.");
        }
        this.quantity -= quantity;
    }
}
