package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.SnakeRaidState;

public class Snake extends Predator{
    public Snake(int x, int y) {
        super(x, y);
        this.currentState = new SnakeRaidState(this);

    }
}
