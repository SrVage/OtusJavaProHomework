package org.example;

import org.example.services.databases.StudentService;
import org.example.services.databases.StudentServiceImpl;

public class Main {

    public static void main(String[] args) {
        StudentService studentService = new StudentServiceImpl();
        studentService.createTable();
        studentService.createStudent("Bob", "Math");
        studentService.createStudent("Mike", "Programming");
    }
}