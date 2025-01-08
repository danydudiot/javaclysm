package modele.clock.commands;

import modele.Inventory;
import modele.entity.Entity;
import modele.interaction.Grab;

/**
 * Commande représentant l'action de déposer un objet par le joueur.
 */
public class PlayerDropCommand implements Command {
    /**
     * L'entité à déposer.
     */
    private Entity what;

    /**
     * Constructeur de la classe PlayerDropCommand.
     * Initialise l'entité à déposer.
     *
     * @param what L'entité à déposer.
     */
    public PlayerDropCommand(Entity what) {
        this.what = what;
    }

    /**
     * Exécute la commande pour déposer l'entité.
     */
    @Override
    public void doCommand() {
        Inventory.getInstance().dropItem();
    }

    /**
     * Annule la commande pour reprendre l'entité déposée.
     */
    @Override
    public void undoCommand() {
        Grab grab = new Grab();
        grab.interact(what);
    }
}
