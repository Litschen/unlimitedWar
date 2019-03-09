package model;

/**
 * Class to save casualties for attacker and defender during an invasion of a country
 */
class Casualties {
    //region data fields
    private int casualtiesAttacker;
    private int casualtiesDefender;
    //endregion

    /**
     * Save casualties for attacker and defender during an invasion of a country in this object
     *
     * @param casualtiesAttacker casualties inflicted upon the attacker
     * @param casualtiesDefender casualties inflicted upon the defender
     */
    Casualties(int casualtiesAttacker, int casualtiesDefender) {
        this.casualtiesAttacker = casualtiesAttacker;
        this.casualtiesDefender = casualtiesDefender;
    }

    //region getter setter
    int getCasualtiesAttacker() {
        return casualtiesAttacker;
    }

    int getCasualtiesDefender() {
        return casualtiesDefender;
    }
    //endregion

    /**
     * increase casualties of the attacker by one
     */
    void addCasualtiesAttacker() {
        casualtiesAttacker += 1;
    }

    /**
     * Increase the casualties of the defender by one
     */
    void addCasualtiesDefender() {
        casualtiesDefender += 1;
    }


}
