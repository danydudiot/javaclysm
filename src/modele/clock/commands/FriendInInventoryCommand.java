package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.FriendInInventoryState;

/**
 * Commande représentant l'action de mettre un ami dans l'inventaire.
 */
public class FriendInInventoryCommand implements Command {
    /**
     * La proie à mettre dans l'inventaire.
     */
    private Prey prey;
    /**
     * Ancienne position X de la proie.
     */
    private int old_x;
    /**
     * Ancienne position Y de la proie.
     */
    private int old_y;
    /**
     * Ancien état de la proie.
     */
    private State old_State;
    /**
     * Indique si la proie a été saisie.
     */
    private boolean hasGrabbed;
    /**
     * Emplacement où la proie sera placée (poche ou épaule).
     */
    private String where;

    /**
     * Constructeur de la classe FriendInInventoryCommand.
     * Initialise les attributs de la commande.
     *
     * @param prey La proie à mettre dans l'inventaire.
     */
    public FriendInInventoryCommand(Prey prey) {
        this.prey = prey;
        this.old_x = prey.getX();
        this.old_y = prey.getY();
        this.old_State = prey.getCurrentState();
        if (prey instanceof Squirrel) {
            this.where = "dans la poche";
        } else {
            this.where = "sur l'épaule";
        }
    }

    /**
     * Exécute la commande pour mettre la proie dans l'inventaire.
     */
    @Override
    public void doCommand() {
        try {
            Inventory.getInstance().add(prey);
            prey.setCurrentState(new FriendInInventoryState(prey));
            Board.getInstance().clearCase(prey.getX(), prey.getY());
            Board.getInstance().logAction(prey.getDisplayName() + " est " + where);
            hasGrabbed = true;
            prey.setHasMoved(true);
        } catch (Exception exception) {
            hasGrabbed = false;
            Board.getInstance().logError(prey.getDisplayName() + " tente d'aller " + where + ", mais l'inventaire est plein...");
        }
    }

    /**
     * Annule la commande pour retirer la proie de l'inventaire et la remettre à sa position initiale.
     */
    @Override
    public void undoCommand() {
        if (hasGrabbed) {
            Inventory.getInstance().remove(prey);
            Board.getInstance().setEntityOnCase(old_x, old_y, prey);
            prey.setCurrentState(old_State);
        }
    }
}
