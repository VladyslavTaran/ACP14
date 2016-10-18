package run;

import model.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Vlad on 15.10.2016.
 */
public class RunApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-unit");
        //EntityManager manager = factory.createEntityManager();
        //manager.persist(new Student(1,"new name",1,true));
    }
}
