package com.example.gccoffee.controller;

import com.example.gccoffee.domain.Product;
import com.example.gccoffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.val;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String productPage(Model model){
        List<Product> allProducts = productService.getAllProducts();

        val products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @PostMapping("/products")
    public String productPage(CreateProductRequest createProductRequest){

        productService.createProduct(
                createProductRequest.name(),
                createProductRequest.category(),
                createProductRequest.price(),
                createProductRequest.description()
        );

        return "redirect:/products";
    }

    @GetMapping("/new-product")
    public String newProductPage(){
        return "new-product";
    }

}
