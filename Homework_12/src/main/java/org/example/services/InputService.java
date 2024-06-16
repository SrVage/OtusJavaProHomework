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

    public void start() {
        try {
            while (true) {
                logger.info(Messages.HELLO_MESSAGE.toString());
                logger.info(Messages.CHOOSE_ACTION.toString());
                int input = Integer.parseInt(reader.readLine());
                switch (input) {
                    case 0:
                        return;
                    case 1:
                        productService.addNew(reader);
                        break;
                    case 2:
                        productService.printAll();
                        break;
                    case 3:
                        customerService.addNew(reader);
                        break;
                    case 4:
                        customerService.printAll();
                        break;
                    case 5:
                        customerService.printById(Long.parseLong(reader.readLine()));
                        break;
                    case 6:
                        productService.printById(Long.parseLong(reader.readLine()));
                        break;
                    case 7:
                        customerService.deleteById(Long.parseLong(reader.readLine()));
                        break;
                    case 8:
                        productService.deleteById(Long.parseLong(reader.readLine()));
                        break;
                    default:
                        logger.warning(Messages.BAD_OPERATION.toString());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
