package com.example.Homework_14.controllers;

import com.example.Homework_14.dto.UserDto;
import com.example.Homework_14.services.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/users/")
public class UserController extends BaseCRUDController<UserDto, UserServiceImpl>{

    public UserController(UserServiceImpl service) {
        super(service);
    }

    @GetMapping(path = "firstName/{firstName}")
    public ResponseEntity<Collection<UserDto>> getAllByFirstName(@PathVariable String firstName) {
        return new ResponseEntity<Collection<UserDto>>(service.findAllUsersByFirstName(firstName), HttpStatus.OK);
    }

    @GetMapping(path = "age/{age}")
    public ResponseEntity<Collection<UserDto>> getAllByFirstName(@PathVariable int age) {
        return new ResponseEntity<Collection<UserDto>>(service.findAllUsersWithAgeGreaterThan(age), HttpStatus.OK);
    }
}
