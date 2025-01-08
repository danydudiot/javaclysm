package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

/**
 * Classe représentant l'état de repos d'un serpent (Snake).
 */
public class SnakeRestState extends RestState {

    /**
     * Constructeur de la classe SnakeRestState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public SnakeRestState(Predator predator) {
        super(predator, 3);
    }

    /**
     * Obtient le prochain état du prédateur.
     *
     * @return Le prochain état du prédateur, qui est un état de raid.
     */
    @Override
    protected PredatorState getNextState() {
        return new SnakeRaidState(predator);
    }
}