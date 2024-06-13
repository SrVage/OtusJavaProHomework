package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customers_table")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "customer_to_book",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        products.forEach(product->{
            builder.append(product.getTitle());
            builder.append("-");
            builder.append(product.getPrice());
            builder.append("\n");
        });
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + builder.toString() +
                '}';
    }
}
