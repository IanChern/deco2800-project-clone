package deco2800.spooky.Inventory;

import deco2800.spooky.util.HexVector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventoryBoxTest {
    private InventoryBox inventoryBox;
    @Before
    public void setUp() {

        inventoryBox = new InventoryBox(1, 1);
    }

    @Test
    public void getPositionTest() {
        assertEquals(new HexVector(1,1), inventoryBox.getPosition());
    }

}
