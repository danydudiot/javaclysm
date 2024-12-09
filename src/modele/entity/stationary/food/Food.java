package modele.entity.stationary.food;

import modele.InventoryItem;
import modele.entity.stationary.StaticEntity;
import modele.interaction.Grab;
import modele.interaction.Hit;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

import java.util.ArrayList;
import java.util.List;

public abstract class Food extends StaticEntity implements Interactible, InventoryItem {
    protected Interaction[] interactionList;
    public Food(int x, int y) {
        super(x, y);
        this.interactionList = new Interaction[1];
        interactionList[0]= new Grab();
    }

    public Interaction[] getInteractions(){
        return interactionList;
    }

}
