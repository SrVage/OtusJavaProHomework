package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.Messages;
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
                logger.info(Messages.HELLO_MESSAGE.toString());
                logger.info(Messages.CHOOSE_ACTION.toString());
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
                    logger.warning(Messages.BAD_OPERATION.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
