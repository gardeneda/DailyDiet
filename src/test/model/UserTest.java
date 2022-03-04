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
    public void testGetName() {
        assertEquals("Jake", youngBoy.getName());
        assertEquals("Ryan", adultMale.getName());
        assertEquals("Cecily", youngGirl.getName());
        assertEquals("Lisa", adultWoman.getName());
        assertEquals("Jacob", elderlyMan.getName());
        assertEquals("Irene", elderlyWoman.getName());
    }

    @Test
    public void testGetGender() {
        assertEquals("M", youngBoy.getGender());
        assertEquals("F", youngGirl.getGender());
        assertEquals("M", adultMale.getGender());
        assertEquals("F", adultWoman.getGender());
        assertEquals("M", elderlyMan.getGender());
        assertEquals("F", elderlyWoman.getGender());
    }

    @Test
    public void testGetBodyMassIndex() {
        assertEquals(youngBoy.calculateBMI(youngBoy.getWeight()), youngBoy.getBodyMassIndex());
        assertEquals(youngGirl.calculateBMI(youngGirl.getWeight()), youngGirl.getBodyMassIndex());
        assertEquals(adultMale.calculateBMI(adultMale.getWeight()), adultMale.getBodyMassIndex());
        assertEquals(adultWoman.calculateBMI(adultWoman.getWeight()), adultWoman.getBodyMassIndex());
    }

    @Test
    public void testGetDailyMetabolism() {
        assertEquals(youngBoy.calculateMetabolism(), youngBoy.getDailyMetabolism());
        assertEquals(youngGirl.calculateMetabolism(), youngGirl.getDailyMetabolism());
        assertEquals(adultMale.calculateMetabolism(), adultMale.getDailyMetabolism());
        assertEquals(adultWoman.calculateMetabolism(), adultWoman.getDailyMetabolism());
        assertEquals(elderlyWoman.calculateMetabolism(), elderlyWoman.getDailyMetabolism());
        assertEquals(elderlyMan.calculateMetabolism(), elderlyMan.getDailyMetabolism());
    }

    @Test
    public void testCalculateBMI() {
        assertEquals((youngBoy.getWeight() / (youngBoy.getHeightInMeters() * youngBoy.getHeightInMeters())),
                youngBoy.calculateBMI(youngBoy.getWeight()));
        assertEquals((adultMale.getWeight() / (adultMale.getHeightInMeters() * adultMale.getHeightInMeters())),
                adultMale.calculateBMI(adultMale.getWeight()));
        assertEquals((youngGirl.getWeight() / (youngGirl.getHeightInMeters() * youngGirl.getHeightInMeters())),
                youngGirl.calculateBMI(youngGirl.getWeight()));
        assertEquals((adultWoman.getWeight() / (adultWoman.getHeightInMeters() * adultWoman.getHeightInMeters())),
                adultWoman.calculateBMI(adultWoman.getWeight()));
        assertEquals((elderlyMan.getWeight() / (elderlyMan.getHeightInMeters() * elderlyMan.getHeightInMeters())),
                elderlyMan.calculateBMI(elderlyMan.getWeight()));
        assertEquals((elderlyWoman.getWeight() / (elderlyWoman.getHeightInMeters() * elderlyWoman.getHeightInMeters())),
                elderlyWoman.calculateBMI(elderlyWoman.getWeight()));
    }

    @Test
    public void testCalculateMetabolism() {
        assertEquals((int)(88.362 + (13.397 * youngBoy.getWeight()) + (4.799 * youngBoy.getHeightInCm()) -
                (5.677 * youngBoy.getAge())), youngBoy.calculateMetabolism());
        assertEquals((int)(447.593 + (9.247 * youngGirl.getWeight()) + (3.089 * youngGirl.getHeightInCm()) -
                (4.330 * youngGirl.getAge())), youngGirl.calculateMetabolism());
        assertEquals((int)(88.362 + (13.397 * adultMale.getWeight()) + (4.799 * adultMale.getHeightInCm()) -
                (5.677 * adultMale.getAge())), adultMale.calculateMetabolism());
        assertEquals((int)(447.593 + (9.247 * adultWoman.getWeight()) + (3.089 * adultWoman.getHeightInCm()) -
                (4.330 * adultWoman.getAge())), adultWoman.calculateMetabolism());
    }

    @Test
    public void testUpdateWeightGoal() {
        youngBoy.updateWeightGoal(60.6);
        assertEquals(60.6, youngBoy.getWeightGoal());
        adultMale.updateWeightGoal(200.1);
        assertEquals(200.1, adultMale.getWeightGoal());
        youngGirl.updateWeightGoal(0);
        assertEquals(0, youngGirl.getWeightGoal());
    }

    @Test
    void testSetAchievingWeightGoal() {
        youngBoy.setAchievingWeightGoal(true);
        assertTrue(youngBoy.isAchievingWeightGoal());
        assertFalse(youngGirl.isAchievingWeightGoal());
    }
}
