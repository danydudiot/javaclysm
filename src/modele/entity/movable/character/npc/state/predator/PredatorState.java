package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.entity.Entity;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.Map;

public abstract class PredatorState implements State {
    protected Predator predator;

    public PredatorState(Predator predator) {
        this.predator = predator;
    }


    protected char getDefault(String allow, boolean isLand){
        int[] position = predator.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String direction = "";
        for (char a : neighbours.keySet()) {
            Terrain terrain = neighbours.get(a);
            if (isLand){
                if (terrain instanceof Empty && allow.contains(direction)) {
                    direction += a;
                }
            } else {
                if (terrain.getEntityOnCase() == null && allow.contains(direction)) {
                    direction += a;
                }
            }
        }
        if (direction.isEmpty()){
            return 'a';
        } else {
            return direction.charAt((int) (Math.random() * direction.length()));
        }

    }

    @Override
    public String applyColorModifier() {
        return predator.getRepresentation();
    }

}
