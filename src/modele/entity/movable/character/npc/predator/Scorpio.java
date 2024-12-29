package modele.entity.movable.character.npc.predator;

import modele.entity.movable.character.npc.state.predator.OwlRaidState;
import modele.entity.movable.character.npc.state.predator.ScorpioRaidState;
import modele.entity.movable.character.npc.state.predator.ScorpioRestState;

public class Scorpio extends Predator{
    protected int canAttack;
    public Scorpio(int x, int y) {
        super(x, y);
        this.currentState = new ScorpioRaidState(this);
        this.representation = "O";
        this.displayName = "Scorpion";
        this.canAttack = 0;
    }

    @Override
    public void afterHit() {
        canAttack = 2;
    }

    public boolean canAttack(){
        return canAttack <= 0;
    }

    @Override
    public void mettreAJour() {
        canAttack--;
        super.mettreAJour();
    }
}
