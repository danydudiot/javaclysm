package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.interaction.Hit;

/**
 * Commande représentant l'action de frapper une entité.
 */
public class InteractionHitCommand implements Command {
    /**
     * La cible à frapper.
     */
    private NonPlayerCharacter target;
    /**
     * Ancien état de la cible.
     */
    private State old_state;
    /**
     * Ancien niveau d'amitié de la cible (si c'est une proie).
     */
    private int old_friendLevel;
    /**
     * Interaction de frappe.
     */
    private Hit interaction;

    /**
     * Constructeur de la classe InteractionHitCommand.
     * Initialise les attributs de la commande.
     *
     * @param target      La cible à frapper.
     * @param interaction L'interaction de frappe.
     */
    public InteractionHitCommand(NonPlayerCharacter target, Hit interaction) {
        this.target = target;
        this.interaction = interaction;
        this.old_state = target.getCurrentState();
        if (target instanceof Prey) {
            this.old_friendLevel = ((Prey) target).getFriendLevel();
        }
    }

    /**
     * Exécute la commande pour frapper la cible.
     */
    @Override
    public void doCommand() {
        this.interaction.interact(target);
    }

    /**
     * Annule la commande pour restaurer l'état initial de la cible.
     */
    @Override
    public void undoCommand() {
        if (target instanceof Prey) {
            ((Prey) target).setFriendLevel(old_friendLevel);
        } else {
            Board.getInstance().setEntityOnCase(target.getX(), target.getY(), target);
            target.setCurrentState(old_state);
        }
    }
}
