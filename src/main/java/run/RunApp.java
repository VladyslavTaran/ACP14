package run;

import db.GroupDAO;
import db.Service;
import db.StudentDAO;
import db.SubjectDAO;
import exception.NoSuchEntityException;
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

/**
 * Created by Vlad on 15.10.2016.
 */
public class RunApp {

    public static final String PWD = "root";
    public static final String USER = PWD;
    public static final String DB_TO_RESTORE = "homeworkjpa";
    public static final String BKP_PATH = DB_TO_RESTORE + "_bkp.sql";

    public static void main(String[] args) {
        /*EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-unit");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        Group group = new Group("new group1", false);
        Subject subject = new Subject("new Subject", "new description", false);

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
        */

        /*EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-unit");
        try {
            Service service = new Service(factory);
            service.addProfessor("Pifagor", 17, 1, true);
            service.addProfessor("Kant", 11, 2, true);
            service.addProfessor("Bilobrov", 9, 3, true);
            service.addProfessor("Da Vinci", 23, 4, true);
            service.addProfessor("Einstein", 30, 5, true);
            service.addProfessor("Mavrodi", 22, 6, true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        /*dao.add(new Subject("Math", "Basis of everything", true));
        dao.add(new Subject("Philosophy", "Important subject", true));
        dao.add(new Subject("Computer science", "For geeks", true));
        dao.add(new Subject("Art", "Privileged subject", true));
        dao.add(new Subject("Physics", "Too important to ignore", true));
        dao.add(new Subject("Economy", "Helps to earn", true));*/


        //Group group = new Group("Group2", true);
        //dao.add(group);
        //dao.deactivate(1);


            try {
                String command = "mysql -u" + USER + " -p" + PWD + " " + DB_TO_RESTORE + " < " + BKP_PATH;
                Process runtimeProcess = Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
            }


    }
}
