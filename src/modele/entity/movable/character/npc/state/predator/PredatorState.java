package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;

public abstract class PredatorState implements State {
    protected Predator predator;

    public PredatorState(Predator predator) {
        this.predator = predator;
    }
}
