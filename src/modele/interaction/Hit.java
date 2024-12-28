package modele.interaction;

import modele.Board;
import modele.Inventory;
import modele.entity.Entity;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.NonPlayerCharacter;

public class Hit implements Interaction {
    private final String displayName = "Frapper";

    public void interact(Inventory inventory, Entity entity, Character author){
        if (entity instanceof NonPlayerCharacter){
            ((NonPlayerCharacter) entity).hit(author);
            Board.getInstance().logAction(entity.getDisplayName() + " à été frappe");
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
