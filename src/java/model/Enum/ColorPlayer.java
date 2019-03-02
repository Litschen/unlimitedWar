package model.Enum;

import java.util.Random;

public enum ColorPlayer {

    RED, BLUE, GREEN, YELLOW;

    private static final ColorPlayer[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();

    public static ColorPlayer getRandomColor()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }

}
