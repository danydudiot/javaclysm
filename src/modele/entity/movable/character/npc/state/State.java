package modele.entity.movable.character.npc.state;


import modele.entity.stationary.terrain.Terrain;

public interface State {
    void updateState();
    void deplacement();
    boolean canMove(Terrain terrain);
    String applyColorModifier();
}
