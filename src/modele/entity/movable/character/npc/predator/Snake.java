package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.SnakeRaidState;
import modele.entity.movable.character.npc.state.predator.SnakeRestState;

public class Snake extends Predator{
    public Snake(int x, int y) {
        super(x, y);
        this.currentState = new SnakeRaidState(this);
        this.representation = "E";
        this.displayName = "Serpent";
    }

    @Override
    public void afterHit(boolean killed) {
        if (killed) {
            setCurrentState(new SnakeRestState(this));
        }
    }
}
