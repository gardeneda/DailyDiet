package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of exercise that the user has performed throughout the day
public class ExerciseList {
    private List<Exercise> workout;

    public ExerciseList() {
        workout = new ArrayList<>();
    }

    // GETTER METHODS
    public List<Exercise> getList() {
        return workout;
    }

    public Exercise getIndex(int i) {
        return workout.get(i);
    }

    // ================================================================
    // EFFECTS: returns true if workout list is empty, otherwise false
    public boolean isEmpty() {
        return workout.size() == 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the specified exercise to the workout list.
    public void addExercise(Exercise exercise) {
        workout.add(exercise);
    }

    // REQUIRES: the workout list is NOT empty.
    // MODIFIES: this
    // EFFECTS: removes the most recently added exercise from the workout list.
    public void removeLastExercise() {
        int lastIndex = workout.size() - 1;
        workout.remove(lastIndex);
    }

    // EFFECTS: takes the calories burnt from each exercise in the workout list
    //          and returns the total amount of calories that the user has burnt in the day.
    public double totalCaloriesBurnt() {
        double sumOfTotalCaloriesBurnt = 0;
        for (Exercise i : workout) {
            sumOfTotalCaloriesBurnt += i.totalCaloriesBurnt();
        }
        return sumOfTotalCaloriesBurnt;
    }
}
