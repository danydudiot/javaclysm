package modele.clock.commands;

/**
 * Historise le tour actuel en l'ajoutant à la liste des tours joués.
 */
public interface Command {
    /**
     * Exécute la commande.
     */
    void doCommand();

    /**
     * Annule la commande.
     */
    void undoCommand();
}
