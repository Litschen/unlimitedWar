package java.model;

import com.sun.istack.internal.NotNull;
//import org.jetbrains.annotations.NotNull;

public class Country {

    //region static variables
    public static final int MIN_SOLDIERS_TO_INVADE = 2;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_ATTACKER = 3;
    public static final int ABSOLUTE_MAX_AMOUNT_THROWS_DEFENDER = 2;
    public static final int METHOD_NOT_IMPELEMENTED_RETURN_VALUE = -1;
    //endregion

    //region data fields
    private String name;
    private int soldiersCount;
    private Player owner;
    //endregion

    //region constructors
    public Country(String name, int soldiersCount, @NotNull Player owner) {
        this.name = name;
        this.soldiersCount = soldiersCount;
        this.owner = owner;
    }
    //endregion

    //region getter setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSoldiersCount() {
        return soldiersCount;
    }

    public void setSoldiersCount(int soldiersCount) {
        this.soldiersCount = soldiersCount;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(@NotNull Player owner) {
        this.owner = owner;
    }
    //endregion

    public boolean isBordering(@NotNull Country country){
        //TODO
        return false;
    }
    public void invade(Country country){
        if(this.canInvade()){

        }
    }

    public int maxAmountDiceThrowsAttacker(){
        return METHOD_NOT_IMPELEMENTED_RETURN_VALUE;
    }

    public int amountDiceThrowsDefender(int amountAttacker){
        return METHOD_NOT_IMPELEMENTED_RETURN_VALUE;
    }

    public void addSoldier(){
        soldiersCount++;
    }

    public Casualties calculateCasualties(int[] diceThrowsAttacker, int[] diceThrowsDefender){
        return new Casualties(METHOD_NOT_IMPELEMENTED_RETURN_VALUE, METHOD_NOT_IMPELEMENTED_RETURN_VALUE);
    }

    private boolean canInvade(){
        return this.soldiersCount >= MIN_SOLDIERS_TO_INVADE;
    }
}
