package modele.entity.stationary.terrain.high;

import modele.entity.Entity;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.low.Rock;

/**
 * Classe représentant un scorpion sous un rocher
 */
public class ScorpioRock extends Rock {
    protected Scorpio scorpio;
    /**
     * Constructeur qui défini la représentation "R" et le displayName "Rocher".
     *
     * @param x La position verticale du rocher.
     * @param y La position horizontale du rocher.
     */
    public ScorpioRock(int x, int y, Scorpio scorpio) {
        super(x, y);
        this.scorpio = scorpio;
    }

    public Scorpio getScorpio() {
        return scorpio;
    }
}
