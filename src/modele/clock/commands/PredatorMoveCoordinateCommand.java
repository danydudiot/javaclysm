package modele.clock.commands;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Terrain;

/**
 * Commande représentant l'action de déplacer un prédateur vers une nouvelle coordonnée.
 */
public class PredatorMoveCoordinateCommand implements Command {
    /**
     * Le prédateur à déplacer.
     */
    private Predator predator;
    /**
     * Le nouveau terrain vers lequel déplacer le prédateur.
     */
    private Terrain terrain;
    /**
     * L'ancien terrain du prédateur.
     */
    private Terrain old_terrain;
    /**
     * L'ancien état du prédateur.
     */
    private State old_state;
    /**
     * Le temps passé sous un rocher par le prédateur (si c'est un scorpion).
     */
    private int old_underRock;
    /**
     * l'ancien niveau de fatigue.
     */
    private int old_restLevel;

    /**
     * Constructeur de la classe PredatorMoveCoordinateCommand.
     * Initialise les attributs de la commande.
     *
     * @param predator Le prédateur à déplacer.
     * @param terrain  Le nouveau terrain vers lequel déplacer le prédateur.
     */
    public PredatorMoveCoordinateCommand(Predator predator, Terrain terrain) {
        this.predator = predator;
        this.terrain = terrain;
        this.old_terrain = Board.getInstance().getAt(predator.getX(), predator.getY());
        this.old_state = predator.getCurrentState();
        this.old_restLevel = predator.getRestLevel();
        if (predator instanceof Scorpio scorpio) {
            this.old_underRock = scorpio.getTimeUnderRock();
        }
    }

    /**
     * Exécute la commande pour déplacer le prédateur vers le nouveau terrain.
     */
    @Override
    public void doCommand() {
        if (!old_terrain.equals(terrain)) {
            predator.move(terrain);
        }
    }

    /**
     * Commande représentant l'action d'un prédateur attaquant mortellement une proie.
     */
    @Override
    public void undoCommand() {
        predator.setCurrentState(old_state);
        predator.setRestLevel(old_restLevel);
        if (!old_terrain.equals(terrain)) {
            predator.move(old_terrain);
        }
        if (predator instanceof Scorpio scorpio) {
            scorpio.setTimeUnderRock(old_underRock);
        }
    }


}
