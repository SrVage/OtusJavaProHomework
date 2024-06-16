package org.example.services;

import org.example.Messages;
import org.example.entities.Product;
import org.example.util.EntityUtil;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class ProductService extends EntityService<Product> {
    public ProductService(SessionFactory sessionFactory) {
        super(sessionFactory, Product.class);
    }

    @Override
    public void addNew(BufferedReader reader) {
        try {
            Product product = new Product();
            logger.info(Messages.INPUT_PRODUCT_TITLE.toString());
            product.setTitle(reader.readLine());
            logger.info(Messages.INPUT_PRODUCT_COST.toString());
            product.setPrice(Double.parseDouble(reader.readLine()));
            EntityUtil.insert(sessionFactory, product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
