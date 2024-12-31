package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.FoxRaidState;
import modele.interaction.Interaction;

public class Fox extends Predator{
    public Fox(int x, int y) {
        super(x, y);
        this.currentState = new FoxRaidState(this);
        this.representation = "R";
        this.displayName = "Renard";
        this.interactionList = new Interaction[0];
    }

    @Override
    public void afterHit(boolean killed) {

    }
}
