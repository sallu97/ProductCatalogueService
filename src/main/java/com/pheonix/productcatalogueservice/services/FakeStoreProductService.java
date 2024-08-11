package com.pheonix.productcatalogueservice.services;

import com.pheonix.productcatalogueservice.dtos.FakeStoreProductDto;
import com.pheonix.productcatalogueservice.dtos.ProductDto;
import com.pheonix.productcatalogueservice.models.Category;
import com.pheonix.productcatalogueservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtoResponse = restTemplate.getForEntity("http://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtoResponse){
            products.add(getProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Product product,Long id) {
        FakeStoreProductDto fakeStoreProductDto = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDtoResponse = requestForEntity("http://fakestoreapi.com/products/{id}",HttpMethod.PUT, fakeStoreProductDto ,FakeStoreProductDto.class,id).getBody();
        return getProduct(fakeStoreProductDtoResponse);
    }

    /* This function is used to update a specific product with respect id*/
    public <T> ResponseEntity<T> requestForEntity(String url, HttpMethod httpMethod ,@Nullable Object request,
                                               Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }

    public FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());

        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }

        return fakeStoreProductDto;
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
