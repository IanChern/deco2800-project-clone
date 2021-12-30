package deco2800.spooky.util;

/**
 * A Rectangle class that represents a rectangle in a 2D space
 */
public class Rectangle {
    private Point topRight;
    private Point bottomLeft;

    /**
     * class constructor
     * @param bottomLeft the bottom left point of the rectangle
     * @param topRight the top right point of the rectangle
     */
    public Rectangle(Point bottomLeft, Point topRight) {
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
    }

    /**
     * class constructor
     * @param x1 the x value of the bottom left point of the rectangle
     * @param y1 the y value of the bottom left point of the rectangle
     * @param x2 the x value of the top right point of the rectangle
     * @param y2 the y value of the top right point of the rectangle
     */
    public Rectangle(float x1, float y1, float x2, float y2) {
        this.bottomLeft = new Point(x1, y1);
        this.topRight = new Point(x2, y2);
    }

    /**
     * Setter of the bottom left point
     * @param bottomLeft the new bottom left point
     */
    public void setBottomLeft(Point bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    /**
     * Setter of the top right point
     * @param topRight the new top right point
     */
    public void setTopRight(Point topRight) {
        this.topRight = topRight;
    }

    /**
     * getter of the bottom left point
     * @return the bottom left point of the rectangle
     */
    public Point getBottomLeft() {
        return bottomLeft;
    }

    /**
     * getter of the top right point
     * @return the top right point of the rectangle
     */
    public Point getTopRight() {
        return topRight;
    }

    /**
     * Checks if two rectangles are close enough to be equal
     * @param o the other rectangle to be compared with
     * @return true iff the bottom left points and the top rights
     * points of the two rectangles are close enough to be equal
     */
    public boolean equals(Object o) {
        if(o == null || o.getClass() != this.getClass()) {
            return(false);
        }
        Rectangle rectangle = (Rectangle) o;
        return this.topRight.equals(rectangle.topRight) && this.bottomLeft.equals(rectangle.bottomLeft);
    }

    /**
     * Generates a hash code for the rectangle of the points hash codes appended to the ends
     * @return the hash code generated
     */
    @Override
    public int hashCode() {
        int tempX = topRight.hashCode();
        int counter = 0;
        while(tempX != 0) {
            tempX /= 10;
            counter++;
        }
        tempX = (int) ((topRight.hashCode()) * Math.pow(10, counter));
        return(tempX + bottomLeft.hashCode());
    }

    /**
     * Checks if two rectangles overlap
     * @param rectangle the other rectangle to be compared with
     * @return true if two rectangles overlap
     */
    public boolean overlaps(Rectangle rectangle) {
        //Rectangle A contains rectangle B
        if(this.topRight.getX()<= rectangle.topRight.getX() &&
                this.topRight.getY() <= rectangle.topRight.getY() &&
                this.bottomLeft.getY() >= rectangle.bottomLeft.getY() &&
                this.bottomLeft.getX() >= rectangle.bottomLeft.getX()) {
            return true;
        }
        //Rectangle B contains rectangle A
        if (this.topRight.getX() >= rectangle.topRight.getX() &&
                this.topRight.getY() >= rectangle.topRight.getY() &&
                this.bottomLeft.getY() <= rectangle.bottomLeft.getY() &&
                this.bottomLeft.getX()<= rectangle.bottomLeft.getX()) {
            return true;
        }
        //Rectangle A and B do not intersect
        if (this.topRight.getY() <= rectangle.bottomLeft.getY() ||
                this.bottomLeft.getY() >= rectangle.topRight.getY()) {
            return false;
        }
        return (this.topRight.getX() > rectangle.bottomLeft.getX()) &&
                (this.bottomLeft.getX() < rectangle.topRight.getX());
    }

    /**
     * Convert the rectangle to string
     * @return the string representation of the rectangle
     */
    @Override
    public String toString() {
        String bottomLeftStr = bottomLeft.toString();
        String topRightStr = topRight.toString();
        return "{" + bottomLeftStr + " " + topRightStr + "}";
    }
}
