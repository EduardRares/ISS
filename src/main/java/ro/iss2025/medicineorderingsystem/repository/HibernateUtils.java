package ro.iss2025.medicineorderingsystem.repository;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ro.iss2025.medicineorderingsystem.domain.*;

import java.util.Properties;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static  SessionFactory createNewSessionFactory(){
        Properties props = new Properties();
        try (var in = HibernateUtils.class
                .getResourceAsStream("/META-INF/hibernate.properties")) {
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load custom Hibernate properties", e);
        }

        // 2) bootstrap Hibernate with those
        return new Configuration()
                .addProperties(props)
                .addAnnotatedClass(HospitalEmployee.class)
                .addAnnotatedClass(Medicine.class)
                .addAnnotatedClass(Order.class)
                .buildSessionFactory();
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}