package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état de raid d'un renard (Fox).
 */
public class FoxRaidState extends RaidState {
    /**
     * Constructeur de la classe FoxRaidState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public FoxRaidState(Predator predator) {
        super(predator);
    }

    /**
     * Vérifie si le prédateur peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le prédateur peut se déplacer sur le terrain, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain instanceof Empty && terrain.isEmpty();
    }

    /**
     * Met à jour l'état du prédateur.
     * Cette méthode est actuellement vide et ne fait rien.
     */
    @Override
    public void updateState() {
    }
}