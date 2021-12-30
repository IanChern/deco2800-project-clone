package deco2800.spooky.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Used to test the 3D boxing class
 */
public class Box3DTest {

    private Box3D boxDiff = new Box3D(0f, 1f, 2f, 3f, 4f, 5f);
    private Box3D box = new Box3D(0f, 0f, 0f, 1f, 1f, 1f);

    @Test
    public void overlapsX() {
        Box3D box2 = new Box3D(2f, 2f, 2f, 1f, 1f, 1f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsXLarger() {
        Box3D box2 = new Box3D(-1f, 2f, 2f, -2f, 1f, 1f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsY() {
        Box3D box2 = new Box3D(0f, 2f, 2f, 0.1f, 0.9f, 1f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsYLarger() {
        Box3D box2 = new Box3D(0f, -1f, 2f, 0.1f, -2f, 1f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsZ() {
        Box3D box2 = new Box3D(0f, 0f, 2f, 0.1f, 0.1f, 0.9f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsZLarger() {
        Box3D box2 = new Box3D(0f, 0f, -1f, 0.1f, 0.1f, 0.9f);
        assertFalse(box.overlaps(box2));
    }

    @Test
    public void overlapsNone() {
        Box3D box2 = new Box3D(0f, 0f, 0f, 0.1f, 0.1f, 0.1f);
        assertTrue(box.overlaps(box2));
    }

    @Test
    public void distance() {
    }

    @Test
    public void getX() {
        assertEquals(0f, boxDiff.getX(), 0.0f);
    }

    @Test
    public void setX() {
        boxDiff.setX(6f);
        assertEquals(6f, boxDiff.getX(), 0.0f);
    }

    @Test
    public void getY() {
        assertEquals(1f, boxDiff.getY(), 0.0f);
    }

    @Test
    public void setY() {
        boxDiff.setY(7f);
        assertEquals(7f, boxDiff.getY(), 0.0f);
    }

    @Test
    public void getZ() {
        assertEquals(2f, boxDiff.getZ(), 0.0f);
    }

    @Test
    public void setZ() {
        boxDiff.setZ(8f);
        assertEquals(8f, boxDiff.getZ(), 0.0f);
    }

    @Test
    public void getXLength() {
        assertEquals(3f, boxDiff.getXLength(), 0.0f);
    }

    @Test
    public void getYLength() {
        assertEquals(4f, boxDiff.getYLength(), 0.0f);
    }

    @Test
    public void getZLength() {
        assertEquals(5f, boxDiff.getZLength(), 0.0f);
    }
    
    @Test
    public void testDistance() {
        Box3D setBox = new Box3D(0f, 0f, 0f, 1f, 1f, 1f);
        assertEquals(0f, box.distance(setBox), 0.0f);
    }

    @Test
    public void cubeStringTest() {
        Cube cube = Cube.oddqToCube(1f, 1f);
        assertEquals("[1.0 1.0 -2.0]", cube.toString());
    }
    @Test
    public void makeCopy() {
        Box3D box2 = new Box3D(box);
        assertNotEquals(box, box2);
        assertEquals(box.getX(), box2.getX(), 0.0f);
        assertEquals(box.getXLength(), box2.getXLength(), 0.0f);
        assertEquals(box.getY(), box2.getY(), 0.0f);
        assertEquals(box.getYLength(), box2.getYLength(), 0.0f);
        assertEquals(box.getZ(), box2.getZ(), 0.0f);
        assertEquals(box.getZLength(), box2.getZLength(), 0.0f);
    }
}