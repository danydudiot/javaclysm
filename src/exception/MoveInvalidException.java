package exception;

public class MoveInvalidException extends RuntimeException {
    public MoveInvalidException() {
        super("Déplacement invalid.");
    }
    public MoveInvalidException(String message) {
        super(message);
    }
}
