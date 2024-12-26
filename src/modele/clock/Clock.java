package modele.clock;

import modele.Board;

import java.util.ArrayList;
import java.util.List;

public final class Clock implements Observable{
    private static Clock INSTANCE;
    private List<Observateur> observateurs;
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
    public void notifierObservateur(Board board) {
        nbTour++;
        for (Observateur observateur : observateurs){
            observateur.mettreAJour(board);
        }
    }
}
