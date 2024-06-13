package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.Customer;
import org.example.entities.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class InputService {
    private final static Logger logger = Logger.getLogger(InputService.class.getName());
    private final EntityService<Customer> customerService;
    private final EntityService<Product> productService;
    private final BufferedReader reader;

    public void start(){
        try{
            while (true){
                logger.info("0 - Выход;\n" +
                        "1 - Создание нового товара;\n" +
                        "2 - Вывести все товары;\n" +
                        "3 - Создание нового пользователя.\n" +
                        "4 - Показать всех пользователей.\n" +
                        "5 - Найти все товары пользователя.\n" +
                        "6 - Найти всех пользователей товара.\n" +
                        "7 - Удалить пользователя.\n" +
                        "8 - Удалить товар");
                logger.info("Выберите действие:");
                int input = Integer.parseInt(reader.readLine());
                if (input == 0) {
                    break;
                } else if(input == 1){
                    productService.addNew(reader);
                } else if (input == 2) {
                    productService.printAll();
                } else if (input == 3) {
                    customerService.addNew(reader);
                } else if (input == 4) {
                    customerService.printAll();
                } else if (input == 5){
                    customerService.printById(Long.parseLong(reader.readLine()));
                } else if (input == 6) {
                    productService.printById(Long.parseLong(reader.readLine()));
                } else if (input == 7){
                    customerService.deleteById(Long.parseLong(reader.readLine()));
                } else if (input == 8) {
                    productService.deleteById(Long.parseLong(reader.readLine()));
                } else{
                    logger.warning("Неверная операция");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
