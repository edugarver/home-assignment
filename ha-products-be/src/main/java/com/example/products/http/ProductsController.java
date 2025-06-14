package com.example.products.http;

import com.example.products.core.ProductService;
import com.example.products.core.model.Product;
import com.example.products.http.model.CreateProductTO;
import com.example.products.http.model.ProductCreatedResponseTO;
import com.example.products.http.model.ProductTO;
import com.example.products.http.model.SearchQueryTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final HttpModelMapper mapper;

    @PostMapping("/api/v1/products/create")
    public ResponseEntity<ProductCreatedResponseTO> createProduct(@RequestBody CreateProductTO createProductTO) {
        final Product product = productService.createProduct(mapper.map(createProductTO));
        return ResponseEntity.ok(ProductCreatedResponseTO.success(product.applicationNumber()));
    }

    @GetMapping("/api/v1/products/list")
    public ResponseEntity<List<ProductTO>> listProducts() {
        final List<Product> products = productService.listProducts();
        return ResponseEntity.ok(products.stream().map(mapper::map).toList());
    }

    @PostMapping("/api/v1/products/search")
    public ResponseEntity<List<ProductTO>> searchProducts(@RequestBody SearchQueryTO searchQueryTO) {
        final List<Product> products = productService.searchProducts(mapper.map(searchQueryTO));
        return ResponseEntity.ok(products.stream().map(mapper::map).toList());
    }

}
