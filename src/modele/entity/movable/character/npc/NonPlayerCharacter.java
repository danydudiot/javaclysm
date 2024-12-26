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
    public final int hungryCountBase;
    protected int hungryCount;
    protected Class<? extends Food> foodPreference;
    protected State currentState;
    protected int friendLevel;
    protected Interaction[] interactionList;

    public NonPlayerCharacter(int x, int y, int hungryCountBase) {
        super(x, y);
        this.currentState = new NotHungryState(this);
        this.hungryCountBase = hungryCountBase;
        this.hungryCount = hungryCountBase;
        this.interactionList = new Interaction[1];
        this.interactionList[0]= new Hit();
    }

    public Interaction[] getInteractions(){
        return interactionList;
    }

    public void mettreAJour(){
        Clock.getInstance().addCommandToTurn(new MoveNPCCommand(this, currentState.deplacement()));
        currentState.updateState();
    }

    public boolean isFriendly () {
        return friendLevel >= 1;
    }
    public int getFriendLevel() {
        return friendLevel;
    }
    public void setFriendLevel(int value) {
        friendLevel = value;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
    public State getCurrentState() {
        return currentState;
    }

    public int getHungryCount() {
        return hungryCount;
    }
    public void setHungryCount(int hungryCount) {
        this.hungryCount = hungryCount;
    }
    public Class<? extends Food> getFoodPreference() {
        return foodPreference;
    }

    public void eat(boolean isPlayerNearby, Board board) {
        if (isPlayerNearby) {
            if (! isFriendly()) {
                friendLevel++;
                if (isFriendly()) {
                    board.logAction(displayName + " est maintenant un ami");
                }
            }
        }
        hungryCount = hungryCountBase;
        currentState.updateState();
    }

    @Override
    public void hit() {
        friendLevel = 0;
    }

    @Override
    public String toString() {
        return currentState.applyColorModifier();
    }
}
