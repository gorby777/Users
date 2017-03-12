package dao;

/*
 * Служебный класс для получения фабрики сессий
 */

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

class HibernateUtil {
    private static final SessionFactory sessionFactory
            = configureSessionFactory();

    private static SessionFactory configureSessionFactory()throws HibernateException {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Получить фабрику сессий
     * @return {@link SessionFactory}
     */
    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
