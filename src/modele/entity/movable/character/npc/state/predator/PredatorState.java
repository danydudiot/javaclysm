package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCoordinateCommand;
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

    protected Terrain getPrey(){
        List<Terrain> neighbours = Board.getInstance().getNeighbours(predator.getX(), predator.getY());
        List<Terrain> casePossible = new ArrayList<>();
        for (Terrain terrain : neighbours){
            if (terrain.getEntityOnCase() instanceof Prey && !((Prey) terrain.getEntityOnCase()).isProtected(terrain, predator)){
                casePossible.add(terrain);
            }
        }

        if (casePossible.isEmpty()){
            Terrain currentCase = Board.getInstance().getAt(predator.getX(), predator.getY());
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, currentCase));
            return currentCase;
        } else {
            Prey preyEntity = (Prey) casePossible.get(0).getEntityOnCase();
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyEntity));
            return casePossible.get(0);
        }
    }


    protected Terrain getDefault(Terrain forbidden){
        List<Terrain> neighbours = Board.getInstance().getNeighbours(predator.getX(), predator.getY());
        List<Terrain> casePossible = new ArrayList<>();

        for (Terrain terrain : neighbours) {
            if (terrain.isEmpty() && canMove(terrain) && !terrain.equals(forbidden)) {
                casePossible.add(terrain);
            }
        }
        if (casePossible.isEmpty()){
            Terrain currentCase = Board.getInstance().getAt(predator.getX(), predator.getY());
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, currentCase));
            return currentCase;
        } else {
            Terrain move = casePossible.get((int) (Math.random() * casePossible.size()));
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, move));
            return move;
        }

    }


    protected Terrain getDefault2Case(){
        List<List<Terrain>> neighbours = Board.getInstance().getNearSorted(predator.getX(), predator.getY(),2);
        List<Terrain> casePossible = new ArrayList<>();

        for (int i = neighbours.size()-1; i >= 0; --i) {
            for (Terrain terrain : neighbours.get(i)) {
                if (terrain.isEmpty() && canMove(terrain)) {
                    casePossible.add(terrain);
                }
            }
            if (!casePossible.isEmpty()){
                Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
                Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
                return terrain;
            }
        }
        Terrain terrain = Board.getInstance().getAt(predator.getX(), predator.getY());
        Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
        return terrain;
    }

}
