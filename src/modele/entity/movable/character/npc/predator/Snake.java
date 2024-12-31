package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.RaidState;
import modele.entity.movable.character.npc.state.predator.SnakeRaidState;
import modele.entity.movable.character.npc.state.predator.SnakeRestState;
import modele.interaction.Interaction;

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

    @Override
    public Interaction[] getInteractions() {
        if (currentState instanceof RaidState){
            return new Interaction[0];
        } else {
            return interactionList;
        }
    }
}
