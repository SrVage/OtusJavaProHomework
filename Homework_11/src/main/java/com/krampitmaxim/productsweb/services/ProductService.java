package com.krampitmaxim.productsweb.services;

import com.krampitmaxim.productsweb.dtos.ProductDto;
import com.krampitmaxim.productsweb.entities.Product;

import java.util.List;

public interface ProductService {
    void createNewProduct(ProductDto productDto);

    Product getProductById(int id);

    List<Product> getAllProducts();
}
