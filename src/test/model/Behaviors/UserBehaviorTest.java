package model.Behaviors;

import model.Country;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import model.Enum.Phase;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

class UserBehaviorTest {

    private UserBehavior testUserBehavior;
    private Country testCountry;
    private ArrayList<Country> selectedCountries;
    private ArrayList<Country> ownedCountries;


    @BeforeEach
    public void setUp() {
        testUserBehavior = new UserBehavior();
        selectedCountries = new ArrayList<>();
        ownedCountries = new ArrayList<>();
    }

    @Test
    void testSelectedCountries() {

        Assert.assertNotNull("List shouldn't be null", selectedCountries);
        assertEquals(0, selectedCountries.size());
    }

    @Test
    void testOwnedCountries() {

        assertNotNull("List shouldn't be null", ownedCountries);
        assertEquals(0, ownedCountries.size());
        assertEquals("Wrong 1st element", "Customer1", ownedCountries.get(0));
        assertEquals("Wrong 2nd element", "Customer2", ownedCountries.get(1));
        assertEquals("Wrong 3rd element", "Customer3", ownedCountries.get(2));
        assertEquals("Wrong 3rd element", "Customer3", ownedCountries.get(3));
    }


    @Test
    void testPlaceSoldiers() {

        assertEquals(Phase.SETTINGPHASE, testUserBehavior.placeSoldiers(selectedCountries, ownedCountries, 3));
        assertEquals(Phase.SETTINGPHASE, testUserBehavior.placeSoldiers(selectedCountries, ownedCountries, 2));
        assertEquals(Phase.SETTINGPHASE, testUserBehavior.placeSoldiers(selectedCountries, ownedCountries, 3));
        assertEquals(Phase.ATTACKPHASE, testUserBehavior.placeSoldiers(selectedCountries, ownedCountries, 0));

    }

    @Test
    void testAttackCountry() {

        assertEquals(Phase.MOVINGPHASE, testUserBehavior.attackCountry(selectedCountries, ownedCountries));
    }

    @Test
    void testMoveSoldiers() {


        assertEquals(Phase.MOVINGPHASE, testUserBehavior.attackCountry(selectedCountries, ownedCountries));
        testCountry.shiftSoldiers(3, testCountry);
        assertEquals(Phase.AIPHASE, testUserBehavior.attackCountry(selectedCountries, ownedCountries));

    }
}