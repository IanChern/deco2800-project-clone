package deco2800.spooky.util;

/**
 * A Point class that represents a point in 2D space.
 */
public class Point {
    private float x;
    private float y;

    /**
     * class constructor
     * @param x x value of the point
     * @param y y value of the point
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter of y
     * @return the y value of the point
     */
    public float getY() {
        return y;
    }

    /**
     * Setter of y
     * @param y the new y value
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Setter of x
     * @param x the new x value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Getter of x
     * @return the x value of the point
     */
    public float getX() {
        return x;
    }

    /**
     *
     * @param o the point to be compared with
     * @return true iff two points are close enough to be the same point
     */
    @Override
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) {
            return(false);
        }
        Point pt = (Point) o;
        return MathUtil.floatEquality(this.x, pt.getX()) && MathUtil.floatEquality(this.y, pt.getY());
    }

    /**
     * Generates a hash code for the point of x with y appended to the end
     * @return the hash code generated
     */
    @Override
    public int hashCode() {
        int tempX = (int) x;
        int counter = 0;
        while(tempX != 0) {
            tempX /= 10;
            counter++;
        }
        tempX = (int) (((int) x) * Math.pow(10, counter));
        return (int) (tempX + y);
    }

    /**
     * Convert the point to string
     * @return the string representation of the point
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
