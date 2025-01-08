package modele.boardFactory;

import java.io.FileNotFoundException;

/**
 * La classe qui crée la Board.
 */
public abstract class BoardFactory {
    /**
     * Permet de crée la Board.
     *
     * @throws FileNotFoundException Si en mode parseur, le fichier n'est pas trouvé.
     */
    public abstract void makeBoard() throws FileNotFoundException;
}
