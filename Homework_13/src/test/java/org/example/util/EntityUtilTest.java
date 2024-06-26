package org.example.util;


import org.example.configurations.JavaBasedSessionFactory;
import org.example.entities.Address;
import org.example.entities.Client;
import org.example.entities.Phone;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.util.Set;

public class EntityUtilTest {
    @Test
    void testAddClient(){
        try (SessionFactory sessionFactory = JavaBasedSessionFactory.getSessionFactory()) {
            int startCount = EntityUtil.findAll(sessionFactory, Client.class).size();
            var client = createClient("Watson",
                    "+79998881122",
                    "Baker street");

            EntityUtil.insert(sessionFactory, client);
            assertEquals(startCount+1, EntityUtil.findAll(sessionFactory, Client.class).size());
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
