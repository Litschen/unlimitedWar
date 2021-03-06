package ch.zhaw.unlimitedWar.model.helpers;

import ch.zhaw.unlimitedWar.model.Country;

public class MoveCountry {

    //region data fields
    private int numberOfNeighbors;
    private Country own;
    private Country neighbor;
    //endregion

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
