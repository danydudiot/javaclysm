package modele;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<InventoryItem> items;
    private InventoryItem equippedItem;

    public Inventory() {
        this.items = new ArrayList<>();
        this.equippedItem = null;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }

    public void setEquippedItem(InventoryItem equippedItem) {
        this.equippedItem = equippedItem;
    }

    public String getEquippedItem(){
        if (equippedItem == null){
            return "Rien";
        } else {
            return equippedItem.getDisplayName();
        }
    }

    @Override
    public String toString() {
        StringBuilder itemsString = new StringBuilder();
        if (items.isEmpty()){
            return "Inventaire vide";
        }
        for (InventoryItem item : items) {
            itemsString.append(item.toString());
        }
        return itemsString.toString();
    }
}
