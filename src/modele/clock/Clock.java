package modele.clock;

import java.util.ArrayList;
import java.util.List;

public class Clock implements Observable{
    private List<Observateur> observateurs;
    private int nbTour;

    public Clock() {
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
    public void notifierObservateur() {
        nbTour++;
        for (Observateur observateur : observateurs){
            observateur.mettreAJour(this);
        }
    }
}
