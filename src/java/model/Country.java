package model;


import org.jetbrains.annotations.NotNull;

public class Country {

    //region data fields
    private String Name;
    private int SoldiersCount;
    private Player Owner;
    //endregion

    //region constructors
    public Country(String name, int soldiersCount, @NotNull Player owner) {
        Name = name;
        SoldiersCount = soldiersCount;
        Owner = owner;
    }
    //endregion

    //region getter setter
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSoldiersCount() {
        return SoldiersCount;
    }

    public void setSoldiersCount(int soldiersCount) {
        SoldiersCount = soldiersCount;
    }

    public Player getOwner() {
        return Owner;
    }

    public void setOwner(@NotNull Player owner) {
        Owner = owner;
    }
    //endregion

    public boolean isBordering(@NotNull Country country){
        //TODO
        return true;
    }
}
