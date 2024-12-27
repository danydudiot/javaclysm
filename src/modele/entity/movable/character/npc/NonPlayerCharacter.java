package modele.entity.movable.character.npc;

import modele.Board;
import modele.clock.Clock;
import modele.clock.Observateur;
import modele.clock.commands.MoveNPCCommand;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.NotHungryState;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.food.Food;
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
}
