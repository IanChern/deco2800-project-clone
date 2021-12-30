package deco2800.spooky.tasks;

import org.junit.Test;

import static org.junit.Assert.*;

public class ItemGenerationTest {

    @Test
    public void start() {
        ItemGeneration itemGeneration = new ItemGeneration();
        assertFalse(itemGeneration.getFlag());
        itemGeneration.start();
        assertTrue(itemGeneration.getFlag());
    }

    @Test
    public void terminate() {
        ItemGeneration itemGeneration = new ItemGeneration();
        itemGeneration.start();
        assertTrue(itemGeneration.getFlag());
        itemGeneration.terminate();
        assertFalse(itemGeneration.getFlag());
    }

    @Test
    public void randInt() {
        ItemGeneration itemGeneration = new ItemGeneration();
        for (int i = 1; i < 100; i++) {
            int randomInt = itemGeneration.randInt(-i, i);
            assertTrue(randomInt <  i);
            assertTrue(randomInt >= -1 * i);
        }
    }
}