package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private List<Integer> diceResults;
    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice();
    }

    @Test
    void rollDefault() {
        diceResults = dice.roll(4);
        assertEquals(diceResults.size(), Dice.MAX_THROWS - 1);
        int previousResult = Dice.MAX_VALUE;
        for(int result : diceResults){
            assertTrue(result >= Dice.MIN_VALUE && result <= Dice.MAX_VALUE);
            assertTrue(previousResult >= result);
            previousResult = result;
        }
    }

    @Test
    void rollNegative(){
        diceResults = dice.roll(-3);
        assertEquals(diceResults.size(), 0);
    }

    @Test
    void rollMax(){
        diceResults = dice.roll(Integer.MAX_VALUE);
        assertEquals(diceResults.size(),0);
    }

    @Test
    public void testGetHighestRoll(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(6);
        rolls.add(1);
        rolls.add(1);
        rolls.add(5);
        rolls.add(4);
        rolls.add(3);
        assertEquals(6, dice.getHighestRoll(rolls));
    }

    @Test
    public void testGetHighestRoll_OneElement(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(1);
        assertEquals(1, dice.getHighestRoll(rolls));
    }

    @Test
    public void testGetHighestRoll_SameValues(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(3);
        rolls.add(3);
        rolls.add(3);
        assertEquals(3, dice.getHighestRoll(rolls));
    }

    @Test
    public void testGetHighestRoll_NegativeValues(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(2);
        rolls.add(-3);
        rolls.add(5);
        assertEquals(5, dice.getHighestRoll(rolls));
    }
}
