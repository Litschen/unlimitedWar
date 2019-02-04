package controller;

import model.Country;
import model.Player;
import model.RandomBehavior;

import java.util.ArrayList;

public class Board {

    //region data fields
    private int turnCount = 0;
    private ArrayList<Player> players;
    private ArrayList<Country> countries;
    //endregion

    //region constructors
    public Board(){
        players = new ArrayList<>();
        countries = new ArrayList<>();

        players.add(new Player("blue", "LMao", new RandomBehavior()));
        players.add(new Player("red", "Hotler", new RandomBehavior()));
        players.add(new Player("green", "Stalout", new RandomBehavior()));
        players.add(new Player("yellow", "Darfolini", new RandomBehavior()));
    }
    //endregion

    //region getter setter
    public Player getPlayer(int id){
        return players.get(id);
    }
    //endregion
}
