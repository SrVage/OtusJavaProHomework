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

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        purchases.forEach(purchase->{
            builder.append(purchase.getCustomer().getName());
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
