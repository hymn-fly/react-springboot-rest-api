package com.example.gccoffee.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of="id")
@ToString
public class Product {
    private final UUID id;
    private String name;
    private Category category;
    private long price;
    private String description;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(UUID id, String productName, Category category, long price) {
        this.id = id;
        this.name = productName;
        this.category = category;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(UUID id, String productName, Category category, long price, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = productName;
        this.category = category;
        this.price = price;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setPrice(long price){
        this.price = price;
        this.updatedAt = LocalDateTime.now();
    }

    public void setCategory(Category category){
        this.category = category;
        this.updatedAt = LocalDateTime.now();
    }

    public void setName(String name){
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description){
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }
}
