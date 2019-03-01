import model.Dice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private int[] diceResults;
    private Dice dice;

    @BeforeEach
    void setUp() {
        dice = new Dice();
    }

    @Test
    void testRoll() {
        diceResults = dice.roll(4);
        assertEquals(Dice.MAX_THROWS - 1, diceResults.length);
        int previousResult = Dice.MAX_VALUE;
        for (int result : diceResults) {
            assertTrue(result >= Dice.MIN_VALUE && result <= Dice.MAX_VALUE);
            assertTrue(previousResult >= result);
            previousResult = result;
        }
    }

    @Test
    void testRollNegative() {
        diceResults = dice.roll(-3);
        assertEquals(0, diceResults.length);
    }

    @Test
    void testRollMax() {
        diceResults = dice.roll(Integer.MAX_VALUE);
        assertEquals(0, diceResults.length);
    }

}
