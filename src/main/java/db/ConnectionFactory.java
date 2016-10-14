package db;

import exception.DBConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Vlad on 14.10.2016.
 */
public class ConnectionFactory {
    private Connection connection;
    private Properties properties;

    public ConnectionFactory(Properties properties) {
        this.properties = properties;
    }

    public Connection connectToDB() throws DBConnectionException {
        try {
            Class.forName(properties.getProperty(Constants.PROP_JDBC));
            connection =
                    DriverManager.
                            getConnection
                                    (properties.getProperty(Constants.PROP_URL),
                                            properties.getProperty(Constants.PROP_USER),
                                            properties.getProperty(Constants.PROP_PWD));
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_NOT_VALID + e.getMessage());
        } catch (SQLException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_NOT_VALID + e.getMessage());
        }
    }

    public void closeConnection() throws DBConnectionException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DBConnectionException(Constants.CONNECTION_DETAILS_ARE_NOT_VALID);
        }
    }

}
