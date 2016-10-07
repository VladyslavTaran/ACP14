package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Vlad on 04.10.2016.
 */
public interface IDBController {
    boolean connectToDB();
    void closeConnection();
    ResultSet executeSQLQuery(String sqlQuery) throws SQLException;
}
