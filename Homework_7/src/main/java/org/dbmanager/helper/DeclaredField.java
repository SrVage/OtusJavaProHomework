package org.dbmanager.helper;

import org.dbmanager.annotation.RepositoryField;
import org.dbmanager.exceptions.ApplicationInitializationException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeclaredField {
    public final Field field;
    public final Method getMethod;
    public final Method setMethod;
    private final String columnName;

    public DeclaredField(Field field, Class<?> cls){
        this.field = field;
        try {
            getMethod = cls.getMethod(methodName(field, "get"));
            setMethod = cls.getMethod(methodName(field, "set"), field.getType());
        } catch (NoSuchMethodException e){
            e.printStackTrace();
            throw new ApplicationInitializationException();
        }
        var fieldAnnotationName = field.getAnnotation(RepositoryField.class).column();
        columnName = fieldAnnotationName.isEmpty() ? field.getName() : fieldAnnotationName;
    }

    private String methodName(Field field, String start){
        return start + field.getName().substring(0, 1).toUpperCase()
                + field.getName().trim().substring(1);
    }

    public void invokeSet(ResultSet rs, Object instance) throws SQLException, InvocationTargetException, IllegalAccessException {
        if (field.getType() == Long.class || field.getType() == long.class){
            setMethod.invoke(instance, rs.getLong(columnName));
        }
        else{
            setMethod.invoke(instance, rs.getString(columnName));
        }
    }
}
