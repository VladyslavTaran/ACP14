package tests;

import db.*;
import exception.NoSuchEntityException;
import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import org.junit.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.file.ProviderNotFoundException;

/**
 * Created by Vlad on 11.10.2016.
 */
public class ServiceTest {
    public static final String PWD = "root";
    public static final String USER = PWD;
    public static final String DB_TO_RESTORE = "homeworkjpa";
    public static final String BKP_PATH = DB_TO_RESTORE + "_bkp.sql";
    private static Service service;
    private static IStudentDAO<Student> daoStudent = null;
    private static IDAO<Group> daoGroup = null;
    private static IDAO<Subject> daoSubject = null;
    private static IDAO<Professor> daoProfessor = null;

    private Student studentActual = null;
    private Group groupActualFirst = null;
    private Group groupActualSecond = null;
    private Professor professorActual = null;
    private Subject subjectActual1 = null;
    private Subject subjectActual2 = null;
    private Subject subjectActual3 = null;
    private Subject subjectActual4 = null;
    private Subject subjectActual5 = null;

    @BeforeClass
    public static void init(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Constants.JPA_UNIT);

        daoStudent = new StudentDAO(factory);
        daoGroup = new GroupDAO(factory);
        daoSubject = new SubjectDAO(factory);
        daoProfessor = new ProfessorDAO(factory);

        try {
            service = new Service(daoStudent, daoGroup, daoSubject, daoProfessor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() {
        groupActualFirst = service.addGroup("group1", false);
        groupActualSecond = service.addGroup("group2", false);

        try {
            service.addStudent("Kolia", groupActualFirst.getId(), true);
            service.addStudent("Vasia", groupActualFirst.getId(), true);
            service.addStudent("Petya", groupActualSecond.getId(), true);
            studentActual = service.addStudent("Stepa", groupActualSecond.getId(), true);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }

        subjectActual1 = service.addSubject("Subject1", "description", true);
        subjectActual2 = service.addSubject("Subject2", "description", true);
        subjectActual3 = service.addSubject("Subject3", "description", true);
        subjectActual4 = service.addSubject("Subject4", "description", true);
        subjectActual5 = service.addSubject("Subject5", "description", true);

        try {
            service.addProfessor("name1", 12, subjectActual1.getId(), true);
            service.addProfessor("name2", 13, subjectActual2.getId(), true);
            service.addProfessor("name3", 23, subjectActual3.getId(), true);
            service.addProfessor("name4", 6, subjectActual4.getId(), true);
            professorActual = service.addProfessor("name5", 8, subjectActual5.getId(), true);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        service.clearDB();
    }

    @Test
    public void testGetAllStudents() {
        int actual = service.getAllStudents().size();
        int expected = 4;
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
        int expected = 5;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetAllProfessors() {
        int actual = service.getAllProfessors().size();
        int expected = 5;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddStudent() {
        Student actual = null;
        Student expected = null;
        try {
            actual = new Student("Test", groupActualFirst, true);
            expected = service.addStudent("Test", groupActualFirst.getId(), true);
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
            expected = service.updateStudentInfo(studentActual.getId(), actual, groupActualFirst.getId(), false).getName();
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
            actual = studentActual.getId();
            expected = service.getStudentById(studentActual.getId()).getId();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetAllStudentsByGroupId() {
        int actual = 0;
        try {
            actual = service.getAllStudentsByGroupId(groupActualFirst.getId()).size();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        int expected = 2;
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testGetGroupById() {
        String actual = null;
        try {
            actual = service.getGroupById(groupActualFirst.getId()).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "group1";
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void testAddGroup() {
        Group actual = null;
        Group expected = null;

        actual = new Group("Test group", false);
        expected = service.addGroup("Test group", false);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateGroupInfo() {
        String actual = null;
        String expected = null;
        try {
            actual = "new name";
            expected = service.updateGroupInfo(groupActualFirst.getId(), actual, false).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAddSubject() {
        Subject actual = null;
        Subject expected = null;

        actual = new Subject("Test subject", "test description", false);
        expected = service.addSubject("Test subject", "test description", false);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testGetSubjectById() {
        String actual = null;
        try {
            actual = service.getSubjectById(subjectActual5.getId()).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        String expected = "Subject5";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testAddProfessor(){
        String actual = null;
        String expected = null;
        try {
            actual = "New name";
            expected = service.addProfessor("New name", 23, subjectActual3.getId(), false).getName();
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
            actual = "name5";
            expected = service.getProfessorById(professorActual.getId()).getName();
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testUpdateProfessorInfo() {
        boolean res = false;;
        try {
            res = service.updateProfessorInfo(professorActual.getId(), "New name", 23, subjectActual3.getId(), false);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }

    @Test
    public void testUpdateSubjectInfo(){
        boolean res = false;
        try {
            res = service.updateSubjectInfo(subjectActual1.getId(), "New name", "New description", false);
        } catch (NoSuchEntityException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(res);
    }
}