package ch.zhaw.unlimitedWar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardTest {

    private Card card;
    private Country mockCountry;

    @BeforeEach
    void setUp() {
        mockCountry = mock(Country.class);
        card = new Card(mockCountry);
    }

    @Test
    void testGetCardName() {
        when(mockCountry.getName()).thenReturn("name");

        assertEquals("name", card.getCardName());
        verify(mockCountry, times(1)).getName();
    }

    @Test
    void testCardBonus() {
        Player player = mock(Player.class);
        when(mockCountry.getOwner()).thenReturn(null);

        assertEquals(2, card.getCardBonus(player));
    }

    @Test
    void testCardBonusWithOwningBonus() {
        Player player = mock(Player.class);
        when(mockCountry.getOwner()).thenReturn(player);

        assertEquals(3, card.getCardBonus(player));
    }

    @Test
    void testIsPlayerOwner() {
        Player player = mock(Player.class);
        when(mockCountry.getOwner()).thenReturn(player);

        assertTrue(card.isPlayerOwner(player));
        verify(mockCountry, times(1)).getOwner();
    }

    @Test
    void testIsNotPlayerOwner() {
        Player player = mock(Player.class);
        when(mockCountry.getOwner()).thenReturn(null);

        assertFalse(card.isPlayerOwner(player));
        verify(mockCountry, times(1)).getOwner();
    }

}