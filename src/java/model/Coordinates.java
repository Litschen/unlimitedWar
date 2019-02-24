package model;

public class Coordinates {
    //region data fields
    private int top;
    private int bottom;
    private int left;
    private int right;
    //endregion

    //region Constructor
    public Coordinates(int top, int right, int bottom, int left) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
    //endregion

    //region getter setter
    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
    //endregion

}
