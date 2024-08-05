package com.pheonix.productcatalogueservice.controllers;

import com.pheonix.productcatalogueservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<Product> getProducts(){
       return null;
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId){
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody  Product product){
        product.setDescription("Product Description");
        return product;
    }



}
