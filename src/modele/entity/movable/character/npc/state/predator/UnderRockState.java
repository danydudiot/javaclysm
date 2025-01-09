package modele.entity.movable.character.npc.state.predator;

import exception.InvalidActionException;
import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.stationary.terrain.Terrain;

/**
 * Classe représentant l'état d'un prédateur sous un rocher.
 */
public class UnderRockState extends PredatorState {
    /**
     * Le scorpion sous le rocher
     */
    private Scorpio scorpio;

    /**
     * Constructeur de la classe UnderRockState.
     *
     * @param predator Le prédateur associé à cet état.
     * @throws InvalidActionException Si le prédateur n'est pas un scorpion.
     */
    public UnderRockState(Predator predator) {
        super(predator);
        if (predator instanceof Scorpio scorpio) {
            this.scorpio = scorpio;
            scorpio.setTimeUnderRock(5);
        } else {
            throw new InvalidActionException("Ce n'est pas un scorpion qui va sous le rocher.");
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
        return terrain.getEntityOnCase() == predator;
    }

    /**
     * Met à jour l'état du prédateur.
     * Réduit le temps passé sous le rocher et change l'état en ScorpioRaidState si le temps est écoulé.
     */
    @Override
    public void updateState() {
        scorpio.setTimeUnderRock(scorpio.getTimeUnderRock() - 1);
        if (scorpio.getTimeUnderRock() <= 0) {
            predator.setCurrentState(new ScorpioRaidState(predator));
        }
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
        if (((Scorpio) predator).canAttack()) {
            return Colors.WHITE_BACKGROUND + Colors.RED + predator.getRepresentation() + Colors.RESET;
        } else {
            return Colors.WHITE_BACKGROUND + Colors.LIGHT_BLACK + predator.getRepresentation() + Colors.RESET;
        }
    }
}