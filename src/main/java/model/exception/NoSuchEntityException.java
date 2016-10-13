package model.exception;

/**
 * Created by Vlad on 08.10.2016.
 */
public class NoSuchEntityException extends Throwable {
    public NoSuchEntityException(String message) {
        super(message);
    }
}
