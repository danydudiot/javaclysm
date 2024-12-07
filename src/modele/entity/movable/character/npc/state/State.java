package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;

public interface State {
    public void updateState();
    public char deplacement(Board board);

    public String applyColorModifier();
}
