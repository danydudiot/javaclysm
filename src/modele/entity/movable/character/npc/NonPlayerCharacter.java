package modele.entity.movable.character.npc;

import modele.clock.Observateur;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.State;
import modele.interaction.Hit;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class NonPlayerCharacter extends Character implements Interactible, Observateur {

    protected State currentState;
    protected Interaction[] interactionList;

    public NonPlayerCharacter(int x, int y) {
        super(x, y);
        this.interactionList = new Interaction[1];
        this.interactionList[0]= new Hit();
    }

    public Interaction[] getInteractions(){
        return interactionList;
    }

    public void mettreAJour(){
        currentState.deplacement();
        currentState.updateState();
    }

    @Override
    public boolean canMove(char direction) {
        return currentState.canMove(direction);
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    public State getCurrentState() {
        return currentState;
    }


    @Override
    public String toString() {
        return currentState.applyColorModifier();
    }

    public abstract boolean hit(Character aggressor);

}
