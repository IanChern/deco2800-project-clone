package deco2800.spooky.entities;

import junit.framework.TestCase;
import org.junit.Test;

public class PillTest extends TestCase {
    @Test
    public void testConstructor() {
        Pill pill = new Pill(0f,0f);
        assertEquals("medicine", pill.getTexture());
        assertEquals(0f, pill.getCol());
        assertEquals(0f, pill.getRow());
        assertEquals(0f, pill.getSpeed());
        //assertEquals(PeonRole.PILL, pill.getPeonRole());
    }
}
