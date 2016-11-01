package tests;

import db.*;
import exception.NoSuchEntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Vlad on 11.10.2016.
 */
public class ServiceTest {
    public static final String PWD = "root";
    public static final String USER = PWD;
    public static final String DB_TO_RESTORE = "homework";
    public static final String BKP_PATH = DB_TO_RESTORE + "_bkp.sql";
    private DAOStudent controller;
    private Service service;

    private void restoreDB(){
        try {
            Process runtimeProcess = Runtime.getRuntime().exec("mysql -u" + USER + " -p" + PWD + " " + DB_TO_RESTORE + " < " + BKP_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
        restoreDB();
        DAOStudent daoStudent = new DAOStudent();
        DAOGroup daoGroup = new DAOGroup();
        DAOSubject daoSubject = new DAOSubject();
        DAOProfessor daoProfessor = new DAOProfessor();
        service = new Service(daoStudent,daoGroup,daoSubject,daoProfessor);
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
        service = null;
    }

    @Test
    public void testGetAllStudents() throws Exception {
        int actual = service.getAllStudents().size();
        int expected = 45;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllGroups() throws Exception {
        int actual = service.getAllGroups().size();
        int expected = 6;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllSubjects() throws Exception {
        int actual = service.getAllSubjects().size();
        int expected = 15;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllProfessors() throws Exception {
        int actual = service.getAllProfessors().size();
        int expected = 14;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddStudent() throws Exception {
        boolean actual = false;
        try {
            actual = service.addStudent("testName",3,true);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void testUpdateStudentInfo() throws Exception {
        boolean actual = false;
        try {
            actual = service.updateStudentInfo(12,"testName",1,true);
        } catch (WrongDataException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(actual);
    }

    @Test
    public void testGetStudentById() throws Exception {
        try {
            String actual = service.getStudentById(10).getName();
            String expected = "Dmytro";
            Assert.assertEquals(expected,actual);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllStudentsByGroupId() throws Exception {
        int actual = 0;
        try {
            actual = service.getAllStudentsByGroupId(1).size();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        int expected = 14;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetGroupById() throws Exception {
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
    public void testAddGroup() throws Exception {
        boolean res = false;
        try {
            res = service.addGroup("new group", false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdateGroupInfo() throws Exception {
        boolean res = false;
        try {
            res = service.updateGroupInfo(2,"updated name",false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testAddSubject() throws Exception {
        boolean res = false;
        try {
            res = service.addSubject("new subject", "description", false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testGetSubjectById() throws Exception {
        String actual = null;
        try {
            actual = service.getSubjectById(5).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "Art";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddProfessor() throws Exception {
        boolean res = false;
        try {
            res = service.addProfessor("prof name",23,2,false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testGetProfessorById() throws Exception {
        String actual = null;
        try {
            actual = service.getProfessorById(3).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "Kant";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testUpdateProfessorInfo() throws Exception {
        boolean res = false;
        try {
            res = service.updateProfessorInfo(3,"new prof name",55,3,false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdateSubjectInfo() throws Exception {
        boolean res = false;
        try {
            res = service.updateSubjectInfo(3,"updated subj name","updated description",false);
        } catch (WrongDataException e) {
            e.printStackTrace();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }
}