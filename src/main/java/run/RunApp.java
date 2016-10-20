package run;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Vlad on 15.10.2016.
 */
public class RunApp {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-unit");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        Group group = new Group("new group1", false);
        Student student = new Student("Student name", group, false);
        Subject subject = new Subject("new Subject", "new description", false);

        Professor professor = new Professor("professor'a name",23,subject,false);
        Course course = new Course(group,subject);

        try{
            transaction.begin();
            //manager.persist(group);
            //manager.persist(subject);
            manager.persist(course);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            manager.close();
        }

        //EntityManager manager = factory.createEntityManager();
        //manager.persist(new Student(1,"new name",1,true));
    }
}
