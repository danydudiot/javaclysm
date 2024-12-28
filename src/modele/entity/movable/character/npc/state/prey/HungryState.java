package modele.entity.movable.character.npc.state.prey;

import modele.entity.movable.character.npc.prey.Prey;

public abstract class HungryState extends PreyState {
    public HungryState(Prey prey) {
        super(prey);
    }


    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            // Dark purple
            return "\u001b[35m" + prey.getRepresentation() + "\u001b[0m";
        } else {
            // Dark white
            return "\u001b[37m" + prey.getRepresentation() + "\u001b[0m";
        }
    }

    public void updateState(){
        if (prey.getHungryCount() > 0){
            prey.setCurrentState(new NotHungryState(prey));
        }
    }

}
