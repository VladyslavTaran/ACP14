package db;

import model.Professor;
import exception.DBConnectionException;
import exception.WrongDataException;
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
public class DAOProfessor {

    private Connection connection = null;
    private ResultSet resultset = null;
    private PreparedStatement preparedStatement = null;
    private Properties properties;
    private ConnectionFactory conFactory;

    public DAOProfessor() {
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

    public List<Professor> getAllProfessors() throws DBConnectionException, WrongDataException, SQLException {
        List<Professor> professors = new ArrayList<>();

        connection = conFactory.connectToDB();
        resultset = executeSQLQuery(Constants.GET_ALL_PROFESSORS);
        if (resultset != null) {
            while (resultset.next()) {
                professors.add(new Professor(
                                resultset.getInt(Constants.FIELD_ID),
                                resultset.getString(Constants.FIELD_NAME),
                                resultset.getInt(Constants.FIELD_EXPERIENCE),
                                new Subject(resultset.getInt(Constants.FIELD_SUBJECT),
                                            "new subject",
                                            "new description",
                                            false),
                                resultset.getBoolean(Constants.FIELD_ACTIVE)
                        )
                );
            }
        }

        if (resultset != null){
            resultset.close();
        }
        conFactory.closeConnection();

        return professors;
    }

    public boolean addProfessor(String name, int experience, int subject_id, boolean isActive) throws SQLException, DBConnectionException {
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_PROFESSOR);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, experience);
        preparedStatement.setInt(3, subject_id);
        preparedStatement.setInt(4, (isActive ? 1 : 0));
        preparedStatement.execute();

        conFactory.closeConnection();

        return true;
    }

    public boolean updateProfessor(int id, String name, int experience, int subject_id, boolean isActive) throws SQLException, DBConnectionException {
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_PROFESSOR);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, experience);
        preparedStatement.setInt(3, subject_id);
        preparedStatement.setInt(4, (isActive ? 1 : 0));
        preparedStatement.setInt(5, id);
        preparedStatement.execute();

        if (preparedStatement != null){
            preparedStatement.close();
        }
        conFactory.closeConnection();
        return true;
    }

    public Professor getProfessorById(int professorId) throws SQLException, DBConnectionException {
        Professor professor = null;
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_PROFESSOR_BY_ID);
        preparedStatement.setInt(1, professorId);
        resultset = preparedStatement.executeQuery();

        if (resultset != null) {
            resultset.next();
            professor = new Professor(
                    resultset.getInt(Constants.FIELD_ID),
                    resultset.getString(Constants.FIELD_NAME),
                    resultset.getInt(Constants.FIELD_EXPERIENCE),
                    new Subject(resultset.getInt(Constants.FIELD_SUBJECT),
                            "new subject",
                            "new description",
                            false),
                    resultset.getBoolean(Constants.FIELD_ACTIVE)
            );
        }

        if (preparedStatement != null){
            preparedStatement.close();
        }

        conFactory.closeConnection();
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
