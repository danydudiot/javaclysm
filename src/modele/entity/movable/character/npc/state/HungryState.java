package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.High;
import modele.entity.stationary.terrain.low.Low;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class HungryState implements State {
    protected Prey npc;
    public HungryState(Prey npc) {
        this.npc = npc;

    }

    @Override
    public void deplacement() {
        char move = 'a';
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
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
                move = 'a';
            } else if (possibleOutcomes.length() == 1) {
                move = possibleOutcomes.charAt(0);
            } else {
                move = possibleOutcomes.charAt((int) (Math.random() * possibleOutcomes.length()));
            }
        } else {
            move = preferedOutcomes.charAt(0);
        }
        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(npc, move));
    }

    protected char getDefault(){
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String direction = "";
        for (char a : neighbours.keySet()) {
            Entity entity = neighbours.get(a).getEntityOnCase();
            if (entity == null) {
                direction += a;
            }
        }
        if (direction.isEmpty()){
            return 'a';
        } else {
            return direction.charAt((int) (Math.random() * direction.length()));
        }

    }

    protected char getFood(){
        char move = 'a';
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String food = "";
        for (char a : neighbours.keySet()) {
            Entity entity = neighbours.get(a).getEntityOnCase();
            if (entity instanceof Food) {
                if (entity.getClass() == npc.getFoodPreference()) {
                    food = a + food;
                } else {
                    food += a;
                }
            }
        }
        if (food.isEmpty()) {
            return 'a';
        } else {
            return food.charAt(0);
        }
    }


    protected char getDanger(boolean playerAllow){
        int[] position = npc.getPosition();
        List<Entity> around = Board.getInstance().getNear(position[0], position[1], 4);
        List<Entity> danger = new ArrayList<>();
        for (Entity entity : around){
            if (entity instanceof Predator){
                danger.add(entity);
            }
        }

        if (danger.isEmpty()){
            return 'a';
        }

        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(npc.getPosition()[0], npc.getPosition()[1]);
        char player = ' ';
        String high = "";
        String low = "";
        String free = "";
        for (char direction: neighbours.keySet()){
            Terrain terrain = neighbours.get(direction);
            if (terrain.getEntityOnCase() instanceof PlayerCharacter){
                player = direction;
            } else if (terrain instanceof High){
                high += direction;
            } else if (terrain instanceof Low){
                low += direction;
            } else if (terrain.getEntityOnCase() == null) {
                free += direction;
            }
        }


        if (playerAllow && player != ' '){
            return 'p'; // 'p' pour player
        } else if (!high.isEmpty()) {
            return high.charAt(0);
        } else if (!low.isEmpty()) {
            return low.charAt(0);
        } else {
            return getDirectionFromDanger(danger, free);
        }
    }



    protected char getDirectionFromDanger(List<Entity> danger, String allow){
        if (allow.isEmpty()){
            return 'a';
        }
        float somme_x = 0;
        float somme_y = 0;
        for (Entity entity : danger){
            int[] position = entity.getPosition();
            somme_x += position[0];
            somme_y += position[1];
        }

        float moyenne_x = somme_x / danger.size();
        float moyenne_y = somme_y / danger.size();

        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(npc.getPosition()[0], npc.getPosition()[1]);
        Map<Character, Double> distance = new HashMap<>();

        distance.put('z' ,
            Math.sqrt(
                Math.pow(
                        neighbours.get('z').getPosition()[0] - moyenne_x,2
                ) + Math.pow(
                        neighbours.get('z').getPosition()[1] - moyenne_y,2)
            )
        );
        distance.put('q' ,
                Math.sqrt(
                        Math.pow(
                                neighbours.get('q').getPosition()[0] - moyenne_x,2
                        ) + Math.pow(
                                neighbours.get('q').getPosition()[1] - moyenne_y,2)
                )
        );
        distance.put('s' ,
                Math.sqrt(
                        Math.pow(
                                neighbours.get('s').getPosition()[0] - moyenne_x,2
                        ) + Math.pow(
                                neighbours.get('s').getPosition()[1] - moyenne_y,2)
                )
        );
        distance.put('d' ,
                Math.sqrt(
                        Math.pow(
                                neighbours.get('d').getPosition()[0] - moyenne_x,2
                        ) + Math.pow(
                                neighbours.get('d').getPosition()[1] - moyenne_y,2)
                )
        );

        char maxi = allow.charAt(0);
        for (char direction : distance.keySet()) {
            if (distance.get(maxi) < distance.get(direction)) {
                maxi = direction;
            }
        }
        return maxi;
    }


    @Override
    public String applyColorModifier() {
        if (npc.isFriendly()) {
            // Dark purple
            return "\u001b[35m" + npc.getRepresentation() + "\u001b[0m";
        } else {
            // Dark white
            return "\u001b[37m" + npc.getRepresentation() + "\u001b[0m";
        }
    }

    public void updateState(){
        if (npc.getHungryCount() > 0){
            npc.setCurrentState(new NotHungryState(npc));
        }
    }

}
