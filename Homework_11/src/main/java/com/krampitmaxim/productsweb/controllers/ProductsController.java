package com.krampitmaxim.productsweb.controllers;

import com.krampitmaxim.productsweb.dtos.ProductDto;
import com.krampitmaxim.productsweb.entities.Product;
import com.krampitmaxim.productsweb.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;

    @PostMapping
    public void createProduct(@RequestBody ProductDto productDto){
        if (productDto.title() == null){
            throw new RuntimeException("No product");
        }
        productService.createNewProduct(productDto);
    }

    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
    }

}
