package com.pheonix.productcatalogueservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;

    private String description;

    private double price;

    private String imageUrl;

    private CategoryDto category;
}
