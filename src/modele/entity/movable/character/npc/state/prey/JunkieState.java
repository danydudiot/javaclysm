package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

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
        Terrain terrain1 = getDefault(null);
        Terrain terrain2 = getDefault(terrain1);
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
