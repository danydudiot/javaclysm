package modele.entity.stationary;

import modele.Board;
import modele.Colors;
import modele.clock.Clock;
import modele.InventoryItem;
import modele.interaction.Interaction;
import modele.interaction.Interactible;
import modele.interaction.GrabTimeStone;

/**
 * Classe représentant les pierres précieuses permettant de revenir en arrière.
 */
public class TimeStone extends StaticEntity implements Interactible, InventoryItem {
    /**
     * Liste des interactions possibles.
     */
    private Interaction[] interactions;

    /**
     * Le nombre de tours a annulé.
     */
    private int turns;

    /**
     * Si la pierre possède toujours son pouvoir.
     */
    private boolean isActive;


    /**
     * Constructeur de la pierre précieuse.
     *
     * @param x     La position vertical.
     * @param y     La position horizontal.
     * @param turns Le nombre de tours a annulé.
     */
    public TimeStone(int x, int y, int turns) {
        super(x, y);
        this.turns = turns;
        this.interactions = new Interaction[1];
        interactions[0] = new GrabTimeStone();
        setActive(true);
    }

    /**
     * Permet d'activer ou désactiver le pouvoir de la pierre précieuse.
     *
     * @param active L'état de la pierre.
     */
    public void setActive(boolean active) {
        isActive = active;
        if (isActive) {
            this.displayName = "Pierre Temporelle";
            if (turns == 2) {
                this.representation = Colors.CYAN + turns + Colors.RESET;
            } else {
                this.representation = Colors.BLUE + turns + Colors.RESET;
            }
        } else {
            this.displayName = "Pierre Inutile";
            this.representation = Colors.LIGHT_BLACK + turns + Colors.RESET;
        }
    }

    /**
     * Effectue un voyage dans le temps en annulant un certain nombre de tours si la pierre est active.
     */
    public void timeTravel() {
        if (isActive) {
            int nbTours = Math.min(Clock.getInstance().getNbTour(), turns);
            for (int i = 0; i < nbTours; ++i) {
                Clock.getInstance().undoLastTurn();
            }
            StringBuilder output = new StringBuilder();
            if (nbTours == 2) {
                output.append(Colors.CYAN);
            } else {
                output.append(Colors.BLUE);
            }
            output.append("Voyage dans le temps de ")
                    .append(nbTours).append(" tours.")
                    .append(Colors.RESET);
            Board.getInstance().logAction(output.toString());
        }
    }

    /**
     * Retourne la liste des interactions possibles avec la pierre précieuse.
     *
     * @return Un tableau d'interactions.
     */
    @Override
    public Interaction[] getInteractions() {
        return interactions;
    }
}
