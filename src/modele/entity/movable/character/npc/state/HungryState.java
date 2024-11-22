package modele.entity.movable.character.npc.state;

import modele.entity.movable.character.npc.NonPlayerCharacter;

public class HungryState implements State {
    private NonPlayerCharacter npc;
    public HungryState(NonPlayerCharacter npc) {
        this.npc = npc;

    }

    public void updateState(){}

}
