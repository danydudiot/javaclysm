package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;

import java.util.Map;

public class NotHungryState implements State {

    private Prey npc;
    public NotHungryState(Prey npc) {
        this.npc = npc;
        npc.setHungryCount(npc.hungryCountBase);
    }

    public void updateState(){
        npc.setHungryCount(npc.getHungryCount()-1);
        if (npc.getHungryCount() <= 0){
            if (npc instanceof Squirrel){
                npc.setCurrentState(new SquirrelHungryState(npc));
            } else {
                npc.setCurrentState(new MonkeyHungryState(npc));
            }
        }
    }

    @Override
    public void deplacement() {
        char move = 'a';
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String possibleOutcomes = "";
        for (char a : neighbours.keySet()) {
            if (neighbours.get(a).getEntityOnCase() == null) {
                possibleOutcomes += a;
            }
        }
        if (possibleOutcomes.isEmpty()) {
            move = 'a';
        } else if (possibleOutcomes.length() == 1) {
            move = possibleOutcomes.charAt(0);
        } else {
            move = possibleOutcomes.charAt((int) (Math.random() * possibleOutcomes.length()));
        }
        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(npc, move));
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
