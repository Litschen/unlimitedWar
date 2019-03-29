package model.helpers;

/**
 * Class to save casualties for attacker and defender during an invasion of a country
 */
public class Casualties {
    //region data fields
    private static int casualtiesAttacker;
    private static int casualtiesDefender;
    //endregion

    /**
     * Save casualties for attacker and defender during an invasion of a country in this object
     */
    public Casualties(int casualtiesAttacker, int casualtiesDefender) {
        Casualties.casualtiesAttacker = casualtiesAttacker;
        Casualties.casualtiesDefender = casualtiesDefender;
    }

    //region getter setter
    public int getCasualtiesAttacker() {
        return casualtiesAttacker;
    }

    public int getCasualtiesDefender() {
        return casualtiesDefender;
    }
    //endregion

    /**
     * increase casualties of the attacker by one
     */
    public void addCasualtiesAttacker() {
        casualtiesAttacker += 1;
    }

    /**
     * Increase the casualties of the defender by one
     */
    public void addCasualtiesDefender() {
        casualtiesDefender += 1;
    }


}