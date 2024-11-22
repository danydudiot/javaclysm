package modele.entity.stationary.food;

import modele.entity.stationary.StaticEntity;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class Food extends StaticEntity implements Interactible {

    public Interaction[] getInteraction(){
        return null;
    }
}
