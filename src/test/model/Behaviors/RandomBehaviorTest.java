package model.Behaviors;

import model.Country;
import model.Enum.Phase;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static model.Behaviors.TestHelperBehavior.setUpMockCountry;
import static model.Behaviors.TestHelperBehavior.setUpMockPlayer;
import static model.Enum.PlayerColor.BLUE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RandomBehaviorTest {

    private Player testPlayer;
    private ArrayList<Country> allCountries;
    private ArrayList<Country> ownedCountries;

    @BeforeEach
    public void setUp() {
        testPlayer = new Player(BLUE, "Lu", new RandomBehavior());
        ownedCountries = new ArrayList<>();
        allCountries = new ArrayList<>();
    }

    @Test
    void placeSoldiers() {
        ownedCountries = TestHelperBehavior.makeList(4, testPlayer);
        allCountries.addAll(ownedCountries);
        allCountries.addAll(TestHelperBehavior.makeList(2, setUpMockPlayer()));
        allCountries.addAll(TestHelperBehavior.makeList(5, setUpMockPlayer()));
        allCountries.addAll(TestHelperBehavior.makeList(3, setUpMockPlayer()));

        assertEquals(Phase.ATTACKPHASE, testPlayer.getBehavior().placeSoldiers(allCountries, ownedCountries, 3));
        assertEquals(6, allCountries.get(0).getSoldiersCount());
        assertEquals(3, testPlayer.getSoldiersToPlace());
    }

    @Test
    void testAttackCountry() {
        Player testPlayer2 = new Player(BLUE, "testplayer02", new RandomBehavior());
        Country mockAttackCountry = setUpMockCountry(testPlayer2);

        allCountries.add(mockAttackCountry);
        allCountries.add(new Country("Spanien", 5, testPlayer));
        ownedCountries = TestHelperBehavior.makeList(1, testPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(allCountries, ownedCountries);
        verify(mockAttackCountry, times(1)).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void testAttackCountryOwnCountries() {

        Player ownTestPlayer = new Player(BLUE, "ownPlayer", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(ownTestPlayer);

        allCountries.add(mockAttackCountry);
        allCountries.add(new Country("Spanien", 5, ownTestPlayer));
        ownedCountries = TestHelperBehavior.makeList(1, ownTestPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(allCountries, ownedCountries);
        verify(mockAttackCountry, times(0)).invade(anyObject(), anyInt(), anyInt());

    }

    @Test
    void testAttackCountryNotOwnCountries() {
        Player opponentPlayer = new Player(BLUE, "opponentPlayer", new UserBehavior());
        Country mockAttackCountry = setUpMockCountry(opponentPlayer);

        allCountries.add(mockAttackCountry);
        allCountries.add(new Country("Spanien", 5, opponentPlayer));
        ownedCountries = TestHelperBehavior.makeList(1, opponentPlayer);
        ownedCountries.add(mockAttackCountry);

        testPlayer.getBehavior().attackCountry(allCountries, allCountries);
        verify(mockAttackCountry, times(0)).invade(anyObject(), anyInt(), anyInt());
    }

    @Test
    void moveSoldiers() {
        allCountries = TestHelperBehavior.makeList(2, testPlayer);
        ownedCountries = TestHelperBehavior.makeList(4, testPlayer);
        ownedCountries.add(allCountries.get(0));
        ownedCountries.add(allCountries.get(1));

        assertEquals(Phase.MOVINGPHASE, testPlayer.getBehavior().moveSoldiers(allCountries, ownedCountries));
    }
}