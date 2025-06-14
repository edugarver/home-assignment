package com.example.products.adapters.mongo;

import com.example.products.adapters.mongo.model.ProductEntity;
import com.example.products.core.ProductRepository;
import com.example.products.core.model.Product;
import com.example.products.core.model.SearchQuery;
import com.example.products.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class ProductMongoRepository implements ProductRepository {

    private final MongoTemplate mongoTemplate;
    private final EntityModelMapper entityModelMapper;

    @Override
    public Product createProduct(Product product) {
        final ProductEntity entity = entityModelMapper.map(product);
        return entityModelMapper.map(mongoTemplate.insert(entity));
    }

    @Override
    public List<Product> listProducts() {
        return mongoTemplate.findAll(ProductEntity.class).stream().map(entityModelMapper::map).toList();
    }

    @Override
    public List<Product> searchProducts(SearchQuery query) {
        Criteria criteria = new Criteria();
        if (!StringUtils.isBlankOrNull(query.manufacturer())) {
            criteria = criteria.and(ProductEntity.Fields.manufacturer).is(query.manufacturer());
        }
        if (!StringUtils.isBlankOrNull(query.brandName())) {
            criteria = criteria.and(ProductEntity.Fields.brandName).is(query.brandName());
        }
        final List<ProductEntity> found = mongoTemplate.find(new Query(criteria), ProductEntity.class);

        return found.stream().map(entityModelMapper::map).toList();
    }
}
