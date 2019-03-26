package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    private List<Integer> diceResults;

    private final int AMOUNT_THROWS = 100;

    @BeforeEach
    public void setUp() {
        diceResults = new ArrayList<>();
    }

    // ---------- roll(int amountOfDice) ----------
    @Test
    public void testRoll() {

        diceResults = Dice.roll(1);
        assertEquals(1, diceResults.size());

        diceResults = Dice.roll(AMOUNT_THROWS);
        assertEquals(AMOUNT_THROWS, diceResults.size());

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
        assertEquals(0, diceResults.size());
    }

    @Test
    public void testRollNegativeAmountOfDices() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Dice.roll(-5));
        assertEquals("amount of dice has to be > 0", exception.getMessage());
    }

    // ---------- roll(int min, int max) ----------
    @Test
    public void testRollRange() {
        int min = 5;
        int max = 20;

        for (int i = 0; i < AMOUNT_THROWS; i++) {
            diceResults.add(Dice.roll(min, max));
        }
        Collections.sort(diceResults);

        assertEquals(min, diceResults.get(0));
        assertEquals(max, diceResults.get(diceResults.size() - 1));
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
        int result;
        result = Dice.roll(-1, -15);
        assertTrue(result <= -1 && result >= -15);

        result = Dice.roll(0, -20);
        assertTrue(result <= 0 && result >= -20);

        result = Dice.roll(-1, 20);
        assertTrue(result >= -1 && result <= 20);
    }
}
