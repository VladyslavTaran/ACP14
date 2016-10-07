import db.DBController;
import db.Deanery;
import db.IDBController;
import model.Group;
import model.Professor;
import model.Student;
import model.Subject;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 03.10.2016.
 */
public class RunApp {
    public static void main(String[] args) {
        DBController controller = new DBController();
        controller.connectToDB();
        //List<Student> students = controller.getAllStudents();
        //List<Group> groups = controller.getAllGroups();
        //List<Subject> subjects = controller.getAllSubjects();
        //List<Professor> professors = controller.getAllProfessors();
        /*try {
            controller.updateGroup(2,"ACB14");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        //controller.updateProfessor(13, "Dante", 12, 14, true);
        //controller.addGroup("ACP11", false);
        //controller.addSubject("Culture", "Culture of various countries", true);
        Deanery deanery = new Deanery(controller);
        /*try {
            deanery.updateSubjectInfo(9,"Biology","Flora and fauna",false);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        List<Student> students = deanery.getStudentsByGroupId(4);

        students.forEach(System.out::println);

        controller.closeConnection();

        /*for (Student student : students) {
            System.out.println(student);
        }

        for (Group group : groups) {
            System.out.println(group);
        }

        for (Subject subject : subjects) {
            System.out.println(subject);
        }

        for (Professor professor : professors) {
            System.out.println(professor);
        }*/
    }
}
