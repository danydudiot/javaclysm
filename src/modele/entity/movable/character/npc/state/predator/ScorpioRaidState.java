package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.Colors;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.low.Rock;

/**
 * Classe représentant l'état de raid d'un scorpion (Scorpio).
 */
public class ScorpioRaidState extends RaidState {

    /**
     * Constructeur de la classe ScorpioRaidState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public ScorpioRaidState(Predator predator) {
        super(predator);
    }

    /**
     * Met à jour l'état du prédateur.
     * Si le prédateur se trouve sur un rocher, change l'état en UnderRockState.
     */
    @Override
    public void updateState() {
        if (Board.getInstance().getAt(predator.getX(), predator.getY()) instanceof Rock) {
            predator.setCurrentState(new UnderRockState(predator));
        }
    }

    /**
     * Vérifie si le prédateur peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le prédateur peut se déplacer sur le terrain, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain instanceof Empty || terrain instanceof Rock;
    }

    /**
     * Applique un modificateur de couleur à la représentation visuelle du prédateur.
     *
     * @return Une chaîne de caractères représentant le prédateur avec un modificateur de couleur.
     */
    @Override
    public String applyColorModifier() {
        if (((Scorpio) predator).canAttack()) {
            return Colors.RED + predator.getRepresentation() + Colors.RESET;
        } else {
            return Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
        }
    }
}