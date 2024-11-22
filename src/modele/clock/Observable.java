package modele.clock;

public interface Observable {
    public void attacher(Observateur observateur);
    public void detacher(Observateur observateur);
    public void notifierObservateur();
}
