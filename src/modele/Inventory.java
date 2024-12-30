package modele;

import modele.entity.Entity;
import modele.entity.stationary.terrain.Empty;
import modele.entity.stationary.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<InventoryItem> items;

    private final int MAX_INVENTORY_SIZE = 9;
    private int equippedItemId;
    public Inventory() {
        this.items = new ArrayList<>();
        this.equippedItemId = -1;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
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
        if (equippedItemId != -1) {
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
            Board.getInstance().logError("Aucun objet équipé.");
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
