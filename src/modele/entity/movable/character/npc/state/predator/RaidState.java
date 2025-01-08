package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe abstraite représentant l'état de raid d'un prédateur.
 */
public abstract class RaidState extends PredatorState {

    /**
     * Constructeur de la classe RaidState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public RaidState(Predator predator) {
        super(predator);
    }

    /**
     * Gère le déplacement du prédateur.
     * Si la proie est trouvée, se déplace vers elle. Sinon, utilise la méthode getDefault pour le déplacement par défaut.
     */
    @Override
    public void deplacement() {
        Terrain move = getPrey();
        if (move.equals(Board.getInstance().getAt(predator.getX(), predator.getY()))) {
            getDefault(null);
        }
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du prédateur.
     *
     * @return Une chaîne de caractères représentant le prédateur avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        return Colors.LIGHT_RED + predator.getRepresentation() + Colors.RESET;
    }
}