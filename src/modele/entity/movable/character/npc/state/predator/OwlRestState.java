package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;

public class OwlRestState extends RestState {
    public OwlRestState(Predator predator) {
        super(predator, 2);
    }

    protected PredatorState getNextState(){
        return new OwlRaidState(predator);
    }

}
