package modele.clock;

import modele.Board;

import javax.swing.*;

public interface Observable {
    public void attacher(Observateur observateur);
    public void detacher(Observateur observateur);
    public void notifierObservateur(Board board);
}
