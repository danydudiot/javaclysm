package modele.entity.movable.character.npc;

import modele.clock.Observateur;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.State;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class NonPlayerCharacter extends Character implements Interactible, Observateur {
    public NonPlayerCharacter(int x, int y) {
        super(x, y);
    }

    public Interaction[] getInteraction(){
        return null;
    }

    public void mettreAJour(Object object){

    }
}
