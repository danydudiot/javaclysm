package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;
import modele.entity.movable.character.npc.state.predator.OwlRestState;
import modele.entity.movable.character.npc.state.predator.RaidState;
import modele.interaction.Interaction;

public class Owl extends Predator{
    public Owl(int x, int y) {
        super(x, y);
        this.currentState = new OwlRaidState(this);
        this.representation = "H";
        this.displayName = "Hibou";
    }

    @Override
    public void afterHit(boolean killed) {
        setCurrentState(new OwlRestState(this));
    }

    @Override
    public Interaction[] getInteractions() {
        if (currentState instanceof RaidState){
            return new Interaction[0];
        } else {
            return interactionList;
        }
    }
}
