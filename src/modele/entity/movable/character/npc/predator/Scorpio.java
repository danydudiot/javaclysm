package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;
import modele.entity.movable.character.npc.state.predator.ScorpioRaidState;
import modele.entity.movable.character.npc.state.predator.ScorpioRestState;

public class Scorpio extends Predator{
    public Scorpio(int x, int y) {
        super(x, y);
        this.currentState = new ScorpioRaidState(this);
    }

    @Override
    public void afterHit() {
        setCurrentState(new ScorpioRestState(this));
    }
}
