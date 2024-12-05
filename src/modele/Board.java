package modele;

import exception.EntityNotFoundException;
import exception.InvalidArgumentException;
import exception.MoveInvalidException;
import modele.clock.Clock;
import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.Monkey;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Food;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;
import modele.entity.stationary.terrain.low.Rock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Board {
    private Terrain[][] board;
    private int height;
    private int width;
    private char theme;
    private PlayerCharacter player;

    private List<String> logs;


    /**
     * Construction de génération
     */
    public Board(Clock clock) {
        logs = new ArrayList<>();
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
        return board[y][x];
    }

    public Map<Character, Terrain> getNeighbours(int x, int y) {
        Map<Character, Terrain> out = new HashMap<>();
        out.put('z', (Terrain) getAt(x, y-1));
        out.put('s', (Terrain) getAt(x, y+1));
        out.put('q', (Terrain) getAt(x-1, y));
        out.put('d', (Terrain) getAt(x+1, y));
        return out;
    }

    public Board(Clock clock, char theme, int height, int width, Terrain[][] board) {
        logs = new ArrayList<>();
        this.theme = theme;
        this.height = height;
        this.width = width;
        this.board = board;
    }

    public int[] moveEntity(int x, int y, char direction) throws MoveInvalidException, EntityNotFoundException, InvalidArgumentException {
        if (board[y][x].getEntityOnCase() == null) {
            throw new EntityNotFoundException();
        } else {
            int new_x;
            int new_y;
            if (direction == 'z') {
                new_x = x;
                new_y = y - 1;
            } else if (direction == 'q') {
                new_x = x - 1;
                new_y = y;
            } else if (direction == 's') {
                new_x = x;
                new_y = y + 1;
            } else if (direction == 'd') {
                new_x = x + 1;
                new_y = y;
            } else if (direction == 'a') {
//                NPC IS STUCK AND CANNOT MOVE
                new_x = x;
                new_y = y;
            } else {
                throw new InvalidArgumentException("Déplacement inconnue");
            }
            if (new_x < 0 || new_x >= width || new_y < 0 || new_y >= height) {
                throw new MoveInvalidException("Le mouvement est en dehors de la carte.");
            }
            Entity entity = board[y][x].getEntityOnCase();
            if (entity.getClass() == PlayerCharacter.class) {
                if (!(board[new_y][new_x].getClass() == Empty.class && board[new_y][new_x].getEntityOnCase() == null)) {
                    throw new MoveInvalidException("Le joueur ne peut pas aller sur cette case.");
                } else {
                    board[new_y][new_x].setEntityOnCase(entity);
                    board[y][x].clearEntityOnCase();
                }
            } else if (entity.getClass() == Monkey.class || entity.getClass() == Squirrel.class) {
				if (board[new_y][new_x].getEntityOnCase() == null) {
                    board[new_y][new_x].setEntityOnCase(entity);
                    board[y][x].clearEntityOnCase();
                } else if (board[new_y][new_x].getEntityOnCase() instanceof Food) {
                    board[new_y][new_x].setEntityOnCase(entity);
                    board[y][x].clearEntityOnCase();
                    Map<Character, Terrain> neighbours = getNeighbours(x, y);
                    boolean isPlayerNearby = false;
                    for (char k : neighbours.keySet()) {
                        if (neighbours.get(k).getEntityOnCase() instanceof PlayerCharacter) {
                            isPlayerNearby = true;
                            break;
                        }
                    }
                    ((NonPlayerCharacter) entity).eat(isPlayerNearby, this);
                } else {
                    throw new MoveInvalidException("L'animal ne peut pas aller sur cette case.");
                }
            }
            return new int[]{new_x,new_y};
        }
    }

    public void clearCase(int x, int y) throws EntityNotFoundException {
        if (board[y][x].getEntityOnCase() == null) {
            throw new EntityNotFoundException("L'entité ne peut pas être trouvée.");
        } else {
            board[y][x].clearEntityOnCase();
        }
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
        for (int i=0;i< board.length;i++){ //(Terrain[] line : board){
            for (int j = 0; j< board[i].length;j++){//(Terrain cell : line){
                if (board[i][j] == null){
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

    public List<String> peekAtLogs(int amount) {
        List<String> out = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            try {
                out.add(logs.get(logs.size()-i-1));
            } catch (IndexOutOfBoundsException e) {
                out.add("");
            }
        }
        return out;
    }
}
