package org.example;

import org.example.configurations.JavaBasedSessionFactory;
import org.example.entities.Address;
import org.example.entities.Client;
import org.example.entities.Phone;
import org.example.util.EntityUtil;
import org.hibernate.SessionFactory;

import java.util.Set;


public class Main {
    public static void main(String[] args) {
        try (SessionFactory sessionFactory = JavaBasedSessionFactory.getSessionFactory()) {
            Phone phone = new Phone();
            phone.setNumber("+79998881122");

            Address address = new Address();
            address.setStreet("Baker street");

            Client client = new Client();
            client.setName("Watson");
            client.setPhone(Set.of(phone));
            client.setAddress(address);

            EntityUtil.insert(sessionFactory, client);
        }
    }
}