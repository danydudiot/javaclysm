package modele.entity.movable.character.npc.state.predator;

import modele.entity.movable.character.npc.predator.Predator;

public class SnakeRaidState extends RaidState{
    public SnakeRaidState(Predator predator) {
        super(predator);
    }

    @Override
    public void updateState() {

    }

    @Override
    public void deplacement() {

    }

    @Override
    public String applyColorModifier() {
        return "";
    }
}
