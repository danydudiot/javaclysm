package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant l'état de repos d'un prédateur.
 */
public abstract class RestState extends PredatorState {

    /**
     * Constructeur de la classe RestState.
     *
     * @param predator  Le prédateur associé à cet état.
     * @param restLevel Le niveau de repos initial.
     */
    public RestState(Predator predator, int restLevel) {
        super(predator);
        predator.setRestLevel(restLevel);
    }

    /**
     * Vérifie si le prédateur peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return false car le prédateur ne peut pas se déplacer en état de repos.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return false;
    }

    /**
     * Met à jour l'état du prédateur.
     * Si le niveau de repos est inférieur ou égal à zéro, passe à l'état suivant.
     */
    @Override
    public void updateState() {
        if (predator.getRestLevel() <= 0) {
            predator.setCurrentState(getNextState());
        }
        predator.setRestLevel(predator.getRestLevel() - 1);
    }

    /**
     * Gère le déplacement du prédateur.
     * Ajoute une commande pour déplacer le prédateur à sa position actuelle.
     */
    @Override
    public void deplacement() {
        Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, Board.getInstance().getAt(predator.getX(), predator.getY())));
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du prédateur.
     *
     * @return Une chaîne de caractères représentant le prédateur avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
    }

    /**
     * Obtient le prochain état du prédateur.
     *
     * @return Le prochain état du prédateur.
     */
    protected abstract PredatorState getNextState();
}