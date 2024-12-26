package modele.clock;

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
    }

    public void undoTurn() {
        List <Command> turn = allTurns.remove(allTurns.size() - 1);
        for (Command cmd : turn) {
            cmd.undoCommand();
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

