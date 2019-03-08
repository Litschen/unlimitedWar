package model.Behaviors;

import model.Enum.PlayerColor;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AggressiveBehaviorTest {

    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        List<PlayerColor> colorPlayer = new ArrayList<>();
        colorPlayer.addAll(Arrays.asList(PlayerColor.values()));
        testPlayer = new Player(colorPlayer.remove(1), "testAgressiveComputer", new AggressiveBehavior ());
    }

    @Test
    void placeSoldiers() {



    }

    @Test
    void attackCountry() {
    }

    @Test
    void moveSoldiers() {
    }
}