package modele;

import exception.EntityNotFoundException;
import exception.InvalidActionException;
import exception.InvalidArgumentException;
import exception.MoveInvalidException;
import modele.clock.Clock;
import modele.clock.commands.EatPreyCommand;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.predator.Predator;
import modele.entity.movable.character.npc.predator.Scorpio;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.ScorpioRock;
import modele.entity.stationary.terrain.low.Rock;

import java.util.*;

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

    public Terrain[][] getBoard() {
        return board;
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
        switch (direction) {
            case 'z':
                return getAt(x, y - 1);
            case 's':
                return getAt(x, y + 1);
            case 'q':
                return getAt(x - 1, y);
            case 'd':
                return getAt(x + 1, y);
            default:
                return getAt(x, y);
        }
    }

    public Map<Character, Terrain> getNeighbours(int x, int y) {
        Map<Character, Terrain> out = new HashMap<>();
        for (char c : new char[]{'z', 'q', 's', 'd'}) {
            Terrain target = getToward(x, y, c);
            if (target != null) {
                out.put(c, target);
            }
        }
        return out;
    }

//    public int[] moveEntity(int x, int y, char direction) throws MoveInvalidException, EntityNotFoundException, InvalidArgumentException {
//        if (board[y][x].getEntityOnCase() == null) {
//            throw new EntityNotFoundException("Entité non trouver. x=" + x + " y=" + y);
//        } else {
//            int new_x;
//            int new_y;
//            if (direction == 'z') {
//                new_x = x;
//                new_y = y - 1;
//            } else if (direction == 'q') {
//                new_x = x - 1;
//                new_y = y;
//            } else if (direction == 's') {
//                new_x = x;
//                new_y = y + 1;
//            } else if (direction == 'd') {
//                new_x = x + 1;
//                new_y = y;
//            } else if (direction == 'a') {
////                NPC IS STUCK AND CANNOT MOVE
//                return new int[]{x, y};
//            } else {
//                throw new InvalidArgumentException("Déplacement inconnue");
//            }
//            if (new_x < 0 || new_x >= width || new_y < 0 || new_y >= height) {
//                logError("Le mouvement est en dehors de la carte.");
//                return new int[]{x, y};
//            }
//            return moveEntity(x, y, new_x, new_y);
//        }
//    }
//
//
//    public int[] moveEntity(int x, int y, int new_x, int new_y) throws MoveInvalidException, EntityNotFoundException, InvalidArgumentException {
//        if (board[y][x].getEntityOnCase() == null) {
//            throw new EntityNotFoundException("Entité non trouver. x=" + x + " y=" + y);
//        }
//        Entity entity = board[y][x].getEntityOnCase();
//        if (entity instanceof PlayerCharacter) {
//            if (!(board[new_y][new_x].getClass() == Empty.class && board[new_y][new_x].getEntityOnCase() == null)) {
////                    throw new MoveInvalidException("Le joueur ne peut pas aller sur cette case.");
//                logError("Le joueur ne peut pas aller sur cette case");
//                return new int[]{x, y};
//            } else {
//                board[new_y][new_x].setEntityOnCase(entity);
//                board[y][x].clearEntityOnCase();
//            }
//        } else if (entity instanceof Prey) {
//            if (board[new_y][new_x].getEntityOnCase() == null) {
//                board[new_y][new_x].setEntityOnCase(entity);
//                board[y][x].clearEntityOnCase();
//            } else if (board[new_y][new_x].getEntityOnCase() instanceof Food) {
//                Food food = (Food) board[new_y][new_x].getEntityOnCase();
//                board[new_y][new_x].setEntityOnCase(entity);
//                board[y][x].clearEntityOnCase();
//                Clock.getInstance().addCommandToTurn(new EatPreyCommand((Prey) entity, food));
//            } else {
//                throw new MoveInvalidException("L'animal ne peut pas aller sur cette case.");
//            }
//        } else if (entity instanceof Predator) {
//            if (entity instanceof Scorpio) {
//                if (board[new_y][new_x].getEntityOnCase() instanceof Prey){
//                    Prey prey = (Prey) board[new_y][new_x].getEntityOnCase();
//                    prey.hit((Predator) entity);
//                }
//                if (board[y][x] instanceof ScorpioRock){
//                    Scorpio scorpio = ((ScorpioRock) board[y][x]).getScorpio();
//                    board[y][x] = new Rock(x,y);
//                    board[y][x].setEntityOnCase(scorpio);
//                }
//                if (board[new_y][new_x].getEntityOnCase() == null && board[new_y][new_x] instanceof Rock) {
//                    board[new_y][new_x] = new ScorpioRock(new_x, new_y, (Scorpio) entity);
//                    board[y][x].clearEntityOnCase();
//                } else if (board[new_y][new_x].getEntityOnCase() == null) {
//                    board[new_y][new_x].setEntityOnCase(entity);
//                    board[y][x].clearEntityOnCase();
//                } else {
//                    throw new MoveInvalidException("Le scorpion ne peut pas aller sur cette case.");
//                }
//            } else {
//                if (board[new_y][new_x].getEntityOnCase() == null) {
//                    board[new_y][new_x].setEntityOnCase(entity);
//                    board[y][x].clearEntityOnCase();
//                } else if (board[new_y][new_x].getEntityOnCase() instanceof Prey) {
//                    Prey prey = (Prey) board[new_y][new_x].getEntityOnCase();
//                    prey.hit((Predator) entity);
//                    board[new_y][new_x].setEntityOnCase(entity);
//                    board[y][x].clearEntityOnCase();
//                } else {
//                    throw new MoveInvalidException("Le prédateur ne peut pas aller sur cette case.");
//                }
//            }
//        }
//        return new int[]{new_x, new_y};
//
//    }


    public void clearCase(int x, int y) throws EntityNotFoundException {
        if (board[y][x].getEntityOnCase() == null) {
            throw new EntityNotFoundException("L'entité ne peut pas être trouvée.");
        } else {
            board[y][x].clearEntityOnCase();
        }
    }


    public void fillCase(int x, int y, char direction, Entity entity) {
        Terrain new_position = getToward(x, y, getPlayer().getOrientation());
        if (new_position == null || new_position.getEntityOnCase() != null) {
            throw new InvalidActionException("Vous ne pouvez pas jeter quelque chose sur un case non vide");
        }
        entity.setPosition(new_position.getX(), new_position.getY());
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
        for (int i = 0; i < board.length; i++) { //(Terrain[] line : board){
            for (int j = 0; j < board[i].length; j++) {//(Terrain cell : line){
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

    public List<Terrain> getNear(int x, int y, int nbCases) {
        List<Terrain> listNear = new ArrayList<>();
        for (int i = Math.max(x - nbCases, 0); i < Math.min(x + nbCases, width); i++) {
            for (int j = Math.max(y - nbCases, 0); j < Math.min(y + nbCases, height); j++) {
                if ((Math.abs(x - i) + Math.abs(y - j)) <= nbCases) {
                    listNear.add(board[j][i]);
                }
            }
        }
        return listNear;
    }
}
