package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état de raid d'un serpent (Snake).
 */
public class SnakeRaidState extends RaidState {

    /**
     * Constructeur de la classe SnakeRaidState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public SnakeRaidState(Predator predator) {
        super(predator);
    }

    /**
     * Met à jour l'état du prédateur.
     * Cette méthode est actuellement vide et ne fait rien.
     */
    @Override
    public void updateState() {
    }

    /**
     * Gère le déplacement du prédateur.
     * Si la proie est trouvée, se déplace vers elle. Sinon, utilise la méthode getDefault pour le déplacement par défaut.
     * Si la proie est toujours introuvable, utilise la méthode getDefault avec le terrain interdit.
     */
    @Override
    public void deplacement() {
        Terrain forbidden = Board.getInstance().getAt(predator.getX(), predator.getY());
        Terrain move = getPrey();
        if (move.equals(Board.getInstance().getAt(predator.getX(), predator.getY()))) {
            move = getDefault(null);
            if (getPrey().equals(Board.getInstance().getAt(predator.getX(), predator.getY()))) {
                getDefault(forbidden);
            }
        }
    }
}