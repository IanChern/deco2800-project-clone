package deco2800.spooky.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the Rectangle class
 */
public class RectangleTest {

    private Point bottomLeft = new Point(0.1f, 0.2f);
    private Point topRight = new Point(0.23f, 0.5f);
    private Rectangle rectangle = new Rectangle(bottomLeft, topRight);

    @Test
    /*
      Test Rectangle.setBottomLeft(Point bottomLeft)
     */
    public void testSetBottomLeft() {
        Point newBottomLeft = new Point(1f,1.5f);
        rectangle.setBottomLeft(newBottomLeft);
        assertTrue(rectangle.getBottomLeft().equals(newBottomLeft));
        assertFalse(rectangle.getBottomLeft().equals(bottomLeft));
    }

    @Test
    /*
      Test Rectangle.setTopRight(Point topRight)
     */
    public void testSetTopRight() {
        Point newTopRight = new Point(1f,1f);
        rectangle.setTopRight(newTopRight);
        assertTrue(rectangle.getTopRight().equals(newTopRight));
        assertFalse(rectangle.getTopRight().equals(topRight));
    }

    @Test
    /*
      Test Rectangle.getBottomLeft()
     */
    public void testGetBottomLeft() {
        Point expectedBottomLeft = new Point(0.1f, 0.2f);
        assertTrue(rectangle.getBottomLeft().equals(expectedBottomLeft));

        Rectangle newRactangle = new Rectangle(0.1f, 0.2f, 0.23f, 0.5f);
        assertTrue(rectangle.getBottomLeft().equals(newRactangle.getBottomLeft()));
    }

    @Test
    /*
      Test Rectangle.getTopRight()
     */
    public void testGetTopRight() {
        Point expectedTopRight = new Point(0.23f, 0.5f);
        assertTrue(rectangle.getTopRight().equals(expectedTopRight));

        Rectangle newRactangle = new Rectangle(0.1f, 0.2f, 0.23f, 0.5f);
        assertTrue(rectangle.getTopRight().equals(newRactangle.getTopRight()));
    }

    @Test
    /*
      Test Rectangle.equals(Rectangle rectangle)
     */
    public void testEquals() {
        Point newBottomLeft = new Point(0.1001f, 0.2f);
        Point newTopRight = new Point(0.23f, 0.5f);
        Rectangle newRectagnle = new Rectangle(newBottomLeft, newTopRight);
        assertTrue(newRectagnle.equals(rectangle));

        newBottomLeft = new Point(0.101f, 0.2f);
        newRectagnle = new Rectangle(newBottomLeft, newTopRight);
        assertFalse(newRectagnle.equals(rectangle));
    }

    @Test
    /*
      Test Rectangle.equals(Rectangle rectangle)
     */
    public void testEqualsNull() {
        Rectangle newRectagnle = null;
        assertFalse(rectangle.equals(newRectagnle));
    }

    @Test
    /*
      Test Rectangle.overlaps(Rectangle rectangle)
     */
    public void testOverlaps() {
        Rectangle newRectangle1 = new Rectangle(0.1f, 0.2f, 0.4f, 0.3f);
        assertTrue(rectangle.overlaps(newRectangle1));

        Rectangle newRectangle2 = new Rectangle(0.2f, 0.3f, 1.2f, 3.4f);
        assertFalse(newRectangle1.overlaps(newRectangle2));

        Rectangle newRectangle3 = new Rectangle(-1f,-2f,0f, 0.3f);
        assertFalse(newRectangle3.overlaps(newRectangle2));
        assertFalse(newRectangle2.overlaps(newRectangle3));
        assertFalse(newRectangle1.overlaps(newRectangle3));
        assertFalse(newRectangle3.overlaps(newRectangle1));

        Rectangle rectangle3 = new Rectangle(0.21f, 0.32f, 1.18f, 3.3f);
        assertTrue(rectangle3.overlaps(newRectangle2));
        assertTrue(newRectangle2.overlaps(rectangle3));
    }

    @Test
    /*
      Test Rectangle.toString()
     */
    public void toStringTest() {
        assertEquals(rectangle.toString(), "{(0.1, 0.2) (0.23, 0.5)}");
    }

    @Test
    public void hashCodeTest() {
        Point bl = new Point(0.0f, 0.0f);
        Point tr = new Point(1.0f, 1.0f);
        Rectangle r1 = new Rectangle(bl,tr);
        assertEquals(1100, r1.hashCode());
    }
}