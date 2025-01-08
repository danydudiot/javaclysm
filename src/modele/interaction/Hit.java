package modele.interaction;

import modele.Board;
import modele.entity.Entity;
import modele.entity.movable.character.npc.NonPlayerCharacter;

public class Hit implements Interaction {
    private final String displayName = "Frapper";

    public void interact(Entity entity){
        if (entity instanceof NonPlayerCharacter npc){
            Board.getInstance().logAction(entity.getDisplayName() + " à été frappé");
            npc.hit(Board.getInstance().getPlayer());
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
