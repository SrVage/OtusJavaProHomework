package org.dbmanager.service;

import org.dbmanager.annotation.RepositoryField;
import org.dbmanager.annotation.RepositoryIdField;
import org.dbmanager.helper.DeclaredField;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AbstractRepository<T> {
    private final PreparedStatement psCreate;
    private final PreparedStatement psSelect;
    private final PreparedStatement psSelectById;
    private final PreparedStatement psUpdate;
    private final PreparedStatement psDeleteById;
    private final PreparedStatement psDeleteAll;
    private final List<Field> cachedGetFields;
    private final List<Field> cachedSetFields;
    private final Method getId;
    private final Class<T> cls;
    private final Map<Field, DeclaredField> fieldMap;

    public AbstractRepository(
            Class<T> cls,
            PreparedStatement psCreate,
            PreparedStatement psSelect,
            PreparedStatement psSelectById,
            PreparedStatement psUpdate,
            PreparedStatement psDeleteById,
            PreparedStatement psDeleteAll,
            List<Field> cachedGetFields,
            List<Field> cachedSetFields,
            Map<Field, DeclaredField> fieldMap) throws NoSuchMethodException {
        this.cls = cls;
        this.psCreate = psCreate;
        this.psSelect = psSelect;
        this.psSelectById = psSelectById;
        this.psUpdate = psUpdate;
        this.psDeleteById = psDeleteById;
        this.psDeleteAll = psDeleteAll;
        this.cachedGetFields = cachedGetFields;
        this.cachedSetFields = cachedSetFields;
        this.fieldMap = fieldMap;
        getId = prepareIdField();
    }

    public void create(T entity) {
        try {
            for (int i = 0; i < cachedGetFields.size(); i++) {
                var method = fieldMap.get(cachedGetFields.get(i)).getMethod;
                psCreate.setObject(i + 1, method.invoke(entity));
            }
            psCreate.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<T> findAll() throws SQLException {
        List<T> out = new ArrayList<>();
        try (ResultSet rs = psSelect.executeQuery()) {
            while (rs.next()) {
                Constructor<T> constructor = cls.getDeclaredConstructor();
                var obj = constructor.newInstance();
                for (var field : cachedSetFields){
                    fieldMap.get(field).invokeSet(rs, obj);
                }
                out.add(obj);
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(out);
    }

    public Optional<T> findById(long id) throws SQLException {
        psSelectById.setObject(1, id);
        try (
                ResultSet rs =  psSelectById.executeQuery()) {
            while (rs.next()) {
                Constructor<T> constructor = cls.getDeclaredConstructor();
                var obj = constructor.newInstance();
                for (var field : cachedSetFields){
                    var method = cls.getMethod("set"+field.getName().substring(0,1).toUpperCase()+field.getName().trim().substring(1), field.getType());
                    var columnName = field.getAnnotation(RepositoryField.class).column();
                    if (field.getType() == Long.class || field.getType() == long.class){
                        method.invoke(obj, rs.getLong(columnName.isEmpty() ? field.getName() : columnName));
                    }
                    else{
                        method.invoke(obj, rs.getString(columnName.isEmpty() ? field.getName() : columnName));
                    }
                }
                return Optional.of(obj);
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(T entity){
        try {
            for (int i = 0; i < cachedGetFields.size(); i++) {
                var method = cls.getMethod("get" +
                        cachedGetFields.get(i).getName().substring(0, 1).toUpperCase() +
                        cachedGetFields.get(i).getName().trim().substring(1));
                psUpdate.setObject(i + 1, method.invoke(entity));
            }
            psUpdate.setObject(cachedGetFields.size() + 1, getId.invoke(entity));
            psUpdate.executeUpdate();
        } catch (SQLException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(long id){
        try{
            psDeleteById.setObject(1, id);
            psDeleteById.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() throws SQLException {
        try {
            psDeleteAll.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private Method prepareIdField() throws NoSuchMethodException {
        var idField = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(RepositoryField.class))
                .filter(f -> f.isAnnotationPresent(RepositoryIdField.class))
                .findFirst().orElse(null);
        if (idField != null) {
            return cls.getMethod("get" + idField.getName().substring(0, 1).toUpperCase() + idField.getName().substring(1));
        } else {
            throw new NoSuchMethodException("ID field not found");
        }
    }
}
