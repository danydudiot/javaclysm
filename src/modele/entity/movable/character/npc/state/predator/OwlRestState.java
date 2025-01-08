package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

public class OwlRestState extends RestState {
    public OwlRestState(Predator predator) {
        super(predator, 1);
    }

    protected PredatorState getNextState(){
        return new OwlRaidState(predator);
    }

}
