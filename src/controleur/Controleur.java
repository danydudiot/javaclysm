package controleur;

import modele.Board;
import vue.Ihm;

public class Controleur {
    protected Ihm ihm;
    protected Board board;
    public Controleur() {
        this.ihm = Ihm();
    }

    public void startGame(){
        if (ihm.askBoard()){
            this.board = new Board();
        } else {
            this.board = new Board(ihm.askFile());
        }
    }

}
