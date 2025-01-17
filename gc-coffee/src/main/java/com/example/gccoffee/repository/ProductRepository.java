package com.example.gccoffee.repository;

import com.example.gccoffee.domain.Category;
import com.example.gccoffee.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    List<Product> findAll();

    Product insert(Product product);

    Product update(Product product);

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category);

    void deleteAll();

}
