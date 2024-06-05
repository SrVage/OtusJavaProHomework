package com.krampitmaxim.productsweb.repositories;

import com.krampitmaxim.productsweb.entities.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class ProductInMemoryRepositoryImpl implements ProductRepository{
    private final List<Product> products;

    public ProductInMemoryRepositoryImpl(){
        products = new ArrayList<>();
    }

    @Override
    public int addToRepository(Product product){
        int id = products.stream().max(Comparator.comparing(Product::getId)).map(Product::getId).orElse(0)+1;
        product.setId(id);
        products.add(product);
        return id;
    }

    @Override
    public Optional<Product> getProductById(int id){
        return products.stream().filter(item->item.getId() == id).findFirst();
    }

    @Override
    public List<Product> getAllProducts(){
        return products;
    }
}
