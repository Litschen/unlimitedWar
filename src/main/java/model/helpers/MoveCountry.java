package model.helpers;

import model.Country;

public class MoveCountry {

    private int numberOfNeighbors;
    private Country own;
    private Country neighbor;


    public MoveCountry(int numberOfNeighbors, Country neighbor, Country own) {
        this.numberOfNeighbors = numberOfNeighbors;
        this.own = own;
        this.neighbor = neighbor;
    }

    public int getNumberOfNeighbors() {
        return numberOfNeighbors;
    }

    public Country getOwn() {
        return own;
    }

    public Country getNeighbor() {
        return neighbor;
    }


    public void setNeighbor(Country neighbor) {
        this.neighbor = neighbor;
    }
}
