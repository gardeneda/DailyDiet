package model;

import java.util.ArrayList;
import java.util.List;

public class ExerciseList {
    private List<Exercise> workout;

    public ExerciseList() {
        workout = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds the specified exercise to the list
    public void addExercise(Exercise exercise) {
        workout.add(exercise);
    }


    public double totalCaloriesBurnt() {
        double sumOfTotalCaloriesBurnt = 0;
        for (Exercise i : workout) {
            sumOfTotalCaloriesBurnt += i.totalCaloriesBurnt();
        }
        return 0.0;
    }


}
