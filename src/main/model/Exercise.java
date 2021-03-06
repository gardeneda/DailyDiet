package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents the exercise that the user does.
// Contains information of how many calories they burn.
// Currently, only implemented basic exercises, but the code supports the
// inclusion of more exercises.

public class Exercise implements Writable {
    private int hours;
    private int minutes;
    private double caloriesBurnPerMinute;
    private double caloriesBurnPerHour;
    private String name;


    public Exercise(String exerciseName, int hours, int minutes) {
        this.name = exerciseName;
        this.hours = hours;
        this.minutes = minutes;
        switch (exerciseName) {
            case "running":
                this.caloriesBurnPerHour = 606.0;
                this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
                break;
            case "swimming":
            case "bicycling":
                this.caloriesBurnPerHour = 720.0;
                this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
                break;
            case "weightlifting":
            case "hiking":
                this.caloriesBurnPerHour = 432.0;
                this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
                break;
            case "calisthenics":
                this.caloriesBurnPerHour = 324.0;
                this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
                break;
        }
    }

    // Getters
    public double getCaloriesBurnPerHour() {
        return this.caloriesBurnPerHour;
    }

    public double getCaloriesBurnPerMinute() {
        return this.caloriesBurnPerMinute;
    }

    public String getExerciseName() {
        return this.name;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    // EFFECTS: returns the total amount of calories burnt by performing the exercise.
    public double totalCaloriesBurnt() {
        int totalTimeInMinutes = (this.hours * 60) + this.minutes;
        return this.caloriesBurnPerMinute * totalTimeInMinutes;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("hours", hours);
        json.put("minutes", minutes);

        return json;
    }
}
