package modele.entity.stationary.food;

import modele.InventoryItem;
import modele.entity.stationary.StaticEntity;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class Food extends StaticEntity implements Interactible, InventoryItem {
    protected String displayName;
    public Food(int x, int y) {
        super(x, y);
    }

    public Interaction[] getInteraction(){
        return null;
    }

    public String getDisplayName(){
        return displayName;
    }
}
