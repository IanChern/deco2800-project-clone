package deco2800.spooky.Inventory;

import deco2800.spooky.entities.Peon;
import deco2800.spooky.util.HexVector;

public class InventoryBox extends Peon {
    private HexVector position;

    /**
     *
     * @param col the column of the bounding box
     * @param row the row of the bounding box
     */

    public InventoryBox(float col,float row) {
        this.position = new HexVector(col, row);
    }

    /**
     * return the position of the inventory box
     * @return the position of the inventory box
     */
    public HexVector getPosition() {
        return this.position;
    }


}

