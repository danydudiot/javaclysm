package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

import java.util.Map;

public class NotHungryState implements State {

    private NonPlayerCharacter npc;
    public NotHungryState(NonPlayerCharacter npc) {
        this.npc = npc;
        npc.setHungryCount(npc.hungryCountBase);
    }

    public void updateState(){
        npc.setHungryCount(npc.getHungryCount()-1);
        if (npc.getHungryCount() <= 0){
            npc.setCurrentState(new HungryState(npc));
        }
    }

    @Override
    public char deplacement() {
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String possibleOutcomes = "";
        for (char a : neighbours.keySet()) {
            if (neighbours.get(a).getEntityOnCase() == null) {
                possibleOutcomes += a;
            }
        }
        if (possibleOutcomes.isEmpty()) {
            return 'a';
        } else if (possibleOutcomes.length() == 1) {
            return possibleOutcomes.charAt(0);
        } else {
            return possibleOutcomes.charAt((int) (Math.random() * possibleOutcomes.length()));
        }
    }

    @Override
    public String applyColorModifier() {
        if (npc.isFriendly()) {
            // Light purple
            return "\u001b[95m" + npc.getRepresentation() + "\u001b[0m";
        } else {
            // light white
            return "\u001b[97m" + npc.getRepresentation() + "\u001b[0m";
        }
    }
}
