package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private double price;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products", fetch = FetchType.EAGER)
    private Set<Customer> customers;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        customers.forEach(customer->{
            builder.append(customer.getName());
            builder.append("\n");
        });
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", customers=" + builder.toString() +
                '}';
    }
}
