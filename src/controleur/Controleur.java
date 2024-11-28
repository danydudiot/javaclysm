package controleur;

import modele.Board;
import modele.Inventory;
import modele.clock.Clock;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.interaction.Grab;
import modele.interaction.Interactible;
import modele.interaction.Interaction;
import vue.Ihm;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Controleur {
    protected Ihm ihm;
    protected Board board;
    protected PlayerCharacter playerCharacter;
    protected Clock clock;
    protected Inventory inventory;

    public Controleur() {
        this.ihm = new Ihm();
        this.clock = new Clock();
        this.inventory = new Inventory();
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


    private void game(){
        Deque<String> deque = new ArrayDeque<>(3);
        deque.add("Element 1");
        deque.add("Element 2");
        deque.add("Element 3");
        while (true){
            ihm.display(board.getBoardAsList(), board.getHeight(), board.getWidth(), deque, playerCharacter.getPosition()[0], playerCharacter.getPosition()[1], playerCharacter.getOrientation(), "Pas d'inventaire");
            char action = ihm.askAction();
            if ("zqsd".indexOf(action) != -1){
                 playerCharacter.move(action, board);
            } else if ("oklm".indexOf(action) != -1) {
                playerCharacter.changeOrientation(action);
            } else if ("i".indexOf(action) != -1) {
                System.out.println(inventory);
            } else if ("e".indexOf(action) != -1) {
                interation();
            }
            clock.notifierObservateur(board);
        }
    }

    private void interation(){
        int[] position = playerCharacter.getTarget();
        Entity entity = board.getAt(position[0],position[1]).getEntityOnCase();
        if (entity instanceof Interactible){
            Interactible interactible = (Interactible) entity;
            Interaction[] interactions = interactible.getInteraction();
            for (int i = 0; i < interactions.length; i++) {
                System.out.println(interactions[i].getDisplayName());
            }
            Scanner sc = new Scanner(System.in);
            int numInteraction = sc.nextInt();
            if (numInteraction < interactions.length){
                interactions[0].interact(inventory, board, (Entity) interactible);
            }
        } else {
            System.out.println("pas d'interaction disponible");
        }

    }
}
