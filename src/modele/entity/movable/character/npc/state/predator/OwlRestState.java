package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

/**
 * Classe représentant l'état de repos d'un hibou (Owl).
 */
public class OwlRestState extends RestState {
    /**
     * Constructeur de la classe OwlRestState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public OwlRestState(Predator predator) {
        super(predator, 1);
    }

    /**
     * Obtient le prochain état du prédateur.
     *
     * @return Le prochain état du prédateur, qui est un état de raid.
     */
    protected PredatorState getNextState() {
        return new OwlRaidState(predator);
    }
}