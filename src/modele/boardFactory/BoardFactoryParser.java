package modele.boardFactory;

import modele.Board;
import modele.boardFactory.generator.Generator;
import modele.boardFactory.generator.GeneratorForest;
import modele.boardFactory.generator.GeneratorJungle;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Génère la board en fonction du thème.
 */
public class BoardFactoryParser extends BoardFactory {
    /**
     * Le fichier de la carte.
     */
    protected File mapFile;

    /**
     * Constructeur de la classe BoardFactoryParser.
     * Initialise le fichier de la carte.
     */
    public BoardFactoryParser() {
//		 this.mapFile = new File("carteBasique.txt");
//		 this.mapFile = new File("carteRenard.txt");
        this.mapFile = new File("carteHibou.txt");
//		 this.mapFile = new File("carteSerpent.txt");
//		 this.mapFile = new File("carteScorpion.txt");
    }

    /**
     * Méthode pour créer la board en parsant le fichier.
     *
     * @throws FileNotFoundException si le fichier de la carte n'est pas trouvé.
     */
    @Override
    public void makeBoard() throws FileNotFoundException {
        parseBoard();
    }

    /**
     * Méthode pour parser le fichier de la carte et générer la board.
     *
     * @throws FileNotFoundException si le fichier de la carte n'est pas trouvé.
     */
    public void parseBoard() throws FileNotFoundException {
        Scanner scanner = new Scanner(mapFile);

        PlayerCharacter player = null;
        char theme = scanner.nextLine().charAt(0);
        int height = scanner.nextInt();
        int width = scanner.nextInt();

        Terrain[][] board = new Terrain[height][width];
        int y = 0;

        Generator generator;
        if (theme == 'F') {
            generator = new GeneratorForest();
        } else {
            generator = new GeneratorJungle();
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.contains("//")) { // Ligne vide (entré fantôme Scanner) et "//" commentaire dans la carte.
                continue;
            }
            for (int x = 0; x < width; x++) {
                if (x >= line.length()) {
                    board[y][x] = new Empty(x, y);
                } else {
                    board[y][x] = generator.parse(x, y, line.charAt(x));
                    if (board[y][x].getEntityOnCase() instanceof PlayerCharacter) {
                        player = (PlayerCharacter) board[y][x].getEntityOnCase();
                    }
                }
            }
            y++;
        }
        Board.buildBoard(theme, height, width, board, player);
    }
}
