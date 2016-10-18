package db;

import model.Group;
import model.Student;
import exception.DBConnectionException;
import exception.NoSuchEntityException;
import exception.WrongDataException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vlad on 04.10.2016.
 */
public class DAOStudent {

    private Connection connection = null;
    private ResultSet resultset = null;
    private PreparedStatement preparedStatement = null;
    private Properties properties;
    private ConnectionFactory conFactory;

    public DAOStudent() {
        loadProperties();
        conFactory = new ConnectionFactory(properties);
    }

    private ResultSet executeSQLQuery(String sqlQuery) throws WrongDataException {
        try {
            resultset = connection.createStatement().executeQuery(sqlQuery);
            return resultset;
        } catch (SQLException e) {
            throw new WrongDataException(Constants.WRONG_DATA_MESSAGE);
        }
    }

    public List<Student> getAllStudents() throws SQLException, WrongDataException, DBConnectionException {
        List<Student> students = new ArrayList<>();

        try {
            connection = conFactory.connectToDB();
            resultset = executeSQLQuery(Constants.GET_ALL_STUDENTS);
            if (resultset != null) {
                while (resultset.next()) {
                    students.add(new Student(
                                    resultset.getInt(Constants.FIELD_ID),
                                    resultset.getString(Constants.FIELD_NAME),
                                    new Group(resultset.getInt(Constants.FIELD_GROUP_ID),
                                            "group name",null,false),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                            )
                    );
                }
            }
        } finally {
            try {
                if (resultset != null){
                    resultset.close();
                }
                conFactory.closeConnection();
            } catch (DBConnectionException e) {
                throw e;
            }
        }
        return students;
    }

    public boolean addStudent(String name, int group_id, boolean isActive) throws DBConnectionException, SQLException {
        try {
            connection = conFactory.connectToDB();
            preparedStatement = connection.prepareStatement(Constants.INSERT_STUDENT);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, (isActive ? 1 : 0));

            preparedStatement.execute();
        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                conFactory.closeConnection();
            } catch (DBConnectionException e) {
                throw e;
            }
        }
        return true;
    }

    public boolean updateStudent(int id, String name, int group_id, boolean isActive) throws SQLException, DBConnectionException {
        try {
            connection = conFactory.connectToDB();
            preparedStatement = connection.prepareStatement(Constants.UPDATE_STUDENT);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, group_id);
            preparedStatement.setInt(3, (isActive ? 1 : 0));
            preparedStatement.setInt(4, id);

            preparedStatement.execute();
        } catch (DBConnectionException e) {
            return false;
        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                conFactory.closeConnection();
            } catch (DBConnectionException e) {
                throw e;
            }
        }
        return true;
    }

    public Student getStudentById(int studentId) throws NoSuchEntityException, DBConnectionException, SQLException {
        Student student = null;

        try {
            connection = conFactory.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_STUDENT_BY_ID);
            preparedStatement.setInt(1, studentId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                student = new Student(
                                        resultset.getInt(Constants.FIELD_ID),
                                        resultset.getString(Constants.FIELD_NAME),
                                        new Group(resultset.getInt(Constants.FIELD_GROUP_ID),
                                                "group name",null,false),
                                        resultset.getBoolean(Constants.FIELD_ACTIVE)
                                     );

            }
        } catch (SQLException e) {
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                conFactory.closeConnection();
            } catch (DBConnectionException e) {
                throw e;
            }
        }
        return student;
    }

    public List<Student> getAllStudentByGroupId(int groupId) throws DBConnectionException, NoSuchEntityException {
        List<Student> students = new ArrayList<>();

        try {
            connection = conFactory.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_ALL_STUDENTS_BY_GROUP_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                while(resultset.next()) {
                    students.add(new Student(
                                            resultset.getInt(Constants.FIELD_ID),
                                            resultset.getString(Constants.FIELD_NAME),
                                            new Group(resultset.getInt(Constants.FIELD_GROUP_ID),
                                                    "group name",null,false),
                                            resultset.getBoolean(Constants.FIELD_ACTIVE)
                                            )
                                );
                };
            }
        } catch (SQLException e) {
            throw new NoSuchEntityException(Constants.NO_ID_IN_DB + e.getMessage());
        } finally {
            try {
                conFactory.closeConnection();
            } catch (DBConnectionException e) {
                throw e;
            }
        }
        return students;
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
