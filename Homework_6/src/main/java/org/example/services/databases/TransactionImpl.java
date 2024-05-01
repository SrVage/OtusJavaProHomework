package org.example.services.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionImpl implements Transaction {
    private Connection connection;

    @Override
    public void executeQuery(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setConnection(java.sql.Connection connection) {
        this.connection = connection;
    }
}
