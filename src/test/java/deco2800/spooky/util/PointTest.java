package deco2800.spooky.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class Point
 */
public class PointTest {
    private Point pt = new Point(7.7f, 7.7f);

    @Test
    /*
      Test Point.getY()
     */
    public void testGetY() {
        assertEquals(7.7f, pt.getY(), 0);
    }

    @Test
    /*
      Test Point.setY(float y)
     */
    public void testSetY() {
        pt.setY(6.7f);
        assertEquals(6.7f, pt.getY(), 0);
    }

    @Test
    /*
      Test Point.setX(float x)
     */
    public void testSetX() {
        pt.setX(0.7f);
        assertEquals(0.7f, pt.getX(),0);
    }

    @Test
    /*
      Test Point.getX()
     */
    public void testGetX() {
        assertEquals(7.7f, pt.getX(), 0);

    }

    @Test
    /*
      Test Point.equals()
     */
    public void testEquals() {
        Point newPt = new Point(7.7f, 7.70001f);
        assertTrue(pt.equals(newPt));
        newPt.setY(7.701f);
        assertFalse(pt.equals(newPt));
    }

    @Test
    /*
      Test Point.equals()
     */
    public void testEqualsNull() {
        Point newPt = null;
        assertFalse(pt.equals(newPt));
    }

    @Test
    /*
      Test Point.toString()
     */
    public void testToString() {
        assertEquals("(7.7, 7.7)", pt.toString());
    }

    @Test
    public void hashCodeTest() {
        Point p1 = new Point(1,2);
        assertEquals(12, p1.hashCode());
    }

    @Test
    public void mathsUtilTestEquality() {
        assertTrue(MathUtil.floatEquality(0, 1, 2));
    }

    @Test
    public void mathsUtilTestEqualityFalse() {
        assertFalse(MathUtil.floatEquality(0, 1, 0));
    }
}