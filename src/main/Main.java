package main;

import controleur.Controleur;

/**
 * La classe Main.
 */
public class Main {
    /**
     * La méthode main.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        Controleur controleur = new Controleur();
        controleur.startGame();
    }
}