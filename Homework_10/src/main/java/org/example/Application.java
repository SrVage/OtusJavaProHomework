package org.example;

import org.example.model.Cart;
import org.example.repository.ProductRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@ComponentScan
public class Application {
    private final static Logger logger = Logger.getLogger(Application.class.getName());
    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        context.getBean(ProductsInitializer.class).initializeRepository();
        logger.info("0 - Выход;\n" +
                "1 - Создание новой корзины;\n" +
                "2 - Добавление товара в корзину;\n" +
                "3 - Удаление из корзины.\n" +
                "4 - Показать все товары");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Cart cart = context.getBean(Cart.class);
        try{
            while (true){
                logger.info("Выберите действие:");
                int input = Integer.parseInt(reader.readLine());
                if (input == 0) {
                    break;
                } else if (input == 1) {
                    cart = context.getBean(Cart.class);
                } else if (input == 2) {
                    int productId = getProductId(reader);
                    cart.addToCart(productId);
                } else if (input == 3) {
                    int productId = getProductId(reader);
                    cart.deleteFromCart(productId);
                } else if (input == 4) {
                    context.getBean(ProductRepository.class).getAllProducts().forEach(item->logger.info(item.toString()));
                } else{
                    logger.warning("Неверная операция");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getProductId(BufferedReader reader) throws IOException {
        logger.info("Введите id товара:");
        return Integer.parseInt(reader.readLine());
    }
}