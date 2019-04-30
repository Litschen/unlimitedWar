package ch.zhaw.unlimitedWar.model;

import ch.zhaw.unlimitedWar.helpers.TestHelperBehavior;
import ch.zhaw.unlimitedWar.model.behavior.RandomBehavior;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContinentTest {

    private Player player;
    private Continent continent;

    @BeforeEach
    void setUp() {
        player = new Player(PlayerColor.BLUE, "", new RandomBehavior());
        continent = new Continent(10, "");
    }

    @Test
    void testGetTextColorPositive() {
        continent.addCountries(TestHelperBehavior.getCountryList(5, player));
        assertEquals(player.getPlayerColor().toString(), continent.getTextColor());

    }

    @Test
    void testGetTextColorNegative() {
        continent.addCountries(TestHelperBehavior.getCountryList(5, player));
        continent.addCountries(TestHelperBehavior.getCountryList(5, TestHelperBehavior.getMockPlayer()));
        assertEquals(Continent.STANDARD_TEXT_COLOR, continent.getTextColor());
    }
}