package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SnakeRaidState extends RaidState {
    public SnakeRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {

    }

    @Override
    public void deplacement() {
        int[] position = predator.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String directionLand = "";
        List<Prey> preyList = new ArrayList<>();
        for (char a : neighbours.keySet()) {
            Terrain terrain = neighbours.get(a);
            if (terrain.getEntityOnCase() instanceof Prey) {
                preyList.add((Prey) terrain.getEntityOnCase());
            } else if (terrain.getEntityOnCase() == null && ! (terrain instanceof PalmTree)) {
                directionLand += a;
            }

        }
        if (!preyList.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyList.get(0)));
        } else if (!directionLand.isEmpty()) {
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, directionLand.charAt((int) (Math.random() * directionLand.length()))));
        } else {
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, 'a'));
        }
    }

}
