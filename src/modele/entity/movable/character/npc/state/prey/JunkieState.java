package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

import java.util.ArrayList;
import java.util.List;

public class JunkieState extends PreyState {
    public JunkieState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    @Override
    public void updateState() {
        prey.setHungryCount(prey.getHungryCount()-1);
        if (prey.getHungryCount() <= 0){
            if (prey instanceof Squirrel){
                prey.setCurrentState(new SquirrelHungryState(prey));
            } else {
                prey.setCurrentState(new MonkeyHungryState(prey));
            }
        }

    }

    @Override
    public void deplacement() {
        List<List<Terrain>> neighbours = Board.getInstance().getNearSorted(prey.getX(), prey.getY(),2);
        List<Terrain> casePossible = new ArrayList<>();

        for (int i = neighbours.size()-1; i >= 0; --i) {
            for (Terrain terrain : neighbours.get(i)) {
                if (canMove(terrain)) {
                    casePossible.add(terrain);
                }
            }
            if (!casePossible.isEmpty()){
                Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
                Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
                return;
            }
        }

        Terrain terrain = Board.getInstance().getAt(prey.getX(), prey.getY());
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, terrain));
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
        return super.canMove(direction) || (target instanceof Rock && target.getEntityOnCase() instanceof Scorpio);
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return super.canMove(terrain) || (terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio);
    }

    @Override
    public String applyColorModifier() {
        return Colors.RED + prey.getRepresentation() + Colors.RESET;
    }
}
