package exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() {
        super("Argument invalid.");
    }
    public InvalidArgumentException(String message) {
        super(message);
    }
}
