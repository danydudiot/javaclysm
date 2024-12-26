package modele.entity.movable.character.npc.state;

import modele.Board;

public interface State {
    public void updateState();
    public char deplacement();
    public String applyColorModifier();
}
