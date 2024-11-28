package controleur;

import modele.Board;
import modele.clock.Clock;
import modele.entity.movable.character.PlayerCharacter;
import vue.Ihm;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Controleur {
    protected Ihm ihm;
    protected Board board;
    protected PlayerCharacter playerCharacter;
    protected Clock clock;

    public Controleur() {
        this.ihm = new Ihm();
        this.clock = new Clock();
    }

    public void startGame(){

        if (ihm.askBoard()){
            try{
                this.board = new Board(ihm.askFile(), clock);
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
            this.board = new Board(clock);
        }
        playerCharacter = board.getPlayer();
        game();
    }


    private void game() {
        while (true){
            ihm.display(board.getBoardAsList(), board.getHeight(), board.getWidth(), board.peekAtLogs(3), playerCharacter.getPosition()[0], playerCharacter.getPosition()[1], playerCharacter.getOrientation(), "Pas d'inventaire");
            char action = ihm.askAction();
            if ("zqsd".indexOf(action) != -1){
                 playerCharacter.move(action, board);
            } else if ("oklm".indexOf(action) != -1) {
                playerCharacter.changeOrientation(action);
            }
            clock.notifierObservateur(board);
        }
    }


}
