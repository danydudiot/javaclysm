package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Terrain;

public abstract class HungryState extends PreyState {
    public HungryState(Prey prey) {
        super(prey);
    }


    @Override
    public boolean canMove(char direction) {
        Terrain target = Board.getInstance().getToward(prey.getX(), prey.getY(), direction);
        return target != null && (target.getEntityOnCase() instanceof Food || target.isEmpty());
    }

    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            return Colors.PURPLE + prey.getRepresentation() + Colors.RESET;
        } else {
            return Colors.WHITE + prey.getRepresentation() + Colors.RESET;
        }
    }

}
