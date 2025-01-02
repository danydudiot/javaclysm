package exception;

/**
 * Exception levée lorsqu'une action invalide ou inconnue est effectuée.
 */
public class InvalidActionException extends RuntimeException {
    /**
     * Constructeur de l'exception InvalidActionException avec un message personnalisé.
     * @param message Le message détaillant la cause de l'exception.
     */
    public InvalidActionException(String message) {
        super(message);
    }
}
