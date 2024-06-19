package itacademy.misbackend.exception;

public class ConfirmationCodeMismatchException extends RuntimeException{
    public ConfirmationCodeMismatchException(String message) {
        super(message);
    }
}
