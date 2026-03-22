package com.klu.HibernateCRUD;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.configure();                 // reads hibernate.cfg.xml from classpath
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);   // ✅ correct spelling
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}



