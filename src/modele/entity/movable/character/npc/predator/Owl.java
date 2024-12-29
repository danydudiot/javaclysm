package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;
import modele.entity.movable.character.npc.state.predator.OwlRestState;

public class Owl extends Predator{
    public Owl(int x, int y) {
        super(x, y);
        this.currentState = new OwlRaidState(this);
        this.representation = "H";
        this.displayName = "Hibou";
    }

    @Override
    public void afterHit() {
        setCurrentState(new OwlRestState(this));
    }
}
