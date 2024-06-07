package org.example;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductsInitializer {
    private final ProductRepository productRepository;

    public ProductsInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void initializeRepository(){
        productRepository.addProduct(new Product(1, "Чай", 100));
        productRepository.addProduct(new Product(2, "Кофе", 200));
        productRepository.addProduct(new Product(3, "Сахар", 50));
        productRepository.addProduct(new Product(4, "Молоко", 80));
        productRepository.addProduct(new Product(5, "Хлеб", 40));
        productRepository.addProduct(new Product(6, "Сыр", 150));
        productRepository.addProduct(new Product(7, "Колбаса", 300));
        productRepository.addProduct(new Product(8, "Масло", 120));
        productRepository.addProduct(new Product(9, "Яблоки", 70));
        productRepository.addProduct(new Product(10, "Бананы", 90));    }
}
