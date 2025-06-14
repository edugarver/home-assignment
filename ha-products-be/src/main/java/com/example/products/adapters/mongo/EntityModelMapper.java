package com.example.products.adapters.mongo;

import com.example.products.adapters.mongo.model.ProductEntity;
import com.example.products.core.model.Product;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EntityModelMapper {

    ProductEntity map(Product model);
    Product map(ProductEntity entity);

}
