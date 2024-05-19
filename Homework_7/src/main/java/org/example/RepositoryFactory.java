package org.example;

import org.example.exceptions.ApplicationInitializationException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryFactory {
    public static <T> AbstractRepository<T> createRepository(DataSource dataSource, Class<T> cls) {
        try {
            Connection connection = dataSource.getConnection();

            List<Field> cachedGetFields = Arrays.stream(cls.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                    .filter(f -> !f.isAnnotationPresent(RepositoryIdField.class))
                    .collect(Collectors.toList());

            List<Field> cachedSetFields = Arrays.stream(cls.getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                    .collect(Collectors.toList());

            String tableName = cls.getAnnotation(RepositoryTable.class).title();

            String insertQuery = createInsertQuery(tableName, cachedGetFields);
            PreparedStatement psCreate = connection.prepareStatement(insertQuery);

            String selectQuery = "SELECT * FROM " + tableName;
            PreparedStatement psSelect = connection.prepareStatement(selectQuery);

            String selectByIdQuery = "SELECT * FROM " + tableName + " WHERE id=?";
            PreparedStatement psSelectById = connection.prepareStatement(selectByIdQuery);

            String updateQuery = createUpdateQuery(tableName, cachedGetFields);
            PreparedStatement psUpdate = connection.prepareStatement(updateQuery);

            String deleteByIdQuery = "DELETE FROM " + tableName + " WHERE id=?";
            PreparedStatement psDeleteById = connection.prepareStatement(deleteByIdQuery);

            String deleteAllQuery = "DELETE FROM " + tableName;
            PreparedStatement psDeleteAll = connection.prepareStatement(deleteAllQuery);

            return new AbstractRepository<>(
                    cls,
                    psCreate,
                    psSelect,
                    psSelectById,
                    psUpdate,
                    psDeleteById,
                    psDeleteAll,
                    cachedGetFields,
                    cachedSetFields
            );

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ApplicationInitializationException();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createInsertQuery(String tableName, List<Field> fields) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(tableName).append(" (");
        for (Field field : fields) {
            var columnName = field.getAnnotation(RepositoryField.class).column();
            if (columnName.isEmpty())
            {
                query.append(field.getName()).append(", ");
            } else{
                query.append(columnName).append(", ");
            }
        }
        query.setLength(query.length() - 2);
        query.append(") VALUES (");
        for (Field field : fields) {
            query.append("?, ");
        }
        query.setLength(query.length() - 2);
        query.append(");");
        return query.toString();
    }

    private static String createUpdateQuery(String tableName, List<Field> fields) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).append(" SET ");
        for (Field field : fields) {
            var columnName = field.getAnnotation(RepositoryField.class).column();
            if (columnName.isEmpty())
            {
                query.append(field.getName()).append(" = ?, ");
            } else{
                query.append(columnName).append(" = ?, ");
            }
        }
        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        return query.toString();
    }
}

