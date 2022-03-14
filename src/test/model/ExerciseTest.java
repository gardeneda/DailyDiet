package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExerciseTest {
    private Exercise running;
    private Exercise swimming;
    private Exercise bicycling;
    private Exercise weightlifting;
    private Exercise calisthenics;
    private Exercise hiking;

    @BeforeEach
    public void setup() {
        running = new Exercise("running", 1, 0);
        swimming = new Exercise("swimming", 2, 12);
        bicycling = new Exercise("bicycling", 0, 60);
        weightlifting = new Exercise("weightlifting", 0, 1);
        calisthenics = new Exercise("calisthenics", 0, 0);
        hiking = new Exercise("hiking", 0, 59);
    }

    @Test
    public void testConstructor() {
        assertEquals("running", running.getExerciseName());
        assertEquals(606.0, running.getCaloriesBurnPerHour());

        assertEquals("swimming", swimming.getExerciseName());
        assertEquals(720.0, swimming.getCaloriesBurnPerHour());

        assertEquals("bicycling", bicycling.getExerciseName());
        assertEquals(720.0, bicycling.getCaloriesBurnPerHour());

        assertEquals("weightlifting", weightlifting.getExerciseName());
        assertEquals(432.0, weightlifting.getCaloriesBurnPerHour());

        assertEquals("calisthenics", calisthenics.getExerciseName());
        assertEquals(324.0, calisthenics.getCaloriesBurnPerHour());

        assertEquals("hiking", hiking.getExerciseName());
        assertEquals(432.0, hiking.getCaloriesBurnPerHour());
    }

    @Test
    public void testTotalCaloriesBurnt() {
        assertEquals(running.getCaloriesBurnPerHour(), running.totalCaloriesBurnt());
        assertEquals(swimming.getCaloriesBurnPerMinute() * 132, swimming.totalCaloriesBurnt());
        assertEquals(bicycling.getCaloriesBurnPerHour(), bicycling.totalCaloriesBurnt());
        assertEquals(weightlifting.getCaloriesBurnPerMinute(), weightlifting.totalCaloriesBurnt());
        assertEquals(0, calisthenics.totalCaloriesBurnt());
        assertEquals(hiking.getCaloriesBurnPerMinute() * 59, hiking.totalCaloriesBurnt());
    }
}
