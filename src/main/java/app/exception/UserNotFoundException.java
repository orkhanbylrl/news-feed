package app.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Exception message) {
        super(message);
    }
}
