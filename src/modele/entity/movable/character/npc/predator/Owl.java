package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;

public class Owl extends Predator{
    public Owl(int x, int y) {
        super(x, y);
        this.currentState = new OwlRaidState(this);

    }
}
