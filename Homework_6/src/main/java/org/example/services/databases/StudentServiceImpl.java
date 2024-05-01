package org.example.services.databases;

public class StudentServiceImpl implements StudentService {
    private final String createTableQuery = "CREATE TABLE Student (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "Name VARCHAR(255)," +
            "Course VARCHAR(255)" +
            ")";
    private final Transaction transaction;

    public StudentServiceImpl() {
        transaction = new TransactionProxy(new TransactionImpl());
    }

    @Override
    public void createStudent(String name, String course) {
        var createQuery = String.format("INSERT INTO Student (Name, Course) VALUES ('%s', '%s')", name, course);
        transaction.executeQuery(createQuery);
    }

    @Override
    public void createTable() {
        transaction.executeQuery(createTableQuery);
    }
}
