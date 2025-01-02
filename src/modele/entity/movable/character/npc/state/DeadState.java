package modele.entity.movable.character.npc.state;

import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

public class DeadState implements State {
    protected NonPlayerCharacter npc;
    public DeadState(NonPlayerCharacter npc) {
        this.npc = npc;
    }

    @Override
    public boolean canMove(char direction) {
        return false;
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return false;
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
