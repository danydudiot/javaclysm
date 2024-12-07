package modele.entity.movable.character.npc;

import modele.Board;
import modele.clock.Observateur;
import modele.entity.movable.character.Character;
import modele.entity.movable.character.npc.state.HungryState;
import modele.entity.movable.character.npc.state.NotHungryState;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.food.Food;
import modele.interaction.Grab;
import modele.interaction.Hit;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

public abstract class NonPlayerCharacter extends Character implements Interactible, Observateur {
    public final int hungryCountBase;
    private int hungryCount;
    protected Class<? extends Food> foodPreference;
    protected State curentState;
    protected int friendLevel;
    protected Interaction[] interactionList;


    protected String displayName;
    public NonPlayerCharacter(int x, int y, int hungryCountBase) {
        super(x, y);
        this.curentState = new NotHungryState(this);
        this.hungryCountBase = hungryCountBase;
        this.hungryCount = hungryCountBase;
        this.interactionList = new Interaction[1];
        interactionList[0]= new Hit();
    }

    public Interaction[] getInteractions(){
        return interactionList;
    }

    public void mettreAJour(Object object){

        if (object instanceof Board){
            Board board = (Board) object;
            curentState.updateState();
            move(curentState.deplacement(board), board);
        }
    }
    public boolean isFriendly () {
        return friendLevel >= 1;
    }

    public void setCurentState(State curentState) {
        if (curentState instanceof NotHungryState){
            System.out.println("changement d'état pas faim");
        } else if (curentState instanceof HungryState){
            System.out.println("changement d'état faim");
        } else {
            System.out.println("INCONNUE " + curentState);
        }
        this.curentState = curentState;
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
        curentState.updateState();
    }

    @Override
    public void hit() {
        friendLevel = 0;
    }

    @Override
    public String toString() {
        return curentState.applyColorModifier();
    }
}
