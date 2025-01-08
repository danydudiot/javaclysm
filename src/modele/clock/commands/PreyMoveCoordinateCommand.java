package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;

/**
 * Commande représentant l'action de déplacer une proie vers une nouvelle coordonnée.
 */
public class PreyMoveCoordinateCommand implements Command {
    /**
     * La proie à déplacer.
     */
    private Prey prey;
    /**
     * Le nouveau terrain vers lequel déplacer la proie.
     */
    private Terrain terrain;
    /**
     * L'ancien terrain de la proie.
     */
    private Terrain old_terrain;
    /**
     * L'ancien niveau de faim de la proie.
     */
    private int old_hunger;
    /**
     * L'ancien niveau de peur de la proie.
     */
    private int old_fear;
    /**
     * Le temps passé dans la poche par la proie.
     */
    private int old_pocket;
    /**
     * L'ancien état de la proie.
     */
    private State old_state;

    /**
     * Constructeur de la classe PreyMoveCoordinateCommand.
     * Initialise les attributs de la commande.
     *
     * @param prey    La proie à déplacer.
     * @param terrain Le nouveau terrain vers lequel déplacer la proie.
     */
    public PreyMoveCoordinateCommand(Prey prey, Terrain terrain) {
        this.prey = prey;
        this.terrain = terrain;
        this.old_terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
        this.old_hunger = prey.getHungryCount() + 1;
        this.old_fear = prey.getFearLevel();
        this.old_pocket = prey.getTimeInPocket();
        this.old_state = prey.getCurrentState();
    }

    /**
     * Exécute la commande pour déplacer la proie vers le nouveau terrain.
     */
    @Override
    public void doCommand() {
        if (!old_terrain.equals(terrain)) {
            prey.move(terrain);
        }
        prey.setHasMoved(true);
    }

    /**
     * Commande représentant l'action d'une proie mangeant de la nourriture.
     */
    @Override
    public void undoCommand() {
        if (!old_terrain.equals(terrain)) {
            prey.move(old_terrain);
        }
        prey.setHungryCount(old_hunger);
        prey.setFearLevel(old_fear);
        prey.setTimeInPocket(old_pocket);
        prey.setCurrentState(old_state);
    }


}
