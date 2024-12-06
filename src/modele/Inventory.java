package modele;

import modele.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<InventoryItem> items;

    private final int MAX_INVENTORY_SIZE = 9;
//    private InventoryItem equippedItem;
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

    public String getEquippedItemString(){
        if (equippedItemId == -1){
            return "...";
        } else {
            return items.get(equippedItemId).getDisplayName();
        }
    }


    public void dropItem(Board board){
        int x = board.getPlayer().getPosition()[0];
        int y = board.getPlayer().getPosition()[1];
        board.fillCase(x,y,board.getPlayer().getOrientation(), ((Entity)equippedItem));
        this.equippedItem = null;
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
