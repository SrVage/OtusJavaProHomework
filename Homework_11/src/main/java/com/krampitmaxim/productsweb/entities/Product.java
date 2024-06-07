package com.krampitmaxim.productsweb.entities;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    public int id;
    public final String title;
    public double price;
}
