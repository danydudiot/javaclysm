package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;


public abstract class NotHungryState extends PreyState {
    public NotHungryState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    @Override
    public String applyColorModifier() {
        if (prey.isFriendly()) {
            return Colors.LIGHT_PURPLE + prey.getRepresentation() + "\u001b[0m";
        } else {
            // light white
            return Colors.LIGHT_WHITE + prey.getRepresentation() + "\u001b[0m";
        }
    }
}
