package utils;

import entities.ConferenceEntity;
import entities.LocationEntity;
import entities.PartakerEntity;
import entities.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        Properties settings = new Properties();

        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "123456");

        configuration.addProperties(settings);
        configuration.addAnnotatedClass(UserEntity.class);
        configuration.addAnnotatedClass(ConferenceEntity.class);
        configuration.addAnnotatedClass(PartakerEntity.class);
        configuration.addAnnotatedClass(LocationEntity.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        return sessionFactory;
    }
}
