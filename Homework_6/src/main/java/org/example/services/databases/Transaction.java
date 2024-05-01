package org.example.services.databases;

import java.sql.Connection;

public interface Transaction {
    void executeQuery(String query);

    void setConnection(Connection connection);
}
