package modele.entity.movable.character.npc.state.predator;


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
        char move = getPrey();
        if (move == 'a'){
            move = getDefault("zqsd");
            if (getPrey() == 'a'){
                getDefault("zqsd".replaceAll(String.valueOf(predator.getInverseDirection(move)), ""));
            }
        }
    }



}
