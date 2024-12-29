package modele.entity.movable.character.npc.state;

import modele.entity.movable.character.npc.NonPlayerCharacter;

public class DeadState implements State {
    protected NonPlayerCharacter npc;
    public DeadState(NonPlayerCharacter npc) {
        this.npc = npc;
    }

    @Override
    public void updateState() {

    }

    @Override
    public void deplacement() {

    }

    @Override
    public String applyColorModifier() {
        return "";
    }
}
