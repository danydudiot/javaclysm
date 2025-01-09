package modele.clock;

import modele.Board;
import modele.Colors;
import modele.clock.commands.*;
import modele.entity.movable.character.npc.predator.Predator;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une horloge qui gère les tours et les observateurs dans le jeu.
 * Permet aussi de revenir dans le passé.
 */
public final class Clock implements Observable {
    /**
     * Instance unique de la classe Clock (Singleton).
     */
    private static Clock INSTANCE;
    /**
     * Liste des observateurs attachés à l'horloge.
     */
    private List<Observateur> observateurs;
    /**
     * Liste de tous les tours joués.
     */
    private List<List<Command>> allTurns;
    /**
     * Liste des commandes du tour actuel.
     */
    private List<Command> currentTurn;
    /**
     * Nombre de tours joués.
     */
    private int nbTour;

    /**
     * Constructeur privé de la classe Clock.
     * Initialise l'horloge en réinitialisant ses attributs.
     */
    private Clock() {
        reset();
    }

    /**
     * Retourne l'instance unique de la classe Clock.
     *
     * @return L'instance unique de la classe Clock.
     */
    public static Clock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Clock();
        }
        return INSTANCE;
    }

    /**
     * Réinitialise l'horloge en remettant à zéro les attributs.
     */
    public void reset() {
        this.nbTour = 0;
        this.observateurs = new ArrayList<>();
        this.allTurns = new ArrayList<>();
        this.currentTurn = new ArrayList<>();
    }

    /**
     * Réinitialise l'horloge en remettant à zéro les attributs.
     * @return Le numéro du tour.
     */
    public int getNbTour() {
        return nbTour;
    }

    /**
     * Attache un observateur à l'horloge.
     *
     * @param observateur L'observateur à attacher.
     */
    @Override
    public void attacher(Observateur observateur) {
        // Make the predator act first.
        if (observateur instanceof Predator) {
            observateurs.add(0, observateur);
        } else {
            observateurs.add(observateur);

        }
    }

    /**
     * Détache un observateur de l'horloge.
     *
     * @param observateur L'observateur à détacher.
     */
    @Override
    public void detacher(Observateur observateur) {
        observateurs.remove(observateur);
    }

    /**
     * Notifie tous les observateurs attachés de l'horloge puis enregistre le tour.
     */
    @Override
    public void notifierObservateur() {
        nbTour++;
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour();
        }
        historizeTurn();
    }

    /**
     * Annule le dernier tour joué.
     */
    public void undoLastTurn() {
        if (nbTour > 0) {
            List<Command> turn = allTurns.remove(allTurns.size() - 1);
            while (!turn.isEmpty()) {
                turn.remove(turn.size() - 1).undoCommand();
            }
            nbTour--;
        } else {
            Board.getInstance().logAction(Colors.BLUE_BACKGROUND + "Je ne peut pas annuler le Big Bang chef..." + Colors.RESET);
        }
    }

    /**
     * Ajoute une commande au tour actuel et exécute la commande.
     *
     * @param cmd La commande à ajouter.
     */
    public void addCommandToTurn(Command cmd) {
        cmd.doCommand();
        currentTurn.add(cmd);
    }

    /**
     * Ajoute une commande au tour actuel avant d'exécuter la commande.
     *
     * @param cmd La commande à ajouter.
     */
    public void addCommandToTurnBefore(Command cmd) {
        currentTurn.add(cmd);
        cmd.doCommand();
    }

    /**
     * Historise le tour actuel en l'ajoutant à la liste des tours joués.
     */
    public void historizeTurn() {
        allTurns.add(new ArrayList<>(currentTurn));
        currentTurn = new ArrayList<>();
    }
}

