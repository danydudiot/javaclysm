package modele;

import modele.entity.Entity;
import modele.entity.movable.character.npc.prey.Prey;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<InventoryItem> items;

    private static Inventory INSTANCE;

    private final int MAX_INVENTORY_SIZE = 9;
    private int equippedItemId;
    private Inventory() {
        this.items = new ArrayList<>();
        this.equippedItemId = -1;
    }

    public static Inventory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Inventory();
        }
        return INSTANCE;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }

    public boolean hasItem(InventoryItem item) {
        for (InventoryItem i : items) {
            if (item.equals(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        return (items.size() >= MAX_INVENTORY_SIZE);
    }

    public boolean isEmpty() {
        return (items.isEmpty());
    }

    public void setEquippedItem(InventoryItem equippedItem) {
        this.equippedItemId = items.indexOf(equippedItem);
    }

    public void setEquippedItem(int equippedItemId) {
        if (equippedItemId < items.size()) {
            this.equippedItemId = equippedItemId;
        } else {
            this.equippedItemId = -1;
        }
    }

    public int getEquippedItemId() {
        return equippedItemId;
    }

    public InventoryItem getEquippedItem() {
        if (equippedItemId == -1){
            return null;
        } else {
            return items.get(equippedItemId);
        }
    }

    public String getEquippedItemString(){
        if (equippedItemId == -1){
            return "...";
        } else {
            return items.get(equippedItemId).getDisplayName();
        }
    }

    public void dropItem(){
        if (equippedItemId != -1 && !(getEquippedItem() instanceof Prey)) {
            Board board = Board.getInstance();
            int x = board.getPlayer().getX();
            int y = board.getPlayer().getY();
            Terrain target = board.getToward(x,y,board.getPlayer().getOrientation());
            if (target != null && (target instanceof Empty) && (target.getEntityOnCase() == null)) {
                board.fillCase(x,y,board.getPlayer().getOrientation(), ((Entity) getEquippedItem()));
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
                Board.getInstance().logError("Espece de monstre sans coeur.");
                Board.getInstance().logError("Vous n'allez quand même pas faire ça ?");
            }
        }
    }

    public void dropSpecificItem(Entity what) {
        int id = items.indexOf(what);
        Board board = Board.getInstance();
        int x = board.getPlayer().getX();
        int y = board.getPlayer().getY();
        board.fillCase(x,y,board.getPlayer().getOrientation(), (Entity) items.get(id));
        items.remove(id);
        if (equippedItemId == id) {
            equippedItemId = -1;
        }
    }

    public List<String> getItemsStrings() {
        List<String> out = new ArrayList<>();
		for (InventoryItem item : items) {
			out.add(item.getDisplayName());
		}
        return out;
    }

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

    public void remove(InventoryItem inventoryItem) {
        if ((equippedItemId != -1) && (items.get(equippedItemId) == inventoryItem)) {
            equippedItemId = -1;
        }
        items.remove(inventoryItem);
    }

    public int getInventorySize() {
        return MAX_INVENTORY_SIZE;
    }

    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder();
        if (items.isEmpty()){
            return "Inventaire vide";
        }
        for (InventoryItem item : items) {
            itemsString.append(item.toString()).append('\n');
        }
        return itemsString.toString();
    }
}
