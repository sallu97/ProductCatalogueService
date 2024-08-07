package com.pheonix.productcatalogueservice.services;

import com.pheonix.productcatalogueservice.dtos.FakeStoreProductDto;
import com.pheonix.productcatalogueservice.models.Category;
import com.pheonix.productcatalogueservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public Product getProductById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("http://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,id).getBody();
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    public Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        //System.out.println("Title: " + fakeStoreProductDto.getTitle()  + ", price: " + fakeStoreProductDto.getPrice() + ", Description: " + fakeStoreProductDto.getDescription() + ", Image: " + fakeStoreProductDto.getImage());
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());

        if (fakeStoreProductDto.getCategory() != null) {
            Category category = new Category();
            category.setName(fakeStoreProductDto.getCategory());
            product.setCategory(category);
        }

        return product;
    }


}
