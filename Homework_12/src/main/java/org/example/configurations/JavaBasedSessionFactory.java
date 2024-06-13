package org.example.configurations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.entities.Customer;
import org.example.entities.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaBasedSessionFactory {

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        Properties properties = getProperties();

        configuration.setProperties(properties);

        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Product.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();


        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "org.h2.Driver");
        properties.put("hibernate.connection.url", "jdbc:h2:~/test");
        properties.put("hibernate.connection.username", "sa");
        properties.put("hibernate.connection.password", "");
        //properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.current_session_context_class", "thread");
        return properties;
    }
}
