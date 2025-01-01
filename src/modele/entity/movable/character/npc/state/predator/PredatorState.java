package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCommand;
import modele.entity.Entity;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PredatorState implements State {
    protected Predator predator;

    public PredatorState(Predator predator) {
        this.predator = predator;
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(predator.getX(), predator.getY(), direction);
        return target != null && (target.isEmpty() || target.getEntityOnCase() instanceof Prey);
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return terrain != null && (terrain.isEmpty() || terrain.getEntityOnCase() instanceof Prey);
    }

    protected char getPrey(){
        Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(predator.getX(),predator.getY());
        String prey = "";
        for (char direction : neighbours.keySet()){
            Terrain terrain = neighbours.get(direction);
            if (terrain.getEntityOnCase() instanceof Prey && !((Prey) terrain.getEntityOnCase()).isProtected(terrain, predator)){
                prey += direction;
            }
        }

        if (prey.isEmpty()){
            return 'a';
        }

        Prey preyEntity = (Prey) neighbours.get(prey.charAt(0)).getEntityOnCase();
        Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyEntity));
        return prey.charAt(0);
    }


    protected char getDefault(String allow){
        int[] position = predator.getPosition();
        final Map<Character, Terrain> neighbours = Board.getInstance().getNeighbours(position[0], position[1]);
        String directionList = "";
        for (char direction : neighbours.keySet()) {
            Terrain terrain = neighbours.get(direction);
            if (terrain.isEmpty() && isAllow(terrain) && allow.contains(String.valueOf(direction))) {
                directionList += direction;
            }
        }
        if (directionList.isEmpty()){
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, 'a'));
            return 'a';
        } else {
            char move = directionList.charAt((int) (Math.random() * directionList.length()));
            Clock.getInstance().addCommandToTurn(new PredatorMoveCommand(predator, move));
            return move;
        }

    }

    protected boolean isAllow(Terrain terrain) {
        return terrain instanceof Empty;
    }
}
