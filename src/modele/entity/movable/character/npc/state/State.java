package modele.entity.movable.character.npc.state;


public interface State {
    void updateState();
    void deplacement();
    String applyColorModifier();
}
