package ch.zhaw.unlimitedWar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    private List<Integer> diceResults;

    private final int AMOUNT_THROWS = 100;

    @BeforeEach
    void setUp() {
        diceResults = new ArrayList<>();
    }

    @Test
    void testRoll() {

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
    void testRollZeroDices() {
        diceResults = Dice.roll(0);
        assertEquals(0, diceResults.size());
    }

    @Test
    void testRollNegativeAmountOfDices() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Dice.roll(-5));
        assertEquals("amount of dice has to be > 0", exception.getMessage());
    }

    // ---------- roll(int min, int max) ----------
    @Test
    void testRollRange() {
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
    void testRollInverseRange() {
        int min = 1;
        int max = 10;

        for (int i = 0; i < AMOUNT_THROWS; i++) {
            int result = Dice.roll(max, min);
            assertTrue(result >= min);
            assertTrue(result <= max);
        }
    }

    @Test
    void testRollRangeZero() {
        assertEquals(0, Dice.roll(0, 0));
    }

    @Test
    void testRollNegativeRange() {
        int result;
        result = Dice.roll(-1, -15);
        assertTrue(result <= -1 && result >= -15);

        result = Dice.roll(0, -20);
        assertTrue(result <= 0 && result >= -20);

        result = Dice.roll(-1, 20);
        assertTrue(result >= -1 && result <= 20);
    }
}
