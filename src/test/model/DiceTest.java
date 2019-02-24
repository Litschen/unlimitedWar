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
    void testRoll() {
        diceResults = dice.roll(4);
        assertEquals(Dice.MAX_THROWS - 1, diceResults.size());
        int previousResult = Dice.MAX_VALUE;
        for(int result : diceResults){
            assertTrue(result >= Dice.MIN_VALUE && result <= Dice.MAX_VALUE);
            assertTrue(previousResult >= result);
            previousResult = result;
        }
    }

    @Test
    void testRollNegative(){
        diceResults = dice.roll(-3);
        assertEquals(0, diceResults.size());
    }

    @Test
    void testRollMax(){
        diceResults = dice.roll(Integer.MAX_VALUE);
        assertEquals(0, diceResults.size());
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
    public void testGetHighestRollOneElement(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(1);
        assertEquals(1, dice.getHighestRoll(rolls));
    }

    @Test
    public void testGetHighestRollSameValues(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(3);
        rolls.add(3);
        rolls.add(3);
        assertEquals(3, dice.getHighestRoll(rolls));
    }

    @Test
    public void testGetHighestRollNegativeValues(){
        List<Integer> rolls = new ArrayList<Integer>();
        rolls.add(2);
        rolls.add(-3);
        rolls.add(5);
        assertEquals(5, dice.getHighestRoll(rolls));
    }
}
