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

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Purchase> purchases;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        purchases.forEach(purchase->{
            builder.append(purchase.getProduct().getTitle());
            builder.append("-");
            builder.append(purchase.getProduct().getPrice());
            builder.append("\t");
            builder.append("цена на момент покупки - ");
            builder.append(purchase.getPriceAtPurchase());
            builder.append("\n");
        });
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + builder.toString() +
                '}';
    }
}
