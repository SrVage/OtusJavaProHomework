package com.example.Homework_14.repositories;

import com.example.Homework_14.entities.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM \"users\" WHERE \"first_name\" = :firstName")
    Iterable<User> findUsersByFirstName(String firstName);

    @Query("SELECT * FROM \"users\" WHERE \"age\" > :age")
    Iterable<User> findUsersWithAgeGreaterThan(int age);
}
