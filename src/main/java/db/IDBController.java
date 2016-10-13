package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;
import model.exception.DBConnectionException;
import model.exception.NoSuchEntityException;
import model.exception.WrongDataException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vlad on 04.10.2016.
 */
public interface IDBController {

    List<Student> getAllStudents() throws DBConnectionException, WrongDataException;

    List<Group> getAllGroups() throws DBConnectionException, WrongDataException;

    List<Subject> getAllSubjects();

    List<Professor> getAllProfessors();

    boolean addStudent(String name, int group_id, boolean isActive) throws SQLException, WrongDataException;

    boolean updateStudent(int id, String name, int group_id, boolean isActive) throws SQLException, WrongDataException, DBConnectionException;

    Student getStudentById(int studentId) throws SQLException, NoSuchEntityException;

    List<Student> getAllStudentByGroupId(int groupId) throws SQLException;

    Group getGroupById(int groupId) throws SQLException, NoSuchEntityException;

    boolean addGroup(String name, boolean isActive) throws SQLException, DBConnectionException;

    boolean updateGroup(int id, String name, boolean isActive) throws SQLException;

    boolean addSubject(String name, String description, boolean isActive) throws SQLException;

    Subject getSubjectById(int subjectId) throws SQLException;

    boolean updateSubject(int id, String name, String description, boolean isActive) throws SQLException;

    boolean addProfessor(String name, int experience, int subject_id, boolean isActive) throws SQLException;

    boolean updateProfessor(int id, String name, int experience, int subject_id, boolean isActive) throws SQLException;

    Professor getProfessorById(int professorId) throws SQLException;
}
