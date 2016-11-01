package db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Vlad on 29.10.2016.
 */
public class MgrFactory {
    private static EntityManagerFactory factory;

    static {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-xml-annot-context.xml");
        factory = applicationContext.getBean(EntityManagerFactory.class);
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
