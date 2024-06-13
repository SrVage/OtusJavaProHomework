package org.example.services;

import org.example.Messages;
import org.example.entities.Customer;
import org.example.entities.Product;
import org.example.util.EntityUtil;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;

public class CustomerService extends EntityService<Customer>{

    public CustomerService(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

    public void addNew(BufferedReader reader){
        try{
        Customer customer = new Customer();
        logger.info(Messages.INPUT_CUSTOMER_NAME.toString());
        customer.setName(reader.readLine());
        var products = new HashSet<Product>();
        while (true){
            logger.info(Messages.INPUT_PRODUCT_ID.toString());
            var str = reader.readLine();
            if (str.equals("!")){
                break;
            }
            Product product = EntityUtil.findById(sessionFactory, Product.class, Long.parseLong(str));
            products.add(product);
        }
        customer.setProducts(products);
        EntityUtil.insert(sessionFactory, customer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
