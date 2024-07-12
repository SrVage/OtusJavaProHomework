package com.example.homework14.controllers;

import com.example.homework14.services.CRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

public abstract class BaseCRUDController<TDto, TService extends CRUDService<TDto>> {
    protected final TService service;

    public BaseCRUDController(TService service){
        this.service = service;
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<TDto>(service.getById(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Collection<TDto>> getAll() {
        return new ResponseEntity<Collection<TDto>>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TDto item) {
        try {
            TDto newItem = service.create(item);
            return new ResponseEntity<TDto>(newItem, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody TDto item) {
        try {
            TDto newItem = service.update(item);
            return new ResponseEntity<TDto>(newItem, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
