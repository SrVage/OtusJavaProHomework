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
        purchases.forEach(purchase -> {
            builder.append(purchase.getProduct().getTitle())
                    .append("-")
                    .append(purchase.getProduct().getPrice())
                    .append("\t")
                    .append("цена на момент покупки - ")
                    .append(purchase.getPriceAtPurchase())
                    .append("\n");
        });
        return new StringBuilder()
                .append("Customer{")
                .append("id=")
                .append(id)
                .append(", name='")
                .append(name)
                .append('\'')
                .append(", products=")
                .append(builder.toString())
                .append('}')
                .toString();
    }
}
