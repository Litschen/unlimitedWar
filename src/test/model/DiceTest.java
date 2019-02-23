package model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private List<Integer> diceResults;

    @Test
    void rollDefault() {
        diceResults = new Dice().roll(4);
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
        diceResults = new Dice().roll(-3);
        assertEquals(diceResults.size(), 0);
    }

    @Test
    void rollMax(){
        diceResults = new Dice().roll(Integer.MAX_VALUE);
        assertEquals(diceResults.size(),0);
    }
}
