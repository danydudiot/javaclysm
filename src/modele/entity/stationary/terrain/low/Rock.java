package modele.entity.stationary.terrain.low;

import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorFatalAttackCommand;
import modele.entity.Entity;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.movable.character.npc.prey.Prey;

/**
 * Classe représentant un rocher sur la carte.
 */
public class Rock extends Low {

    /**
     * Constructeur qui défini la représentation "R" et le displayName "Rocher".
     *
     * @param x La position verticale du rocher.
     * @param y La position horizontale du rocher.
     */
    public Rock(int x, int y) {
        super(x, y);
        this.representation = Colors.WHITE + "R" + Colors.RESET;
        this.displayName = "Rocher";
        this.highlightColor = Colors.WHITE_BACKGROUND + Colors.BLACK;
    }

    @Override
    public void setEntityOnCase(Entity entity) {
        if (getEntityOnCase() == null){
            this.entityOnCase = entity;
            entity.setPosition(this.getX(), this.getY());
        } else if (getEntityOnCase() instanceof Scorpio scorpio && entity instanceof Monkey monkey){
            Clock.getInstance().addCommandToTurn(new PredatorFatalAttackCommand(scorpio, monkey));
        }
    }
}
