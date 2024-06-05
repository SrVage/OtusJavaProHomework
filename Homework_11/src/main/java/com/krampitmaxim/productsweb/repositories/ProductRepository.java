package com.krampitmaxim.productsweb.repositories;

import com.krampitmaxim.productsweb.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    int addToRepository(Product product);

    Optional<Product> getProductById(int id);

    List<Product> getAllProducts();
}
