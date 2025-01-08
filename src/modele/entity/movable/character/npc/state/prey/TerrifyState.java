package modele.entity.movable.character.npc.state.prey;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PreyMoveCoordinateCommand;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.prey.Squirrel;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état terrifié d'un personnage proie.
 */
public class TerrifyState extends PreyState {
    /**
     * Constructeur de la classe TerrifyState.
     *
     * @param prey Le personnage proie associé à cet état.
     */
    public TerrifyState(Prey prey) {
        super(prey);
        prey.setFearLevel(3);

    }

    /**
     * Vérifie si le personnage proie peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le personnage proie peut se déplacer sur le terrain, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain.getEntityOnCase() == prey;
    }

    /**
     * Met à jour l'état du personnage proie.
     * Si le niveau de peur est inférieur ou égal à 0, l'état passe à affamé ou non-affamé en fonction du compteur de faim.
     */
    @Override
    public void updateState() {
        if (prey.getFearLevel() <= 0) {

            if (prey.getHungryCount() <= 0) {
                if (prey instanceof Squirrel) {
                    prey.setCurrentState(new SquirrelHungryState(prey));
                } else {
                    prey.setCurrentState(new MonkeyHungryState(prey));
                }
            } else {
                if (prey instanceof Squirrel) {
                    prey.setCurrentState(new SquirrelNotHungryState(prey));
                } else {
                    prey.setCurrentState(new MonkeyNotHungryState(prey));
                }
            }
        }
        prey.setFearLevel(prey.getFearLevel() - 1);
    }

    /**
     * Gère le déplacement du personnage proie.
     * Ajoute une commande de déplacement à la file d'attente des commandes du tour.
     */
    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new PreyMoveCoordinateCommand(prey, Board.getInstance().getAt(prey.getX(), prey.getY())));
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du personnage.
     *
     * @return Une chaîne de caractères représentant le personnage avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        return Colors.BLUE_BACKGROUND + prey.getRepresentation() + Colors.RESET;
    }
}
