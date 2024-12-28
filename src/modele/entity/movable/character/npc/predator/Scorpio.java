package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.ScorpioRaidState;

public class Scorpio extends Predator{
    public Scorpio(int x, int y) {
        super(x, y);
        this.currentState = new ScorpioRaidState(this);

    }
}
