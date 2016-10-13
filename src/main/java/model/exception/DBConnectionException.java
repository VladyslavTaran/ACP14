package model.exception;

/**
 * Created by Vlad on 08.10.2016.
 */
public class DBConnectionException extends Throwable {
    public DBConnectionException(String message) {
        super(message);
    }
}
