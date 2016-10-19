package db;

import model.Subject;
import exception.DBConnectionException;
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
public class DAOSubject {

    private Connection connection = null;
    private ResultSet resultset = null;
    private Properties properties;
    private ConnectionFactory conFactory;

    public DAOSubject() {
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

    public List<Subject> getAllSubjects() throws DBConnectionException, SQLException, WrongDataException {
        List<Subject> subjects = new ArrayList<>();

        connection = conFactory.connectToDB();
        resultset = executeSQLQuery(Constants.GET_ALL_SUBJECTS);

        if (resultset != null) {
            while (resultset.next()) {
                subjects.add(new Subject(
                                resultset.getString(Constants.FIELD_NAME),
                                resultset.getString(Constants.FIELD_DESCRIPTION),
                                resultset.getBoolean(Constants.FIELD_ACTIVE)
                        )
                );
            }
        }

        if (resultset != null){
            resultset.close();
        }
        conFactory.closeConnection();
        return subjects;
    }

    public boolean addSubject(String name, String description, boolean isActive) throws SQLException, DBConnectionException {

        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement =  connection.prepareStatement(Constants.INSERT_SUBJECT);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, (isActive ? 1 : 0));
        preparedStatement.execute();

        conFactory.closeConnection();
        return true;
    }

    public Subject getSubjectById(int subjectId) throws SQLException, DBConnectionException {
        Subject subject = null;

        connection = conFactory.connectToDB();
        PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_SUBJECT_BY_ID);
        preparedStatement.setInt(1, subjectId);

        resultset = preparedStatement.executeQuery();

        if (resultset != null) {
            resultset.next();
            subject = new Subject(
                    resultset.getString(Constants.FIELD_NAME),
                    resultset.getString(Constants.FIELD_DESCRIPTION),
                    resultset.getBoolean(Constants.FIELD_ACTIVE)
            );
        }
        conFactory.closeConnection();

        return subject;
    }

    public boolean updateSubject(int id, String name, String description, boolean isActive) throws SQLException, DBConnectionException {
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_SUBJECT);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, description);
        preparedStatement.setInt(3, (isActive ? 1 : 0));
        preparedStatement.setInt(4, id);
        preparedStatement.execute();

        conFactory.closeConnection();
        return true;
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
