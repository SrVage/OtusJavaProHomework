package org.example.services.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TransactionProxy implements Transaction {
    private final Transaction transaction;
    private Connection connection;
    private final String url = "jdbc:h2:mem:testdb";
    private final String user = "sa";
    private final String pass = "";
    private final Logger logger = Logger.getLogger(TransactionProxy.class.getSimpleName());

    public TransactionProxy(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void executeQuery(String query) {
        if (connection == null) {
            try {
                connection = getNewConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            setConnection(connection);
        }
        try {
            transaction.executeQuery(query);
            connection.commit();
            logger.info(query + " was committed");

        } catch (Exception e) {
            try {
                connection.rollback();
                logger.warning(query + " was rollback");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void setConnection(Connection connection) {
        transaction.setConnection(connection);
    }

    private Connection getNewConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            return connection;
        } finally {
            connection = null;
        }
    }
}
