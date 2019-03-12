package model.Behaviors;

import model.Country;
import model.Player;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testHelperBehavior {



    public static Country setUpMockCountry(Player player) {
        Country mockCountry = mock(Country.class);
        when(mockCountry.canInvade(anyObject())).thenReturn(true);
        when(mockCountry.getOwner()).thenReturn(player);

        return mockCountry;
    }
}
