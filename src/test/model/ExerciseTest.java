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
        running = new Exercise("running");
        swimming = new Exercise("swimming");
        bicycling = new Exercise("bicycling");
        weightlifting = new Exercise("weightlifting");
        calisthenics = new Exercise("calisthenics");
        hiking = new Exercise("hiking");
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
        assertEquals(running.getCaloriesBurnPerHour(), running.totalCaloriesBurnt(1, 0));
        assertEquals(running.getCaloriesBurnPerMinute() * 132,
                running.totalCaloriesBurnt(2, 12));
        assertEquals(running.getCaloriesBurnPerHour(), running.totalCaloriesBurnt(0, 60));
        assertEquals(running.getCaloriesBurnPerMinute(), running.totalCaloriesBurnt(0, 1));
        assertEquals(0, running.totalCaloriesBurnt(0, 0));
    }
}
