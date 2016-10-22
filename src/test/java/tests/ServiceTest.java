package tests;

import db.*;
import exception.NoSuchEntityException;
import exception.WrongDataException;
import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

import static db.Constants.*;

/**
 * Created by Vlad on 11.10.2016.
 */
public class ServiceTest {
    public static final String PWD = "root";
    public static final String USER = PWD;
    public static final String DB_TO_RESTORE = "homeworkjpa";
    public static final String BKP_PATH = DB_TO_RESTORE + "_bkp.sql";
    private Service service;

    private void restoreDB(){
        try {
            Process runtimeProcess = Runtime.getRuntime().exec("mysql -u" + USER + " -p" + PWD + " " + DB_TO_RESTORE + " < " + BKP_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        restoreDB();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("hibernate-unit");
        try {
            service = new Service(factory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void testGetAllStudents() {
        int actual = service.getAllStudents().size();
        int expected = 10;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetAllGroups() {
        int actual = service.getAllGroups().size();
        int expected = 2;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllSubjects() {
        int actual = service.getAllSubjects().size();
        int expected = 6;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllProfessors() {
        int actual = service.getAllProfessors().size();
        int expected = 6;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddStudent() {
        Student actual = null;
        Student expected = null;
        try {
            actual = new Student("Test", service.getGroupById(2), true);
            expected = service.addStudent("Test", 2, true);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateStudentInfo() {
        String actual = null;
        String expected = null;
        try {
            actual = "new name";
            expected = service.updateStudentInfo(1, actual, 1, false).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetStudentById() {
        int actual = 0;
        int expected = 0;
        try {
            actual = 2;
            expected = service.getStudentById(2).getId();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllStudentsByGroupId() {
        int actual = 0;
        try {
            actual = service.getAllStudentsByGroupId(1).size();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        int expected = 5;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetGroupById() {
        String actual = null;
        try {
            actual = service.getGroupById(1).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "ACP14";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddGroup() {
        Group actual = null;
        Group expected = null;
        try {
            actual = new Group("Test group", false);
            expected = service.addGroup("Test group", true);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateGroupInfo() {
        String actual = null;
        String expected = null;
        try {
            actual = "new name";
            expected = service.updateGroupInfo(1, actual, false).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAddSubject() {
        Subject actual = null;
        Subject expected = null;
        try {
            actual = new Subject("Test subject", "test description", false);
            expected = service.addSubject("Test subject", "test description", false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetSubjectById() {
        String actual = null;
        try {
            actual = service.getSubjectById(5).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "Physics";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddProfessor(){
        String actual = null;
        String expected = null;
        try {
            actual = "New name";
            expected = service.addProfessor("New name", 23, 3, false).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetProfessorById(){
        String actual = null;
        String expected = null;
        try {
            actual = "Da Vinci";
            expected = service.getProfessorById(4).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateProfessorInfo() {
        boolean res = false;;
        try {
            res = service.updateProfessorInfo(2, "New name", 23, 3, false);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdateSubjectInfo(){
        boolean res = false;
        try {
            res = service.updateSubjectInfo(1, "New name", "New description", false);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }
}