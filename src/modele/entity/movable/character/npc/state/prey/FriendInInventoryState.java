package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.FriendOutInventoryCommand;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état d'un ami dans l'inventaire d'un personnage proie.
 */
public class FriendInInventoryState extends PreyState {
    /**
     * Constructeur de la classe FriendInInventoryState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public FriendInInventoryState(Prey prey) {
        super(prey);
        prey.setTimeInPocket(3);
    }

    /**
     * Met à jour l'état du personnage proie.
     * Si le temps passé dans l'inventaire est écoulé, une commande pour sortir l'ami de l'inventaire est ajoutée.
     * Sinon, le temps passé dans l'inventaire est décrémenté.
     */
    @Override
    public void updateState() {
        if (prey.getTimeInPocket() <= 0) {
            Clock.getInstance().addCommandToTurn(new FriendOutInventoryCommand(prey));
        }
        prey.setTimeInPocket(prey.getTimeInPocket() - 1);
    }

    /**
     * Gère le déplacement du personnage proie.
     * Ajoute une commande pour déplacer la proie sur le terrain actuel.
     */
    @Override
    public void deplacement() {
        Terrain current = Board.getInstance().getAt(prey.getX(), prey.getY());
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, current)); // Ici, on fait une commande de déplacement sur place, pour énregistrer l'état courant de la prey.
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     *
     * @return Une chaîne de caractères indiquant que cet état ne devrait jamais être visible.
     */
    @Override
    public String applyColorModifier() {
        return "YOU SHOULD NEVER SEE THIS (FriendInInventoryState)";
    }
}