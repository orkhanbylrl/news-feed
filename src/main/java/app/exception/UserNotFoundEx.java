package app.exception;

public class UserNotFoundEx extends RuntimeException{
    public UserNotFoundEx(Exception message) {
        super(message);
    }
}
