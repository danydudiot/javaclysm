package modele;

import modele.entity.Entity;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'inventaire d'un joueur.
 */
public class Inventory {
    /**
     * Liste des objets d'inventaire.
     */
    private List<InventoryItem> items;
    /**
     * Instance unique de la classe Inventory (singleton).
     */
    private static Inventory INSTANCE;
    /**
     * Taille maximale de l'inventaire.
     */
    private final int MAX_INVENTORY_SIZE = 9;
    /**
     * Identifiant de l'objet équipé.
     */
    private int equippedItemId;

    /**
     * Constructeur privé pour initialiser l'inventaire.
     */
    private Inventory() {
        this.items = new ArrayList<>();
        this.equippedItemId = -1;
    }

    /**
     * Obtient l'instance unique de la classe Inventory.
     *
     * @return L'instance unique de la classe Inventory.
     */
    public static Inventory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Inventory();
        }
        return INSTANCE;
    }

    /**
     * Vérifie si l'inventaire est plein.
     *
     * @return true si l'inventaire est plein, false sinon.
     */
    public boolean isFull() {
        return (items.size() >= MAX_INVENTORY_SIZE);
    }

    /**
     * Vérifie si l'inventaire est vide.
     *
     * @return true si l'inventaire est vide, false sinon.
     */
    public boolean isEmpty() {
        return (items.isEmpty());
    }

    /**
     * Définit l'objet équipé.
     *
     * @param equippedItem L'objet à équiper.
     */
    public void setEquippedItem(InventoryItem equippedItem) {
        this.equippedItemId = items.indexOf(equippedItem);
    }

    /**
     * Définit l'objet équipé par son identifiant.
     *
     * @param equippedItemId L'identifiant de l'objet à équiper.
     */
    public void setEquippedItem(int equippedItemId) {
        if (equippedItemId < items.size()) {
            this.equippedItemId = equippedItemId;
        } else {
            this.equippedItemId = -1;
        }
    }

    /**
     * Obtient l'identifiant de l'objet équipé.
     *
     * @return L'identifiant de l'objet équipé.
     */
    public int getEquippedItemId() {
        return equippedItemId;
    }

    /**
     * Obtient l'objet équipé.
     *
     * @return L'objet équipé, ou null si aucun objet n'est équipé.
     */
    public InventoryItem getEquippedItem() {
        if (equippedItemId == -1) {
            return null;
        } else {
            return items.get(equippedItemId);
        }
    }

    /**
     * Obtient le nom affiché de l'objet équipé.
     *
     * @return Le nom affiché de l'objet équipé, ou "..." si aucun objet n'est équipé.
     */
    public String getEquippedItemString() {
        if (equippedItemId == -1) {
            return "...";
        } else {
            return getEquippedItem().getDisplayName();
        }
    }

    /**
     * Jette l'objet équipé sur le plateau de jeu.
     */
    public void dropItem() {
        if (equippedItemId != -1 && !(getEquippedItem() instanceof Prey)) {
            Board board = Board.getInstance();
            int x = board.getPlayer().getX();
            int y = board.getPlayer().getY();
            Terrain target = board.getToward(x, y, board.getPlayer().getOrientation());
            if (target != null && (target instanceof Empty) && target.isEmpty()) {
                board.fillCase(x, y, ((Entity) getEquippedItem()));
                board.logAction(getEquippedItemString() + " jeté.");
                items.remove(equippedItemId);
                this.equippedItemId = -1;
            } else {
                board.logError("Impossible de jeter cela ici.");
            }
        } else {
            if (equippedItemId == -1) {
                Board.getInstance().logError("Aucun objet équipé.");
            } else {
                Board.getInstance().logError("Espèce de monstre sans coeur.");
                Board.getInstance().logError("Vous n'allez quand même pas faire ça ?");
            }
        }
    }

    /**
     * Obtient les noms affichés des objets de l'inventaire.
     *
     * @return Une liste des noms affichés des objets de l'inventaire.
     */
    public List<String> getItemsStrings() {
        List<String> out = new ArrayList<>();
        for (InventoryItem item : items) {
            out.add(item.getDisplayName());
        }
        return out;
    }

    /**
     * Ajoute un objet à l'inventaire.
     *
     * @param inventoryItem L'objet à ajouter.
     * @throws Exception Si l'inventaire est plein.
     */
    public void add(InventoryItem inventoryItem) throws Exception {
        if (items.size() < MAX_INVENTORY_SIZE) {
            items.add(inventoryItem);
            if (equippedItemId == -1) {
                setEquippedItem(inventoryItem);
            }
        } else {
            throw new Exception("l'Inventaire est plein");
        }
    }

    /**
     * Retire un objet de l'inventaire.
     *
     * @param inventoryItem L'objet à retirer.
     */
    public void remove(InventoryItem inventoryItem) {
        if ((equippedItemId != -1) && (getEquippedItem() == inventoryItem)) {
            equippedItemId = -1;
        }
        items.remove(inventoryItem);
    }

    /**
     * Obtient la taille maximale de l'inventaire.
     *
     * @return La taille maximale de l'inventaire.
     */
    public int getInventorySize() {
        return MAX_INVENTORY_SIZE;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'inventaire.
     *
     * @return Une représentation en chaîne de caractères de l'inventaire.
     */
    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder();
        if (items.isEmpty()) {
            return "Inventaire vide";
        }
        for (InventoryItem item : items) {
            itemsString.append(item.toString()).append('\n');
        }
        return itemsString.toString();
    }
}
