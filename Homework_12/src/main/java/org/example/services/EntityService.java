package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entities.Customer;
import org.example.util.EntityUtil;
import org.hibernate.SessionFactory;

import java.io.BufferedReader;
import java.util.logging.Logger;

@RequiredArgsConstructor
public abstract class EntityService<T> {
    protected static final Logger logger = Logger.getLogger(EntityService.class.getName());
    protected final SessionFactory sessionFactory;
    private final Class<T> cls;

    public abstract void addNew(BufferedReader reader);

    public void printAll(){
        var list = EntityUtil.findAll(sessionFactory, cls);
        list.forEach(item->logger.info(item.toString()));
    }

    public void printById(Long id){
        var customer = EntityUtil.findById(sessionFactory, cls, id);
        logger.info(customer.toString());
    }

    public void deleteById(Long id){
        EntityUtil.deleteById(sessionFactory, cls, id);
    }
}
