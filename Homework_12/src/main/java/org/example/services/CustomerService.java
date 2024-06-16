package org.example.services;

import org.example.Messages;
import org.example.entities.Customer;
import org.example.entities.Product;
import org.example.entities.Purchase;
import org.example.util.EntityUtil;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class CustomerService extends EntityService<Customer> {

    public CustomerService(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

    public void addNew(BufferedReader reader) {
        try {
            Customer customer = new Customer();
            logger.info(Messages.INPUT_CUSTOMER_NAME.toString());
            customer.setName(reader.readLine());

            var tableCustomer = EntityUtil.insert(sessionFactory, customer);

            while (true) {
                logger.info(Messages.INPUT_PRODUCT_ID.toString());
                var str = reader.readLine();
                if (str.equals("!")) {
                    break;
                }
                Product product = EntityUtil.findById(sessionFactory, Product.class, Long.parseLong(str));
                Purchase purchase = new Purchase();
                purchase.setCustomer(tableCustomer);
                purchase.setProduct(product);
                purchase.setPriceAtPurchase(product.getPrice());
                EntityUtil.insert(sessionFactory, purchase);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
