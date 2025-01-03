package modele;

import exception.EntityNotFoundException;
import exception.InvalidActionException;
import modele.entity.Entity;
import modele.entity.movable.MovableEntity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Board {

    private static Board INSTANCE;
    private final Terrain[][] board;
    private final int height;
    private final int width;
    private final char theme;
    private final PlayerCharacter player;

    private List<String> logs;

    private Board(char theme, int height, int width, Terrain[][] board, PlayerCharacter player) {
        logs = new ArrayList<>();
        this.theme = theme;
        this.height = height;
        this.width = width;
        this.board = board;
        this.player = player;
    }

    private Board() {
        logs = new ArrayList<>();
        this.theme = 0;
        this.height = 0;
        this.width = 0;
        this.board = null;
        this.player = null;
    }

    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }

    public static void buildBoard(char theme, int height, int width, Terrain[][] board, PlayerCharacter player) {
        INSTANCE = new Board(theme, height, width, board, player);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public char getTheme() {
        return theme;
    }

    public Terrain getAt(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1) {
            return null;
        }
        return board[y][x];
    }

    public Terrain getToward(int x, int y, char direction) {
        return switch (direction) {
            case 'z' -> getAt(x, y - 1);
            case 's' -> getAt(x, y + 1);
            case 'q' -> getAt(x - 1, y);
            case 'd' -> getAt(x + 1, y);
            default -> getAt(x, y);
        };
    }

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

    public void moveToward(MovableEntity entity, char direction) {
        // On part du principe que le déplacement est toujours valide (testé en amont).
        clearCase(entity.getX(), entity.getY());
        setEntityOnCase(entity.getX(), entity.getY(), entity);
    }

    public void moveTo(MovableEntity entity, int x, int y) {
        // On part du principe que le déplacement est toujours valide (testé en amont).
        clearCase(entity.getX(), entity.getY());
        setEntityOnCase(x,y,entity);
    }

    public void clearCase(int x, int y) throws EntityNotFoundException {
        if (getAt(x,y) == null || getAt(x,y).getEntityOnCase() == null) {
            throw new EntityNotFoundException("L'entité ne peut pas être trouvée. (x= " + x + ", y= " + y + ")");
        }
        getAt(x,y).clearEntityOnCase();
    }

    public void fillCase(int x, int y, Entity entity) {
        Terrain new_position = getToward(x, y, getPlayer().getOrientation());
        if (new_position == null || new_position.getEntityOnCase() != null) {
            throw new InvalidActionException("Vous ne pouvez pas jeter quelque chose sur un case non vide");
        }
        entity.setPosition(x, y);
        new_position.setEntityOnCase(entity);
    }

    public void setEntityOnCase(int x, int y, Entity entity) {
        Terrain new_position = getAt(x,y);

        if (new_position == null || new_position.getEntityOnCase() != null) {
            throw new InvalidActionException("Case null ou non vide");
        }
        entity.setPosition(x, y);
        new_position.setEntityOnCase(entity);
    }

    public PlayerCharacter getPlayer() {
        return player;
    }

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

    public void logAction(String log) {
        logs.add(log);
    }

    public void logError(String error) {
        logs.add(Colors.RED + error + Colors.RESET);
    }

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
