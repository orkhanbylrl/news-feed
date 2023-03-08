package app.exception;

import jakarta.mail.MessagingException;

public class MessageFailedException extends RuntimeException {
    public MessageFailedException(String s) {
        super(s);
    }
}
