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
            var client = createClient("Watson",
                    "+79998881122",
                    "Baker street");

            EntityUtil.insert(sessionFactory, client);
        }
    }

    private static Client createClient(String name, String phoneNumber, String street) {
        Phone phone = new Phone();
        phone.setNumber(phoneNumber);

        Address address = new Address();
        address.setStreet(street);

        Client client = new Client();
        client.setName(name);
        client.setPhone(Set.of(phone));
        client.setAddress(address);

        return client;
    }
}