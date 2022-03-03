package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExerciseListTest {
    private ExerciseList emptyList;
    private ExerciseList oneWorkOut;
    private ExerciseList fourWorkOut;

    private Exercise running;
    private Exercise swimming;
    private Exercise weightlifting;
    private Exercise calisthenics;


    @BeforeEach
    void setup() {
        running = new Exercise("running", 2, 0);
        swimming = new Exercise("swimming", 0, 50);
        weightlifting = new Exercise("weightlifting", 0, 0);
        calisthenics = new Exercise("calisthenics", 1, 27);

        emptyList = new ExerciseList();

        oneWorkOut = new ExerciseList();
        oneWorkOut.addExercise(running);

        fourWorkOut = new ExerciseList();
        fourWorkOut.addExercise(running);
        fourWorkOut.addExercise(swimming);
        fourWorkOut.addExercise(weightlifting);
        fourWorkOut.addExercise(calisthenics);
    }

    @Test
     void testRemoveExercise() {
        oneWorkOut.removeLastExercise();
        assertTrue(oneWorkOut.isEmpty());

        fourWorkOut.removeLastExercise();
        fourWorkOut.removeLastExercise();
        fourWorkOut.removeLastExercise();
        fourWorkOut.removeLastExercise();
        assertTrue(fourWorkOut.isEmpty());
    }

    @Test
    void testSumOfTotalCaloriesBurnt() {
        assertEquals(0, emptyList.totalCaloriesBurnt());
        assertEquals(1212.0, oneWorkOut.totalCaloriesBurnt());
        assertEquals(2281.8, fourWorkOut.totalCaloriesBurnt());
    }
}
