package modele.entity.movable.character.npc.state.predator;


import modele.Board;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

public class SnakeRaidState extends RaidState {
    public SnakeRaidState(Predator predator) {
        super(predator);
    }
    @Override
    public void updateState() {
    }
    @Override
    public void deplacement() {
        Terrain move = getPrey();
        if (move.equals(Board.getInstance().getAt(predator.getX(), predator.getY()))){
            getDefault2Case();
        }
    }



}
