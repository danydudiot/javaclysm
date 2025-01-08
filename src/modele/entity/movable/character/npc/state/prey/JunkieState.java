package modele.entity.movable.character.npc.state.prey;

import modele.Colors;
import modele.entity.movable.character.npc.prey.Prey;


public abstract class JunkieState extends PreyState {
    public JunkieState(Prey prey) {
        super(prey);
        prey.setHungryCount(prey.hungryCountBase);
    }

    @Override
    public String applyColorModifier() {
        return Colors.RED + prey.getRepresentation() + Colors.RESET;
    }
}
