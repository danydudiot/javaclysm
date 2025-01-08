package modele.clock.commands;

import modele.Board;
import modele.Inventory;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.movable.character.npc.state.State;
import modele.entity.movable.character.npc.state.prey.MonkeyHungryState;
import modele.entity.movable.character.npc.state.prey.MonkeyNotHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelHungryState;
import modele.entity.movable.character.npc.state.prey.SquirrelNotHungryState;
import modele.entity.stationary.terrain.Terrain;

import java.util.List;

/**
 * Annule la commande pour retirer la proie de l'inventaire et la remettre à sa position initiale.
 */
public class FriendOutInventoryCommand implements Command {
    /**
     * La proie à retirer de l'inventaire.
     */
    public Prey prey;
    /**
     * Ancien état de la proie.
     */
    private State old_State;
    /**
     * Indique si la proie a été déposée.
     */
    private boolean hasDrop;

    /**
     * Constructeur de la classe FriendOutInventoryCommand.
     * Initialise les attributs de la commande.
     *
     * @param prey La proie à retirer de l'inventaire.
     */
    public FriendOutInventoryCommand(Prey prey) {
        this.prey = prey;
        this.old_State = prey.getCurrentState();
        this.hasDrop = false;
    }

    /**
     * Exécute la commande pour retirer la proie de l'inventaire et la placer sur le terrain.
     */
    @Override
    public void doCommand() {
        PlayerCharacter playerCharacter = Board.getInstance().getPlayer();
        int rayon = 1;
        while (!hasDrop) {
            List<Terrain> around = Board.getInstance().getNearSorted(playerCharacter.getX(), playerCharacter.getY(), rayon).get(rayon);
            for (Terrain terrain : around) {
                if (terrain.isEmpty()) {
                    Inventory.getInstance().remove(prey);
                    terrain.setEntityOnCase(prey);
                    hasDrop = true;
                    break;
                }
            }
            ++rayon;
        }
        if (prey instanceof Squirrel) {
            if (prey.getHungryCount() <= 0) {
                prey.setCurrentState(new SquirrelHungryState(prey));
            } else {
                prey.setCurrentState(new SquirrelNotHungryState(prey));
            }
        } else {
            if (prey.getHungryCount() <= 0) {
                prey.setCurrentState(new MonkeyHungryState(prey));
            } else {
                prey.setCurrentState(new MonkeyNotHungryState(prey));
            }
        }
        prey.setHasMoved(true);
    }

    /**
     * Annule la commande pour remettre la proie dans l'inventaire et restaurer son état initial.
     */
    @Override
    public void undoCommand() {
        try {
            Inventory.getInstance().add(prey);
            prey.setCurrentState(old_State);
            Board.getInstance().clearCase(prey.getX(), prey.getY());
        } catch (Exception e) {
            Board.getInstance().logError(e.getMessage());
        }
    }
}
