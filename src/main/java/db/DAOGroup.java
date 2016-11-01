package db;

import model.Group;
import exception.NoSuchEntityException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vlad on 04.10.2016.
 */
public class DAOGroup {

    private Connection connection = null;
    private ResultSet resultset = null;
    private PreparedStatement preparedStatement = null;
    private Properties properties;
    private ConnectionFactory conFactory;

    public DAOGroup() {
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

    public List<Group> getAllGroups() throws SQLException, DBConnectionException, WrongDataException {
        List<Group> groups = new ArrayList<>();

        connection = conFactory.connectToDB();
        resultset = executeSQLQuery(Constants.GET_ALL_GROUPS);
        if (resultset != null) {
            while (resultset.next()) {
                groups.add(new Group(
                                    resultset.getString(Constants.FIELD_NAME),
                                    resultset.getBoolean(Constants.FIELD_ACTIVE)
                                    )
                          );
            }
        }

        if (resultset != null){
            resultset.close();
        }
        conFactory.closeConnection();

        return groups;
    }

    public Group getGroupById(int groupId) throws NoSuchEntityException, DBConnectionException, SQLException {
        Group group = null;

        try {
            connection = conFactory.connectToDB();
            PreparedStatement preparedStatement = connection.prepareStatement(Constants.GET_GROUP_BY_ID);
            preparedStatement.setInt(1, groupId);

            resultset = preparedStatement.executeQuery();

            if (resultset != null) {
                resultset.next();
                group = new Group(
                        resultset.getString(Constants.FIELD_NAME),
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
        return group;
    }

    public boolean addGroup(String name, boolean isActive) throws SQLException, DBConnectionException {
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.INSERT_GROUP);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, (isActive ? 1 : 0));
        preparedStatement.execute();

        conFactory.closeConnection();
        return true;
    }

    public boolean updateGroup(int id, String name, boolean isActive) throws SQLException, DBConnectionException {
        connection = conFactory.connectToDB();

        PreparedStatement preparedStatement = connection.prepareStatement(Constants.UPDATE_GROUP);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, (isActive ? 1 : 0));
        preparedStatement.setInt(3, id);
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
