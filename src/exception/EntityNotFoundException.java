package exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("Entité non trouver.");
    }
    public EntityNotFoundException(String message) {
        super(message);
    }
}
