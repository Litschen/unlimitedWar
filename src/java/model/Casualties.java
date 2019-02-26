package model;

public class Casualties {
    //region data fields
    private int casualtiesAttacker;
    private int casualtiesDefender;
    //endregion

    /**
     * @param casualtiesAttacker
     * @param casualtiesDefender
     */
    //region constructors
    public Casualties(int casualtiesAttacker, int casualtiesDefender) {
        this.casualtiesAttacker = casualtiesAttacker;
        this.casualtiesDefender = casualtiesDefender;
    }
    //endregion

    /**
     * @return by Casualties Attacker
     */
    //region getter setter
    public int getCasualtiesAttacker() {
        return casualtiesAttacker;
    }

    /**
     * @return by Casualties Defender
     */
    public int getCasualtiesDefender() {
        return casualtiesDefender;
    }

    public void addCasualtiesAttacker() {
        this.casualtiesAttacker += 1;
    }

    public void addCasualtiesDefender() {
        this.casualtiesDefender += 1;
    }
    //endregion

}
