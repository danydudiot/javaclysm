package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.entity.Entity;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;

import java.util.Map;

public class HungryState implements State {
    private NonPlayerCharacter npc;
    public HungryState(NonPlayerCharacter npc) {
        this.npc = npc;

    }

    @Override
    public char deplacement(Board board) {
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = board.getNeighbours(position[0], position[1]);
        String possibleOutcomes = "";
        String preferedOutcomes = "";
        for (char a : neighbours.keySet()) {
            Entity entity = neighbours.get(a).getEntityOnCase();
            if (entity == null) {
                possibleOutcomes += a;
            } else if (entity instanceof Food) {
                if (entity.getClass() == npc.getFoodPreference()) {
                    preferedOutcomes = a + preferedOutcomes;
                } else {
                    preferedOutcomes += a;
                }
            }
        }
        if (preferedOutcomes.isEmpty()) {
            if (possibleOutcomes.isEmpty()) {
                return 'a';
            } else if (possibleOutcomes.length() == 1) {
                return possibleOutcomes.charAt(0);
            } else {
                return possibleOutcomes.charAt((int) (Math.random() * possibleOutcomes.length()));
            }
        } else {
            return preferedOutcomes.charAt(0);
        }
    }

    public void updateState(){}

}
