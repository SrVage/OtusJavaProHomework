package com.krampitmaxim.productsweb.services;

import com.krampitmaxim.productsweb.dtos.ProductDto;
import com.krampitmaxim.productsweb.entities.Product;
import com.krampitmaxim.productsweb.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class.getName());

    @Override
    public void createNewProduct(ProductDto productDto){
        var id = productRepository.addToRepository(convertFromDto(productDto));
        logger.info("Продукт с id {} успешно создан", id);
    }

    @Override
    public Product getProductById(int id){
        return productRepository.getProductById(id).get();
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.getAllProducts();
    }

    private static Product convertFromDto(ProductDto dto){
        Product product = new Product(dto.title());
        product.setPrice(dto.price());
        return product;
    }
}
