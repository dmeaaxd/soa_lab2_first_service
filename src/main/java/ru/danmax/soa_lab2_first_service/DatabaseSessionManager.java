package ru.danmax.soa_lab2_first_service;

import jakarta.enterprise.context.ApplicationScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.danmax.soa_lab2_first_service.entity.Dragon;
import ru.danmax.soa_lab2_first_service.entity.Person;

@ApplicationScoped
public class DatabaseSessionManager {

    private static SessionFactory sessionFactory;

    public DatabaseSessionManager() {
        sessionFactory = buildSessionFactory();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClass(Dragon.class);
            configuration.addAnnotatedClass(Person.class);
            System.err.println("Session Factory created");
            return configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build());
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void closeSession(Session session) {
        if (session.isOpen()) {
            session.close();
        }
    }
}