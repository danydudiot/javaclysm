package modele;

import modele.entity.Entity;
import modele.entity.movable.character.PlayerCharacter;
import modele.entity.movable.character.npc.Monkey;
import modele.entity.movable.character.npc.NonPlayerCharacter;
import modele.entity.movable.character.npc.Squirrel;
import modele.entity.stationary.food.Acorn;
import modele.entity.stationary.food.Banana;
import modele.entity.stationary.food.Mushroom;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;
import modele.entity.stationary.terrain.high.PalmTree;
import modele.entity.stationary.terrain.high.Tree;
import modele.entity.stationary.terrain.low.Bush;
import modele.entity.stationary.terrain.low.Rock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    private Entity[][] board;
    private int height;
    private int width;
    private char theme;

    /**
     * Construction de génération
     */
    public Board() {
    }

    public Entity[][] getBoard() {
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

    public Entity getAt(int x, int y) {
        return board[y][x];
    }

    /**
     * Construction de parseur
     * @param file chemin d'accès au fichier contenant la carte
     */
    public Board(String file) throws FileNotFoundException, IllegalArgumentException {
        File mapFile = new File(file);
        Scanner scanner = new Scanner(mapFile);
        this.theme = scanner.nextLine().charAt(0);
        this.height = scanner.nextInt();
        this.width = scanner.nextInt();
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new PlayerCharacter(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'E') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Squirrel(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'A') {
                    this.board[y][x] = new Tree(x,y);
                } else if (line.charAt(x) == 'B') {
                    this.board[y][x] = new Bush(x,y);
                } else if (line.charAt(x) == 'G') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Acorn(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'C') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Mushroom(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == ' ') {
                    this.board[y][x] = new Empty(x,y);
                } else {
                    throw new IllegalArgumentException("Le fichier est invalide.");
                }
            }
            y++;
        }
    }

    //TODO foutre les parser là où il faut

    /**
     * @param file chemin d'accès au fichier contenant la carte
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IllegalArgumentException si le fichier contient des charactères non reconnus
     */
    public BoardJUNGLE(String file) throws FileNotFoundException, IllegalArgumentException {
        File mapFile = new File(file);
        Scanner scanner = new Scanner(mapFile);
        this.theme = scanner.nextLine().charAt(0);
        this.height = scanner.nextInt();
        this.width = scanner.nextInt();
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new PlayerCharacter(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'S') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Monkey(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'P') {
                    this.board[y][x] = new PalmTree(x,y);
                } else if (line.charAt(x) == 'R') {
                    this.board[y][x] = new Rock(x,y);
                } else if (line.charAt(x) == 'B') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Banana(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == 'C') {
                    Terrain e = new Empty(x,y);
                    e.setEntityOnCase(new Mushroom(x,y));
                    this.board[y][x] = e;
                } else if (line.charAt(x) == ' ') {
                    this.board[y][x] = new Empty(x,y);
                } else {
                    throw new IllegalArgumentException("Le fichier est invalide.");
                }
            }
            y++;
        }
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
            } else {
                throw new InvalidArgumentException();
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
}
