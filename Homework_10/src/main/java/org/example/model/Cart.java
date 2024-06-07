package org.example.model;

import org.example.repository.ProductRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Scope("prototype")
public class Cart {
    private final Logger logger = Logger.getLogger(Cart.class.getName());
    private final List<Product> products;
    private final ProductRepository productRepository;

    public Cart(ProductRepository productRepository) {
        products = new ArrayList<>();
        this.productRepository = productRepository;
        logger.info(String.format("Корзина успешно создана.\nТекуще количество товаров: %d", products.size()));
    }

    public void addToCart(int id){
        var product = productRepository.getProductById(id);
        if (product != null){
            products.add(product);
            logger.info(String.format("Продукт %s успешно добавлен в корзину", product.getName()));
        } else{
            logger.warning(String.format("Нет продукта с id: %d", id));
        }
    }

    public void deleteFromCart(int id){
        var product = products.stream()
                .filter(item->item.getId() == id)
                .findFirst().orElse(null);
        if (product != null){
            products.remove(product);
            logger.info(String.format("Продукт %s успешно удален из корзины", product.getName()));
        } else {
            logger.warning(String.format("Нет продукта с id: %d", id));
        }
    }
}
