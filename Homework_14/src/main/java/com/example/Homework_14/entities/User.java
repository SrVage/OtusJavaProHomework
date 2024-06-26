package com.example.Homework_14.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@ToString
@Table("users")
public class User {
    @Id
    @Column("id")
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("second_name")
    private String secondName;

    @Column("address")
    private String address;

    @Column("age")
    private Integer age;
}
