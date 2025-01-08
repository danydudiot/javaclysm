package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.ScorpioRaidState;
import modele.interaction.Interaction;

public class Scorpio extends Predator{
    protected int canAttack;
    protected int timeUnderRock;
    public Scorpio(int x, int y) {
        super(x, y);
        this.currentState = new ScorpioRaidState(this);
        this.representation = "O";
        this.displayName = "Scorpion";
        this.canAttack = 0;
        this.interactionList = new Interaction[0];
    }

    @Override
    public void afterHit(boolean killed) {
        canAttack = 2;
    }

    public boolean canAttack(){
        return canAttack <= 0;
    }

    public int getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(int canAttack) {
        this.canAttack = canAttack;
    }

    public int getTimeUnderRock() {
        return timeUnderRock;
    }

    public void setTimeUnderRock(int timeUnderRock) {
        this.timeUnderRock = timeUnderRock;
    }

    @Override
    public void mettreAJour() {
        canAttack--;
        super.mettreAJour();
    }


}
