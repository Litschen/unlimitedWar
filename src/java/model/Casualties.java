package model;

public class Casualties {
    //region data fields
    private int casualtiesAttacker;
    private int casualtiesDefender;
    //endregion

    //region constructors
    public Casualties(int casualtiesAttacker, int casualtiesDefender) {
        this.casualtiesAttacker = casualtiesAttacker;
        this.casualtiesDefender = casualtiesDefender;
    }
    //endregion

    //region getter setter
    public int getCasualtiesAttacker() {
        return casualtiesAttacker;
    }

    public int getCasualtiesDefender() {
        return casualtiesDefender;
    }
    //endregion

}
