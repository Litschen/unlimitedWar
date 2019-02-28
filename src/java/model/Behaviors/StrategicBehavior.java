package model.Behaviors;

import model.Country;
import model.Interface.IBehavior;

import java.util.ArrayList;

public class StrategicBehavior implements IBehavior {
    @Override
    public int placeSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries, int soldiersToPlace) {
        return 0;
    }

    @Override
    public void attackCountry(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }

    @Override
    public void moveSoldiers(ArrayList<Country> allCountries, ArrayList<Country> ownedCountries) {

    }
}