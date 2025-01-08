package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.DeadState;
import modele.entity.movable.character.npc.state.State;

/**
 * Commande représentant l'action d'un prédateur attaquant mortellement une proie.
 */
public class PredatorFatalAttackCommand implements Command {
    /**
     * Le prédateur qui attaque.
     */
    private Predator predator;
    /**
     * L'ancien état du prédateur.
     */
    private State old_predatorState;
    /**
     * La proie attaquée.
     */
    private Prey prey;
    /**
     * L'ancien état de la proie.
     */
    private State old_preyState;
    /**
     * L'ancienne position X de la proie.
     */
    private int old_preyX;
    /**
     * L'ancienne position Y de la proie.
     */
    private int old_preyY;
    /**
     * Indique si le prédateur peut attaquer.
     */
    private int canAttack;

    /**
     * Constructeur de la classe PredatorFatalAttackCommand.
     * Initialise les attributs de la commande.
     *
     * @param predator Le prédateur qui attaque.
     * @param prey     La proie attaquée.
     */
    public PredatorFatalAttackCommand(Predator predator, Prey prey) {
        this.predator = predator;
        this.old_predatorState = predator.getCurrentState();

        this.prey = prey;
        this.old_preyState = prey.getCurrentState();
        this.old_preyX = prey.getX();
        this.old_preyY = prey.getY();
        if (predator instanceof Scorpio scorpio) {
            this.canAttack = scorpio.getCanAttack();
        }
    }

    /**
     * Exécute la commande pour que le prédateur attaque mortellement la proie.
     */
    @Override
    public void doCommand() {
        //Board.getInstance().clearCase(prey.getX(), prey.getY()); // pas besoin car la prey s'est retirer de la case
        prey.setCurrentState(new DeadState(prey));
        predator.afterHit(true);
        Board.getInstance().logAction(predator.getDisplayName() + " à tué " + prey.getDisplayName());
    }

    /**
     * Commande représentant l'action d'un prédateur attaquant une proie.
     */
    @Override
    public void undoCommand() {
        Board.getInstance().setEntityOnCase(old_preyX, old_preyY, prey);
        prey.setCurrentState(old_preyState);
        predator.setCurrentState(old_predatorState);
        if (predator instanceof Scorpio scorpio) {
            scorpio.setCanAttack(canAttack);
        }
    }
}
