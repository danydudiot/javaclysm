package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

public class ScorpioRestState extends RestState {
    public ScorpioRestState(Predator predator) {
        super(predator, 2);
    }


    protected PredatorState getNextState(){
        return new ScorpioRestState(predator);
    }
}
