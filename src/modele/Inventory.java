package modele;

import modele.entity.Entity;

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
            return "...";
        } else {
            return equippedItem.getDisplayName();
        }
    }


    public void dropItem(Board board){
        int x = board.getPlayer().getPosition()[0];
        int y = board.getPlayer().getPosition()[1];
        board.fillCase(x,y,board.getPlayer().getOrientation(), ((Entity)equippedItem));
        this.equippedItem = null;
    }




    public void add(InventoryItem inventoryItem){
        items.add(inventoryItem);
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
