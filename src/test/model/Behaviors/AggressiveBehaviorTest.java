package model.Behaviors;

import model.Country;
import model.Enum.PlayerColor;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggressiveBehaviorTest {

    private AggressiveBehavior aggressiveBehavior;
    private Player testAggressiveBehavior;
    private ArrayList<Country> owndestination;
    private ArrayList<Country> alldestination;
    private final int AMOUNTSOLDIERS = 100;


    @BeforeEach
    public void setUp() {
        List<PlayerColor> colorPlayer = new ArrayList<>();
        colorPlayer.addAll(Arrays.asList(PlayerColor.values()));
        testAggressiveBehavior = new Player(colorPlayer.remove(1), "testAggressiveBehavior", new AggressiveBehavior());
    }

    @Test
    void placeSoldiers() {

    }


    @Test
    void amountSoldiersToPlace() {

    }

    @Test
    void attackCountry() {
    }

    @Test
    void moveSoldiers() {
    }
}