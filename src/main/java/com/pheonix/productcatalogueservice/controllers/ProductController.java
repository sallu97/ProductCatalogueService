package com.pheonix.productcatalogueservice.controllers;

import com.pheonix.productcatalogueservice.dtos.CategoryDto;
import com.pheonix.productcatalogueservice.dtos.ProductDto;
import com.pheonix.productcatalogueservice.models.Category;
import com.pheonix.productcatalogueservice.models.Product;
import com.pheonix.productcatalogueservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public List<ProductDto> getProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        
        for (Product product : products){
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    @GetMapping("/products/{id}")
    public ProductDto getProductById(@PathVariable("id") Long productId){
        Product product = productService.getProductById(productId);
        ProductDto productDto = getProductDto(product);
        return productDto;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody  Product product){
        // adding comment for description
        product.setDescription("Product Description");
        return product;
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id,@RequestBody ProductDto productDto){
        Product input = getProduct(productDto);
        Product response= productService.replaceProduct(input,id);

        return getProductDto(response);
    }

    public Product getProduct(ProductDto productDto){
        Product product = new Product();
        //product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if(productDto.getCategory() != null){
            Category category = new Category();
            //category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            //category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }

        return product;
    }

    public ProductDto getProductDto(Product product){
        //System.out.println("Name: " + product.getName() + ", Description: " + product.getDescription() + ", Price: " + product.getPrice() + ", image: " + product.getImageUrl());
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if(product.getCategory() != null){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());

            productDto.setCategory(categoryDto);
        }
        return productDto;
    }



}
