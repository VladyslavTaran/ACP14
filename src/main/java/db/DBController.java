package db;

import model.Group;
import model.Professor;
import model.Student;
import model.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vlad on 04.10.2016.
 */
public class DBController implements IDBController{
    private Connection connection = null;
    private ResultSet resultset = null;
    private Properties properties;

    public DBController() {
        loadProperties();
    }

    @Override
    public boolean connectToDB() {
        try {
            Class.forName(properties.getProperty(Constants.PROP_JDBC));
            connection =
                    DriverManager.
                            getConnection
                                    (properties.getProperty(Constants.PROP_URL),
                                     properties.getProperty(Constants.PROP_USER),
                                     properties.getProperty(Constants.PROP_PWD));
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void closeConnection() {
        try {
            if (resultset != null) {
                resultset.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeSQLQuery(String sqlQuery) throws SQLException {
        try {
            resultset = connection.createStatement().executeQuery(sqlQuery);
            return resultset;
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();

        try {
            resultset = executeSQLQuery(Constants.GET_ALL_STUDENTS);
            if (resultset != null) {
                while (resultset.next()) {
                    students.add(new Student(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getInt(Constants.FIELD_GROUP_ID),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return students;
    }

    public List<Group> getAllGroups(){
        List<Group> groups = new ArrayList<>();

        try {
            resultset = executeSQLQuery(Constants.GET_ALL_GROUPS);
            if (resultset != null) {
                while (resultset.next()) {
                    groups.add(new Group(
                                        resultset.getInt(Constants.FIELD_ID),
                                        resultset.getString(Constants.FIELD_NAME),
                                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                                        )
                              );
                }
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return groups;
    }

    public List<Subject> getAllSubjects(){
        List<Subject> subjects = new ArrayList<>();

        try {
            resultset = executeSQLQuery(Constants.GET_ALL_SUBJECTS);
            if (resultset != null) {
                while (resultset.next()) {
                    subjects.add(new Subject(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getString(Constants.FIELD_DESCRIPTION),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return subjects;
    }

    public List<Professor> getAllProfessors(){
        List<Professor> professors = new ArrayList<>();

        try {
            resultset = executeSQLQuery(Constants.GET_ALL_PROFESSORS);
            if (resultset != null) {
                while (resultset.next()) {
                    professors.add(new Professor(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getInt(Constants.FIELD_EXPERIENCE),
                                    resultset.getString(Constants.FIELD_SUBJECT),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return professors;
    }

    public void addStudent(String name, int group_id, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_STUDENT);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, group_id);
        preparedStatement.setInt(3, (isActive ? 1 : 0));

        preparedStatement.execute();
    }

    public void updateStudent(int id, String name, int group_id, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_STUDENT);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, group_id);
        preparedStatement.setInt(3, (isActive ? 1 : 0));
        preparedStatement.setInt(4, id);

        preparedStatement.execute();
    }

    public Student getStudentById(int studentId){
        Student student = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_STUDENT_BY_ID);
            preparedStatement.setInt(1, studentId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                student = new Student(
                                        resultset.getInt(Constants.FIELD_ID),
                                        resultset.getString(Constants.FIELD_NAME),
                                        resultset.getInt(Constants.FIELD_GROUP_ID),
                                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                                     );

            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return student;
    }

    public List<Student> getAllStudentByGroupId(int groupId){
        List<Student> students = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_ALL_STUDENTS_BY_GROUP_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                while(resultset.next()) {
                    students.add(new Student(
                                            resultset.getInt(Constants.FIELD_ID),
                                            resultset.getString(Constants.FIELD_NAME),
                                            resultset.getInt(Constants.FIELD_GROUP_ID),
                                            resultset.getBoolean(Constants.FIELD_ACTIVE)
                                            )
                                );
                };
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return students;
    }

    public Group getGroupById(int groupId){
        Group group = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_GROUP_BY_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                group = new Group(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );

            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return group;
    }

    public void addGroup(String name, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_GROUP);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, (isActive ? 1 : 0));

        preparedStatement.execute();
    }

    public void updateGroup(int id, String name, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_GROUP);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, (isActive ? 1 : 0));
        preparedStatement.setInt(3, id);

        preparedStatement.execute();
    }

    public void addSubject(String name, String description, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement(Constants.INSERT_SUBJECT);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, (isActive ? 1 : 0));

        preparedStatement.execute();
    }

    public Subject getSubjectById(int subjectId){
        Subject subject = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_SUBJECT_BY_ID);
            preparedStatement.setInt(1, subjectId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                subject = new Subject(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getString(Constants.FIELD_DESCRIPTION),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return subject;
    }

    public void updateSubject(int id, String name, String description, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_SUBJECT);

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, (isActive ? 1 : 0));
        preparedStatement.setInt(4, id);

        preparedStatement.execute();
    }

    public void addProfessor(String name, int experience, int subject_id, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement(Constants.INSERT_PROFESSOR);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, experience);
        preparedStatement.setInt(3, subject_id);
        preparedStatement.setInt(4, (isActive ? 1 : 0));

        preparedStatement.execute();
    }

    public void updateProfessor(int id, String name, int experience, int subject_id, boolean isActive) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_PROFESSOR);

        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, experience);
        preparedStatement.setInt(3, subject_id);
        preparedStatement.setInt(4, (isActive ? 1 : 0));
        preparedStatement.setInt(5, id);

        preparedStatement.execute();
    }

    public Professor getProfessorById(int professorId){
        Professor professor = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_PROFESSOR_BY_ID);
            preparedStatement.setInt(1, professorId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                professor = new Professor(
                        resultset.getInt(Constants.FIELD_ID),
                        resultset.getString(Constants.FIELD_NAME),
                        resultset.getInt(Constants.FIELD_EXPERIENCE),
                        resultset.getString(Constants.FIELD_SUBJECT),
                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                );
            }
        } catch (SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
        return professor;
    }

    private void loadProperties(){
        properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(Constants.DB_PROPERTIES);

        if (inputStream != null) {
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
