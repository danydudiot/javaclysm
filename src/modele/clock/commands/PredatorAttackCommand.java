package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;

/**
 * Commande représentant l'action d'un prédateur attaquant une proie.
 */
public class PredatorAttackCommand implements Command {
    /**
     * Le prédateur qui attaque.
     */
    private Predator predator;
    /**
     * L'ancien état du prédateur.
     */
    private State old_predatorState;
    /**
     * L'ancienne position X du prédateur.
     */
    private int old_predatorX;
    /**
     * L'ancienne position Y du prédateur.
     */
    private int old_predatorY;
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
     * Indique si la proie a été tuée.
     */
    private boolean wasKilled;
    /**
     * Indique si le prédateur peut attaquer.
     */
    private int canAttack;
    /**
     * Le niveau de faim de la proie.
     */
    private int hungryCount;

    /**
     * Constructeur de la classe PredatorAttackCommand.
     * Initialise les attributs de la commande.
     *
     * @param predator Le prédateur qui attaque.
     * @param prey     La proie attaquée.
     */
    public PredatorAttackCommand(Predator predator, Prey prey) {
        this.predator = predator;
        this.old_predatorState = predator.getCurrentState();
        this.old_predatorX = predator.getX();
        this.old_predatorY = predator.getY();
        this.prey = prey;
        this.hungryCount = prey.getHungryCount() + 1;
        this.old_preyState = prey.getCurrentState();
        this.old_preyX = prey.getX();
        this.old_preyY = prey.getY();
        if (predator instanceof Scorpio scorpio) {
            this.canAttack = scorpio.getCanAttack();
        }
    }

    /**
     * Exécute la commande pour que le prédateur attaque la proie.
     */
    @Override
    public void doCommand() {
        this.wasKilled = prey.hit(predator);
        if (wasKilled) {
            Board.getInstance().logAction(predator.getDisplayName() + " à tué " + prey.getDisplayName());
        }
        prey.setHasMoved(true);
    }

    /**
     * Annule la commande pour restaurer l'état initial du prédateur et de la proie.
     */
    @Override
    public void undoCommand() {
        predator.setCurrentState(old_predatorState);
        Board.getInstance().moveTo(predator, old_predatorX, old_predatorY);
        if (wasKilled) {
            Board.getInstance().setEntityOnCase(old_preyX, old_preyY, prey);
        }
        prey.setCurrentState(old_preyState);
        prey.setHungryCount(hungryCount);
        if (predator instanceof Scorpio scorpio) {
            scorpio.setCanAttack(canAttack);
        }
    }
}
