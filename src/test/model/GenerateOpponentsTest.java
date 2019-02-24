package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GenerateOpponentsTest {

    private AggressiveBehavior aggressiveBehavior;
    private UserBehavior userBehavior;
    private RandomBehavior randomBehavior;

    @BeforeEach
    void setUp() {
        this.setUpAggressiveBehavior();
        this.setUpUserBehavior();
        this.setUpRandomBehavior();
    }

    //region set up methods
    private void setUpAggressiveBehavior() {
        aggressiveBehavior = new AggressiveBehavior();
    }

    private void setUpUserBehavior() {
        userBehavior = new UserBehavior();
    }

    private void setUpRandomBehavior() {
        randomBehavior = new RandomBehavior();
    }
    //endregion

    @Test
    public void test() {

    }

}
