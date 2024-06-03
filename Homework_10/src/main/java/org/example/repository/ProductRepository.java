package org.example.repository;

import org.example.model.Product;

import java.util.List;

public interface ProductRepository {
    void addProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);
}
