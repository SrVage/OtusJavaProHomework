package org.example.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class EntityUtil {

    private EntityUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> T insert(SessionFactory sessionFactory, T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            T merged = session.merge(entity);
            session.getTransaction().commit();

            return merged;
        }
    }

    public static <T> T findById(SessionFactory sessionFactory, Class<T> cls, long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            return session.get(cls, id);
        }
    }

    public static <T> List<T> findAll(SessionFactory sessionFactory, Class<T> cls) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            return session.createQuery("SELECT a FROM " + cls.getSimpleName() + " a", cls).getResultList();
        }
    }

    public static <T> void deleteById(SessionFactory sessionFactory, Class<T> cls, long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            var entity = session.get(cls, id);
            if (entity == null){
                session.getTransaction().commit();
                return;
            }
            session.remove(session.get(cls, id));
            session.getTransaction().commit();
        }
    }
}
