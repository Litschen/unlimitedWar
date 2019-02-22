package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private int[] diceResults;

    @Test
    void rollDefault() {
        diceResults = new Dice().roll(4);
        assertEquals(diceResults.length, Dice.MAX_THROWS - 1);
        int previousResult = Dice.MAX_VALUE;
        for(int result : diceResults){
            assertTrue(result >= Dice.MIN_VALUE && result <= Dice.MAX_VALUE);
            assertTrue(previousResult >= result);
            previousResult = result;
        }
    }

    @Test
    void rollNegative(){
        diceResults = new Dice().roll(-3);
        assertEquals(diceResults.length, 0);
    }

    @Test
    void rollMax(){
        diceResults = new Dice().roll(Integer.MAX_VALUE);
        assertEquals(diceResults.length,0);
    }
}
