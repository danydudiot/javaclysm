package modele.clock;

import modele.Board;
import modele.Colors;
import modele.clock.commands.Command;

import java.util.ArrayList;
import java.util.List;

public final class Clock implements Observable{
    private static Clock INSTANCE;
    private List<Observateur> observateurs;
    private List<List<Command>> allTurns;

    private List<Command> currentTurn;
    private int nbTour;

    private Clock() {
        reset();
    }

    public static Clock getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Clock();
        }
        return INSTANCE;
    }

    public void reset() {
        this.nbTour = 0;
        this.observateurs = new ArrayList<>();
        this.allTurns = new ArrayList<>();
        this.currentTurn = new ArrayList<>();
    }

    public int getNbTour() {
        return nbTour;
    }

    @Override
    public void attacher(Observateur observateur) {
        observateurs.add(observateur);
    }

    @Override
    public void detacher(Observateur observateur) {
        observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateur() {
        nbTour++;
        for (Observateur observateur : observateurs){
            observateur.mettreAJour();
        }
        historizeTurn();
    }

    public void undoLastTurn() {
        if (nbTour > 0) {
            List <Command> turn = allTurns.remove(allTurns.size() - 1);
            while (! turn.isEmpty()) {
                turn.remove(turn.size()-1).undoCommand();
            }
            nbTour--;
        } else {
            Board.getInstance().logAction(Colors.ANSI_BLUE_BACKGROUND + "Je ne peut pas annuler le Big Bang chef..." + Colors.ANSI_RESET);
        }
    }

    public void addCommandToTurn(Command cmd) {
        cmd.doCommand();
        currentTurn.add(cmd);
    }

    public void historizeTurn() {
        allTurns.add(new ArrayList<>(currentTurn));
        currentTurn = new ArrayList<>();
    }
}

