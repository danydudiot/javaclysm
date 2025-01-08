package exception;

/**
 * Exception levée lorsqu'un déplacement est invalide.
 */
public class MoveInvalidException extends RuntimeException {

    /**
     * Constructeur par défaut de l'exception MoveInvalidException.
     * Initialise l'exception avec un message par défaut.
     */
    public MoveInvalidException() {
        super("Déplacement invalid.");
    }

    /**
     * Constructeur de l'exception MoveInvalidException avec un message personnalisé.
     *
     * @param message Le message détaillant la cause de l'exception.
     */
    public MoveInvalidException(String message) {
        super(message);
    }
}