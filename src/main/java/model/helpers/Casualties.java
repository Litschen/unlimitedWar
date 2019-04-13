package model.helpers;

/**
 * Class to save casualties for attacker and defender during an invasion of a country
 */
public class Casualties {
    //region data fields
    private int casualtiesAttacker;
    private int casualtiesDefender;
    //endregion

    public Casualties(int casualtiesAttacker, int casualtiesDefender) {
        this.casualtiesAttacker = casualtiesAttacker;
        this.casualtiesDefender = casualtiesDefender;
    }

    //region getter setter
    public int getCasualtiesAttacker() {
        return casualtiesAttacker;
    }

    public int getCasualtiesDefender() {
        return casualtiesDefender;
    }
    //endregion

    public void addCasualtiesAttacker() {
        casualtiesAttacker += 1;
    }

    public void addCasualtiesDefender() {
        casualtiesDefender += 1;
    }

}
