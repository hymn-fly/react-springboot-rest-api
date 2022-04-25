package com.example.gccoffee.controller;

import com.example.gccoffee.domain.Category;

public record CreateProductRequest(String name, Category category, long price, String description) {
}
