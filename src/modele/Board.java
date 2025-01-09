package modele;

import exception.EntityNotFoundException;
import exception.InvalidActionException;
import modele.entity.Entity;
import modele.entity.movable.MovableEntity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Monkey;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe représentant le plateau de jeu.
 */
public class Board {
    /**
     * Instance unique de la classe Board (singleton).
     */
    private static Board INSTANCE;
    /**
     * Plateau de jeu représenté par une matrice de terrains.
     */
    private final Terrain[][] board;
    /**
     * Hauteur du plateau de jeu.
     */
    private final int height;
    /**
     * Largeur du plateau de jeu.
     */
    private final int width;
    /**
     * Thème du plateau de jeu.
     */
    private final char theme;
    /**
     * Personnage joueur sur le plateau de jeu.
     */
    private final PlayerCharacter player;
    /**
     * Liste des journaux d'actions.
     */
    private List<String> logs;

    /**
     * Constructeur privé pour initialiser le plateau de jeu.
     *
     * @param theme  Thème du plateau de jeu.
     * @param height Hauteur du plateau de jeu.
     * @param width  Largeur du plateau de jeu.
     * @param board  Matrice de terrains représentant le plateau de jeu.
     * @param player Personnage joueur sur le plateau de jeu.
     */
    private Board(char theme, int height, int width, Terrain[][] board, PlayerCharacter player) {
        logs = new ArrayList<>();
        this.theme = theme;
        this.height = height;
        this.width = width;
        this.board = board;
        this.player = player;
    }

    /**
     * Constructeur privé par défaut.
     */
    private Board() {
        logs = new ArrayList<>();
        this.theme = 0;
        this.height = 0;
        this.width = 0;
        this.board = null;
        this.player = null;
    }

    /**
     * Obtient l'instance unique de la classe Board.
     *
     * @return L'instance unique de la classe Board.
     */
    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }

    /**
     * Construit le plateau de jeu avec les paramètres spécifiés.
     *
     * @param theme  Thème du plateau de jeu.
     * @param height Hauteur du plateau de jeu.
     * @param width  Largeur du plateau de jeu.
     * @param board  Matrice de terrains représentant le plateau de jeu.
     * @param player Personnage joueur sur le plateau de jeu.
     */
    public static void buildBoard(char theme, int height, int width, Terrain[][] board, PlayerCharacter player) {
        INSTANCE = new Board(theme, height, width, board, player);
    }

    /**
     * Obtient la hauteur du plateau de jeu.
     *
     * @return La hauteur du plateau de jeu.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Obtient la largeur du plateau de jeu.
     *
     * @return La largeur du plateau de jeu.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Obtient le thème du plateau de jeu.
     *
     * @return Le thème du plateau de jeu.
     */
    public char getTheme() {
        return theme;
    }

    /**
     * Obtient le terrain à la position spécifiée.
     *
     * @param x Coordonnée x du terrain.
     * @param y Coordonnée y du terrain.
     * @return Le terrain à la position spécifiée, ou null si la position est invalide.
     */
    public Terrain getAt(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return null;
        }
        return board[y][x];
    }

    /**
     * Obtient le terrain dans la direction spécifiée à partir de la position donnée.
     *
     * @param x         Coordonnée x de départ.
     * @param y         Coordonnée y de départ.
     * @param direction Direction du déplacement ('z' pour haut, 's' pour bas, 'q' pour gauche, 'd' pour droite).
     * @return Le terrain dans la direction spécifiée, ou le terrain de départ si la direction est invalide.
     */
    public Terrain getToward(int x, int y, char direction) {
        return switch (direction) {
            case 'z' -> getAt(x, y - 1);
            case 's' -> getAt(x, y + 1);
            case 'q' -> getAt(x - 1, y);
            case 'd' -> getAt(x + 1, y);
            default -> getAt(x, y);
        };
    }

    /**
     * Obtient les terrains voisins de la position spécifiée.
     *
     * @param x Coordonnée x de la position.
     * @param y Coordonnée y de la position.
     * @return Une liste des terrains voisins.
     */
    public List<Terrain> getNeighbours(int x, int y) {
        List<Terrain> out = new ArrayList<>();
        for (char c : new char[]{'z', 'q', 's', 'd'}) {
            Terrain target = getToward(x, y, c);
            if (target != null) {
                out.add(target);
            }
        }
        return out;
    }

    /**
     * Déplace une entité mobile dans la direction spécifiée.
     *
     * @param entity    L'entité mobile à déplacer.
     * @param direction La direction du déplacement.
     */
    public void moveToward(MovableEntity entity, char direction) {
        Terrain terrain = getToward(entity.getX(), entity.getY(), direction);
        // On part du principe que le déplacement est toujours valide (testé en amont).
        clearCase(entity.getX(), entity.getY());
        terrain.setEntityOnCase(entity);
    }

    /**
     * Déplace une entité mobile à la position spécifiée.
     *
     * @param entity L'entité mobile à déplacer.
     * @param x      Coordonnée x de la nouvelle position.
     * @param y      Coordonnée y de la nouvelle position.
     */
    public void moveTo(MovableEntity entity, int x, int y) {
        // On part du principe que le déplacement est toujours valide (testé en amont).
        clearCase(entity.getX(), entity.getY());
        setEntityOnCase(x, y, entity);
    }

    /**
     * Vide la case à la position spécifiée.
     *
     * @param x Coordonnée x de la case.
     * @param y Coordonnée y de la case.
     * @throws EntityNotFoundException Si aucune entité n'est trouvée sur la case.
     */
    public void clearCase(int x, int y) throws EntityNotFoundException {
        if (getAt(x, y) == null || getAt(x, y).getEntityOnCase() == null) {
            throw new EntityNotFoundException("L'entité ne peut pas être trouvée. (x= " + x + ", y= " + y + ")");
        }
        getAt(x, y).clearEntityOnCase();
    }

    /**
     * Remplit la case à la position spécifiée avec une entité.
     *
     * @param x      Coordonnée x de la case.
     * @param y      Coordonnée y de la case.
     * @param entity L'entité à placer sur la case.
     */
    public void fillCase(int x, int y, Entity entity) {
        Terrain new_position = getToward(x, y, getPlayer().getOrientation());
        if (new_position == null || new_position.getEntityOnCase() != null) {
            throw new InvalidActionException("Vous ne pouvez pas jeter quelque chose sur un case non vide");
        }
        entity.setPosition(x, y);
        new_position.setEntityOnCase(entity);
    }

    /**
     * Place une entité sur la case à la position spécifiée.
     *
     * @param x      Coordonnée x de la case.
     * @param y      Coordonnée y de la case.
     * @param entity L'entité à placer sur la case.
     */
    public void setEntityOnCase(int x, int y, Entity entity) {
        Terrain new_position = getAt(x, y);
        if (new_position != null && (new_position.getEntityOnCase() == null || (entity instanceof Monkey && new_position.getEntityOnCase() instanceof Scorpio scorpio && scorpio.canAttack()))) {

            entity.setPosition(x, y);
            new_position.setEntityOnCase(entity);
        } else {//(new_position == null || new_position.getEntityOnCase() != null) {
            throw new InvalidActionException("Case null ou non vide");
        }
    }

    /**
     * Obtient le personnage joueur sur le plateau de jeu.
     *
     * @return Le personnage joueur sur le plateau de jeu.
     */
    public PlayerCharacter getPlayer() {
        return player;
    }

    /**
     * Obtient le plateau de jeu sous forme de liste de listes de chaînes de caractères.
     *
     * @return Le plateau de jeu sous forme de liste de listes de chaînes de caractères.
     */
    public List<List<String>> getBoardAsList() {
        List<List<String>> board_list = new ArrayList<>();
        for (Terrain[] terrains : board) { //(Terrain[] line : board){
            List<String> line_list = new ArrayList<>();
            for (Terrain terrain : terrains) {//(Terrain cell : line){
                if (terrain == null) {
                    System.out.println("null");
                } else {
                    line_list.add(terrain.toString());
                }
            }
            board_list.add(line_list);
        }
        return board_list;
    }

    /**
     * Retourne une représentation en chaîne de caractères du plateau de jeu.
     *
     * @return Une représentation en chaîne de caractères du plateau de jeu.
     */
    @Override
    public String toString() {
        StringBuilder board_string = new StringBuilder();
        for (int i = 0; i < board.length; i++) { //( Terrain[] line : board){
            for (int j = 0; j < board[i].length; j++) { // (Terrain cell : line){
                if (board[i][j] == null) {
                    System.out.println("null");
                }
                board_string.append(board[i][j].toString());
            }
            board_string.append("\n");
        }
        return board_string.toString();
    }

    /**
     * Enregistre une action dans les journaux.
     *
     * @param log L'action à enregistrer.
     */
    public void logAction(String log) {
        logs.add(log);
    }

    /**
     * Enregistre une erreur dans les journaux.
     *
     * @param error L'erreur à enregistrer.
     */
    public void logError(String error) {
        logs.add(Colors.RED + error + Colors.RESET);
    }

    /**
     * Obtient les derniers journaux d'actions.
     *
     * @param amount Le nombre de journaux à obtenir.
     * @return Une liste des derniers journaux d'actions.
     */
    public List<String> peekAtLogs(int amount) {
        List<String> out = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            try {
                out.add(logs.get(logs.size() - i - 1));
            } catch (IndexOutOfBoundsException e) {
                out.add("");
            }
        }
        return out;
    }

    /**
     * Obtient les terrains proches de la position spécifiée, triés par distance.
     *
     * @param x       Coordonnée x de la position.
     * @param y       Coordonnée y de la position.
     * @param nbCases Nombre de cases à considérer.
     * @return Une liste de listes de terrains proches, triés par distance.
     */
    public List<List<Terrain>> getNearSorted(int x, int y, int nbCases) {
        List<List<Terrain>> out = new ArrayList<>();
        List<Terrain> all = new ArrayList<>();
        for (int i = 0; i <= nbCases; ++i) {
            out.add(new ArrayList<>());
        }
        out.get(0).add(getAt(x, y));
        all.add(getAt(x, y));
        for (int i = 1; i <= nbCases; ++i) {
            for (Terrain terrain : out.get(i - 1)) {
                for (Terrain neighbour : getNeighbours(terrain.getX(), terrain.getY())) {
                    if (!(all.contains(neighbour))) {
                        out.get(i).add(neighbour);
                        all.add(neighbour);
                    }
                }
            }
        }
        return out;
    }

    /**
     * Obtient les terrains proches de la position spécifiée.
     *
     * @param x       Coordonnée x de la position.
     * @param y       Coordonnée y de la position.
     * @param nbCases Nombre de cases à considérer.
     * @return Une liste de terrains proches.
     */
    public List<Terrain> getNear(int x, int y, int nbCases) {
        List<Terrain> all = new ArrayList<>();
        List<Terrain> toTest = new ArrayList<>();

        all.add(getAt(x, y));
        toTest.add(getAt(x, y));
        for (int i = 1; i <= nbCases; ++i) {
            List<Terrain> next = new ArrayList<>();
            for (Terrain terrain : toTest) {
                for (Terrain neighbour : getNeighbours(terrain.getX(), terrain.getY())) {
                    if (!all.contains(neighbour)) {
                        all.add(neighbour);
                        next.add(neighbour);
                    }
                }
            }
            toTest = next;
        }
        return all;
    }
}
