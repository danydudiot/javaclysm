package modele.entity.stationary;

import modele.Board;
import modele.Colors;
import modele.InventoryItem;
import modele.clock.Clock;
import modele.interaction.GrabTimeStone;
import modele.interaction.Interactible;
import modele.interaction.Interaction;

import java.awt.*;

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
        } else {
            this.displayName = "Pierre Inutile";
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
                output.append(Colors.ANSI_CYAN);
            } else {
                output.append(Colors.ANSI_BLUE);
            }
            output.append("Voyage dans le temps de ")
                    .append(nbTours).append(" tours.")
                    .append(Colors.ANSI_RESET);
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

    /**
     * Retourne une représentation stylisée de la pierre précieuse.
     *
     * @return Une représentation stylisée la pierre précieuse.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        if (!isActive) {
            output.append(Colors.ANSI_LIGHT_BLACK);
        } else if (turns == 2) {
            output.append(Colors.ANSI_CYAN);
        } else {
            output.append(Colors.ANSI_BLUE);
        }
        output.append(turns);
        output.append(Colors.ANSI_RESET);
        return output.toString();
    }
}
