package com.pheonix.productcatalogueservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public abstract class BaseModel {

    private long id;

    private Date createdAt;

    private Date updatedAt;

    private Status status;
}
