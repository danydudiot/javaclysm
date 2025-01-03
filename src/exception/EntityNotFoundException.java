package exception;


/**
 * Exception levée lorsqu'une entité n'est pas trouvée.
 */
public class EntityNotFoundException extends RuntimeException {
    /**
     * Constructeur par défaut de l'exception EntityNotFoundException.
     * Initialise l'exception avec un message par défaut.
     */
    public EntityNotFoundException() {
        super("Entité non trouver.");
    }

    /**
     * Constructeur de l'exception EntityNotFoundException avec un message personnalisé.
     * @param message Le message détaillant la cause de l'exception.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
