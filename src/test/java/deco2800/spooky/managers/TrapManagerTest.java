package deco2800.spooky.managers;

import deco2800.spooky.entities.RoomTrap;
import deco2800.spooky.entities.TrapType;
import deco2800.spooky.managers.TrapManager;
import deco2800.spooky.worlds.TestWorld;
import deco2800.spooky.worlds.Tile;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TrapManagerTest {
    private TestWorld testWorld;
    private GameManager gameManager = GameManager.get();
    private TrapManager tm;

    @Before
    public void setUp() {
        this.tm = new TrapManager();
        this.testWorld = new TestWorld();
    }

    @Test
    public void isTrapTest() {
        gameManager.setWorld(testWorld);

        Tile t = GameManager.get().getWorld().getTile(0, 1);
        RoomTrap trap = new RoomTrap(t.getCol(), t.getRow(), t, TrapType.PITFALL);
        testWorld.addEntity(tm.setTrap(t, trap.trapTypeIs()));

        assertTrue(tm.isTrap(t));
    }
    @Test
    public void trapOpenedTest() {
        gameManager.setWorld(testWorld);

        Tile t = GameManager.get().getWorld().getTile(0, 1);
        RoomTrap trap = new RoomTrap(t.getCol(), t.getRow(), t, TrapType.PITFALL);
        testWorld.addEntity(tm.setTrap(t, trap.trapTypeIs()));
        assertTrue(tm.isTrap(t));

        assertEquals("closedPit", trap.getTexture());
        tm.trapOpened(trap);
        assertEquals("pitfall", trap.getTexture());
    }

    @Test
    public void tileToTrapTest() {
        gameManager.setWorld(testWorld);
        Tile t = GameManager.get().getWorld().getTile(0, 1);
        assertEquals(null, tm.tileToTrap(t));

        RoomTrap trap = new RoomTrap(t.getCol(), t.getRow(), t, TrapType.PITFALL);
        testWorld.addEntity(tm.setTrap(t, trap.trapTypeIs()));

        assertEquals(trap.getTile(), tm.tileToTrap(t).getTile());
        assertFalse(tm.tileToTrap(t).isStep());
        assertEquals(trap.trapTypeIs(), tm.tileToTrap(t).trapTypeIs());
    }
}
