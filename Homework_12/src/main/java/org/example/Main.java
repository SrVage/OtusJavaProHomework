package org.example;

import org.example.configurations.JavaBasedSessionFactory;
import org.example.entities.Customer;
import org.example.entities.Product;
import org.example.services.CustomerService;
import org.example.services.EntityService;
import org.example.services.InputService;
import org.example.services.ProductService;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = JavaBasedSessionFactory.getSessionFactory()) {
            EntityService<Customer> customerService = new CustomerService(sessionFactory);
            EntityService<Product> productService = new ProductService(sessionFactory);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            InputService inputService = new InputService(customerService, productService, reader);

            inputService.start();
        }
    }
}