package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User youngBoy;
    private User adultMale;
    private User youngGirl;
    private User adultWoman;
    private User elderlyMan;
    private User elderlyWoman;


    @BeforeEach
    public void setUp() {
        youngBoy = new User("Jake", 7, "M", 32.2, 121.6);
        adultMale = new User("Ryan", 32, "M", 88.4, 178.9);
        youngGirl = new User("Cecily", 11, "F", 37.7, 142.9);
        adultWoman = new User("Lisa", 28, "F", 53.5, 164.5);
        elderlyMan = new User("Jacob", 63, "M", 71.0, 177.3);
        elderlyWoman = new User("Irene", 67, "F", 63.4, 168.2);
    }

    @Test
    public void testCalculateBMI() {
        assertEquals((youngBoy.getWeight() / youngBoy.getHeight() * youngBoy.getHeight()), youngBoy.calculateBMI());
        assertEquals((adultMale.getWeight() / adultMale.getHeight() * adultMale.getHeight()), adultMale.calculateBMI());


    }

    @Test
    public void testCalculateMetabolism() {
        // stub
    }

    @Test
    public void testUpdateBirthDate() {
        // stub
    }

    @Test
    public void testUpdateWeightGoal() {
        // stub
    }
}
