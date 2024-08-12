package com.pheonix.productcatalogueservice.controllers;

import com.pheonix.productcatalogueservice.dtos.CategoryDto;
import com.pheonix.productcatalogueservice.dtos.ProductDto;
import com.pheonix.productcatalogueservice.models.Category;
import com.pheonix.productcatalogueservice.models.Product;
import com.pheonix.productcatalogueservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        List<ProductDto> response = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for(Product product : products) {
            response.add(getProductDto(product));
        }

        return response;
     }


    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("ProductId is invalid");
        }

        Product product = productService.getProductById(productId);
        ProductDto productDto = getProductDto(product);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("called By", "Mohammad Salman");

        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product)
    {
        return null;
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
      Product input = getProduct(productDto);
      Product product = productService.replaceProduct(input,id);
      return getProductDto(product);
    }

    public Product getProduct(ProductDto productDto){
        Product product = new Product();
        //product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            //category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            //category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        if(product.getCategory() != null) {
            CategoryDto category = new CategoryDto();
            category.setId(product.getCategory().getId());
            category.setName(product.getCategory().getName());
            productDto.setCategory(category);
        }
        return productDto;
    }

    /*
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleExceptions(Exception exc){
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }

     */
}