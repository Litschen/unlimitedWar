package model;

public class Coordinates {
    //region data fields
    private int top;
    private int bottom;
    private int left;
    private int right;
    //endregion

    /** Set Coordinates
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
    //region Constructor
    public Coordinates(int top, int right, int bottom, int left) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
    //endregion

    /**
     * @return by top
     */
    //region getter setter
    public int getTop() {
        return top;
    }

    /**
     * @return by bottom
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * @return by left
     */
    public int getLeft() {
        return left;
    }

    /**
     * @return by Right
     */
    public int getRight() {
        return right;
    }
    //endregion

}
