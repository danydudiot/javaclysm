package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;

public abstract class HungryState extends PreyState {
    public HungryState(Prey prey) {
        super(prey);
    }


    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            return Colors.PURPLE + prey.getRepresentation() + Colors.RESET;
        } else {
            return Colors.WHITE + prey.getRepresentation() + Colors.RESET;
        }
    }

    public void updateState(){
        if (prey.getHungryCount() > 0){
            prey.setCurrentState(new NotHungryState(prey));
        }
    }

}
