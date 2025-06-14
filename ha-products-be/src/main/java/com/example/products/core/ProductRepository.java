package com.example.products.core;

import com.example.products.core.model.Product;
import com.example.products.core.model.SearchQuery;

import java.util.List;

public interface ProductRepository {
    Product createProduct(Product product);

    List<Product> listProducts();

    List<Product> searchProducts(SearchQuery query);
}
