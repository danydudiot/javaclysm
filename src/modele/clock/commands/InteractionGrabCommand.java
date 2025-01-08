package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.InventoryItem;
import modele.entity.Entity;
import modele.interaction.Grab;

/**
 * Commande représentant l'action de saisir une entité.
 */
public class InteractionGrabCommand implements Command {
    /**
     * L'entité à saisir.
     */
    private Entity what;
    /**
     * Ancienne position X de l'entité.
     */
    private int old_x;
    /**
     * Ancienne position Y de l'entité.
     */
    private int old_y;
    /**
     * Interaction de saisie.
     */
    private Grab interaction;
    /**
     * Indique si l'entité a été saisie.
     */
    private boolean hasGrab;

    /**
     * Constructeur de la classe InteractionGrabCommand.
     * Initialise les attributs de la commande.
     *
     * @param what        L'entité à saisir.
     * @param interaction L'interaction de saisie.
     */
    public InteractionGrabCommand(Entity what, Grab interaction) {
        this.what = what;
        this.old_x = what.getX();
        this.old_y = what.getY();
        this.interaction = interaction;
    }

    /**
     * Exécute la commande pour saisir l'entité.
     */
    @Override
    public void doCommand() {
        hasGrab = !Inventory.getInstance().isFull();
        if (hasGrab) {
            interaction.interact(what);
        }
    }

    /**
     * Annule la commande pour retirer l'entité de l'inventaire et la remettre à sa position initiale.
     */
    @Override
    public void undoCommand() {
        if (hasGrab) {
            Inventory.getInstance().remove((InventoryItem) what);
            Board.getInstance().setEntityOnCase(old_x, old_y, what);
        }
    }
}