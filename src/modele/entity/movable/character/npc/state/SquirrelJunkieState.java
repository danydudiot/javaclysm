package modele.entity.movable.character.npc.state;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;

import java.util.Map;

public class SquirrelJunkieState extends JunkieState{
    private Prey npc;
    public SquirrelJunkieState(Prey npc) {
        this.npc = npc;
        npc.setHungryCount(npc.hungryCountBase);

    }

    @Override
    public void updateState() {
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

        char move1 = getDeplacementFrom(' ');
        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(npc, move1));
        char move2 = getDeplacementFrom(npc.getInverseDirection(move1));
        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(npc, move2));
    }

    private char getDeplacementFrom(char disable){
        int[] position = npc.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String possibleOutcomes = "";
        for (char a : neighbours.keySet()) {
            if (neighbours.get(a).getEntityOnCase() == null && a != disable) {
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
            return "\u001b[95m" + npc.getRepresentation() + Colors.ANSI_RESET;
        } else {
            // light white
            return Colors.ANSI_RED + npc.getRepresentation() + Colors.ANSI_RESET;
        }
    }
}
