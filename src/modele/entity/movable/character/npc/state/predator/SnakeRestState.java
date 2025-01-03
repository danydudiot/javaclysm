package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

public class SnakeRestState extends RestState {
    public SnakeRestState(Predator predator) {
        super(predator, 3);
    }


    @Override
    protected PredatorState getNextState() {
        return new SnakeRaidState(predator);
    }
}
