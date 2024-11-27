package controleur;

import modele.Board;
import vue.Ihm;

import java.io.FileNotFoundException;

public class Controleur {
    protected Ihm ihm;
    protected Board board;
    public Controleur() {
        this.ihm = new Ihm();
    }

    public void startGame(){
        if (ihm.askBoard()){
            try{
                this.board = new Board(ihm.askFile());
            } catch (FileNotFoundException exception) {
                ihm.displayError("Le fichier indiqué n'a pas été trouver.");
                startGame();
                return;
            } catch (IllegalArgumentException exception) {
                ihm.displayError("Le fichier indiqué comporte des erreurs.");
                startGame();
                return;
            }
        } else {
            this.board = new Board();
        }
        System.out.println(board);
    }

}
