package modele.entity.movable.character.npc.state.predator;

import modele.Board;
import modele.clock.Clock;
import modele.clock.commands.PredatorAttackCommand;
import modele.clock.commands.PredatorMoveCoordinateCommand;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.movable.character.npc.state.State;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe abstraite représentant l'état d'un prédateur.
 */
public abstract class PredatorState implements State {
    /**
     * Le prédateur.
     */
    protected Predator predator;

    /**
     * Constructeur de la classe PredatorState.
     *
     * @param predator Le prédateur associé à cet état.
     */
    public PredatorState(Predator predator) {
        this.predator = predator;
    }

    /**
     * Vérifie si le prédateur peut se déplacer sur le terrain spécifié.
     *
     * @param terrain Le terrain à vérifier.
     * @return true si le prédateur peut se déplacer sur le terrain, sinon false.
     */
    @Override
    public boolean canMove(Terrain terrain) {
        return terrain != null && (terrain.isEmpty() || terrain.getEntityOnCase() instanceof Prey);
    }

    /**
     * Obtient le terrain où se trouve une proie.
     *
     * @return Le terrain où se trouve une proie, ou le terrain actuel si aucune proie n'est trouvée.
     */
    protected Terrain getPrey() {
        List<Terrain> neighbours = Board.getInstance().getNeighbours(predator.getX(), predator.getY());
        List<Terrain> casePossible = new ArrayList<>();
        for (Terrain terrain : neighbours) { // Chercher une case avec une proie.
            if (terrain.getEntityOnCase() instanceof Prey && !((Prey) terrain.getEntityOnCase()).isProtected(terrain, predator)) {
                casePossible.add(terrain);
            }
        }

        if (casePossible.isEmpty()) {
            return Board.getInstance().getAt(predator.getX(), predator.getY());
        } else {
            Prey preyEntity = (Prey) casePossible.get(0).getEntityOnCase();
            Clock.getInstance().addCommandToTurn(new PredatorAttackCommand(predator, preyEntity));
            return casePossible.get(0);
        }
    }

    /**
     * Obtient le terrain par défaut pour le déplacement du prédateur.
     *
     * @param forbidden Le terrain interdit pour le déplacement.
     * @return Le terrain par défaut pour le déplacement.
     */
    protected Terrain getDefault(Terrain forbidden) {
        List<Terrain> neighbours = Board.getInstance().getNeighbours(predator.getX(), predator.getY());
        List<Terrain> casePossible = new ArrayList<>();

        for (Terrain terrain : neighbours) { // Chercher une case possible.
            if (terrain.isEmpty() && canMove(terrain) && !terrain.equals(forbidden)) {
                casePossible.add(terrain);
            }
        }
        if (casePossible.isEmpty()) {
            Terrain currentCase = Board.getInstance().getAt(predator.getX(), predator.getY());
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, currentCase));
            return currentCase;
        } else {
            Terrain move = casePossible.get((int) (Math.random() * casePossible.size()));
            Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, move));
            return move;
        }

    }

    /**
     * Obtient le terrain par défaut pour le déplacement du prédateur dans un rayon de deux cases.
     *
     * @return Le terrain par défaut pour le déplacement dans un rayon de deux cases.
     */
    protected Terrain getDefault2Case() {
        List<List<Terrain>> neighbours = Board.getInstance().getNearSorted(predator.getX(), predator.getY(), 2);
        List<Terrain> casePossible = new ArrayList<>();

        for (int i = neighbours.size() - 1; i >= 0; --i) { // Commence à regarder à une distance de 2 puis se rapproche si aucune case n'est possible.
            for (Terrain terrain : neighbours.get(i)) {
                if (terrain.isEmpty() && canMove(terrain)) {
                    casePossible.add(terrain);
                }
            }
            if (!casePossible.isEmpty()) {
                Terrain terrain = casePossible.get((int) (Math.random() * casePossible.size()));
                Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
                return terrain;
            }
        }
        Terrain terrain = Board.getInstance().getAt(predator.getX(), predator.getY());
        Clock.getInstance().addCommandToTurn(new PredatorMoveCoordinateCommand(predator, terrain));
        return terrain;
    }

}
