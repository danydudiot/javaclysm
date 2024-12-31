package modele.interaction;

import modele.Board;
import modele.entity.Entity;
import modele.entity.movable.character.npc.NonPlayerCharacter;

public class Hit implements Interaction {
    private final String displayName = "Frapper";

    public void interact(Entity entity){
        if (entity instanceof NonPlayerCharacter){
            Board.getInstance().logAction(entity.getDisplayName() + " à été frappé");
            ((NonPlayerCharacter) entity).hit(Board.getInstance().getPlayer());
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
