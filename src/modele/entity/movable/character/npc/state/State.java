package modele.entity.movable.character.npc.state;

import modele.Board;

public interface State {
    public void updateState();
    public void deplacement();
    public String applyColorModifier();
}
