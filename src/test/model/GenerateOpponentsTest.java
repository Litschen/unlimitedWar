package test.model;

import org.junit.Test;

public class GenerateOpponentsTest {

    AggressiveBehavior  aggressiveBehavior = new AggressiveBehavior ();
    UserBehavior userBehavior = new UserBehavior();
    RandomBehavior randomBehavior = new RandomBehavior();

    @Test
    public void setUpAggressiveBehavior(){
        new AggressiveBehavior(null);
    }

    @Test
    public void setUpUserBehavior(){
        new  UserBehavior(null);
    }

    @Test
    public void setUpRandomBehavior(){
        new  RandomBehavior(null);
    }

}
