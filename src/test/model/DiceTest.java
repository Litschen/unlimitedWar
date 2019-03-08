package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private int[] diceResults;

    private final int AMOUNT_THROWS = 100;

    @BeforeEach
    public void setUp() {
        diceResults = new int[AMOUNT_THROWS];
    }

    // ---------- roll(int amountOfDice) ----------
    @Test
    public void testRoll() {
        diceResults = Dice.roll(1);
        assertEquals(1, diceResults.length);

        diceResults = Dice.roll(AMOUNT_THROWS);
        assertEquals(AMOUNT_THROWS, diceResults.length);

        int previousResult = Dice.MAX_VALUE;
        for (int result : diceResults) {
            assertTrue(result >= Dice.MIN_VALUE && result <= Dice.MAX_VALUE);
            assertTrue(previousResult >= result);
            previousResult = result;
        }
    }

    @Test
    public void testRollZeroDices() {
        diceResults = Dice.roll(0);
        assertEquals(0, diceResults.length);
    }

    @Test
    public void testRollNegativeAmountOfDices() {
        diceResults = Dice.roll(-5);
        assertEquals(0, diceResults.length);
    }

    // ---------- roll(int min, int max) ----------
    @Test
    public void testRollRange() {
        int min = 5;
        int max = 20;

        for (int i = 0; i < AMOUNT_THROWS; i++) {
            diceResults[i] = Dice.roll(min, max);
        }
        Arrays.sort(diceResults);

        assertEquals(min, diceResults[0]);
        assertEquals(max, diceResults[diceResults.length -1]);
    }

    @Test
    public void testRollInverseRange() {
        int min = 1;
        int max = 10;

        for (int i = 0; i < AMOUNT_THROWS; i++) {
            int result = Dice.roll(max, min);
            assertTrue(result >= min);
            assertTrue(result <= max);
        }
    }

    @Test
    public void testRollRangeZero() {
        assertEquals(0, Dice.roll(0, 0));
    }

    @Test
    public void testRollNegativeRange() {
        // TODO
        int result;
        result = Dice.roll(-1, -15);
        result = Dice.roll(1, -20);
        result = Dice.roll(-1, 20);
    }
}
