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

public class MonkeyJunkieState extends JunkieState {
    public MonkeyJunkieState(Prey prey) {
        super(prey);
    }

    @Override
    public void updateState() {
        if (prey.getHungryCount() <= 0){
            prey.setCurrentState(new MonkeyHungryState(prey));
        }
    }

    @Override
    public void deplacement() {
        getDefault(null);
    }

    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
        return super.canMove(direction) || (target instanceof Rock && target.getEntityOnCase() instanceof Scorpio && ((Scorpio)target.getEntityOnCase()).canAttack());
    }

    @Override
    public boolean canMove(Terrain terrain) {
        return super.canMove(terrain) || (terrain instanceof Rock && terrain.getEntityOnCase() instanceof Scorpio && ((Scorpio)terrain.getEntityOnCase()).canAttack());
    }
}
