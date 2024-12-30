package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCommand;
import modele.clock.commands.PredatorAttackCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoxRaidState extends RaidState {
    public FoxRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(predator.getX(),predator.getY(),direction);
        // target != null est déjà testé dans instanceof.
        return target instanceof Empty && target.isEmpty();
    }

    @Override
    public void updateState() {}

    @Override
    public void deplacement() {
        final int[] position = predator.getPosition();
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String directionLand = "";
        List<Prey> preyList = new ArrayList<>();
        for (char a : neighbours.keySet()) {
            Terrain terrain = neighbours.get(a);
            if ((terrain.getEntityOnCase() instanceof Prey) && !(terrain instanceof Tree)) {
                preyList.add((Prey) terrain.getEntityOnCase());
            } else if (terrain instanceof Empty && terrain.getEntityOnCase() == null) {
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
