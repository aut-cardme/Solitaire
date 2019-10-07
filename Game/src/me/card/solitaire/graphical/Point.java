package me.card.solitaire.graphical;

/**
 * The type Point.
 */
public class Point {

    /**
     * The X point.
     */
    int x;
    /**
     * The Y point.
     */
    int y;

    /**
     * Instantiates a new Point.
     *
     * @param x the x point
     * @param y the y point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Instantiates a new Point.
     *
     * @param x the x point
     * @param y the y point
     */
    public Point(double x, double y) {
        this((int) x, (int) y);
    }

    /**
     * Gets x point
     *
     * @return the x point
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y point
     *
     * @return the y point
     */
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" + "row=" + x + ", column=" + y + '}';
    }
}
