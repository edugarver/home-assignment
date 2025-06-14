package com.example.products.http;

import com.example.products.core.model.CreateProductCommand;
import com.example.products.core.model.Product;
import com.example.products.core.model.SearchQuery;
import com.example.products.http.model.CreateProductTO;
import com.example.products.http.model.ProductTO;
import com.example.products.http.model.SearchQueryTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HttpModelMapper {

    CreateProductCommand map(CreateProductTO createProductTO);

    ProductTO map(Product product);

    SearchQuery map(SearchQueryTO searchQueryTO);
}
