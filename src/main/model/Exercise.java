package model;

import java.util.Objects;

// Represents the exercise that the user does.
// Contains information of how many calories they burn.
// Currently, only implemented basic exercises, but the code supports the
// inclusion of more exercises.

public class Exercise {
    private int hours;
    private int minutes;
    private double caloriesBurnPerMinute;
    private double caloriesBurnPerHour;
    private String name;


    public Exercise(String exerciseName, int hours, int mintues) {
        this.name = exerciseName;
        this.hours = hours;
        this.minutes = minutes;
        if (Objects.equals(exerciseName, "running")) {
            this.caloriesBurnPerHour = 606.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
        } else if (Objects.equals(exerciseName, "swimming")) {
            this.caloriesBurnPerHour = 720.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
        } else if (Objects.equals(exerciseName, "bicycling")) {
            this.caloriesBurnPerHour = 720.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
        } else if (Objects.equals(exerciseName, "weightlifting")) {
            this.caloriesBurnPerHour = 432.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
        } else if (Objects.equals(exerciseName, "calisthenics")) {
            this.caloriesBurnPerHour = 324.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
        } else if (Objects.equals(exerciseName, "hiking")) {
            this.caloriesBurnPerHour = 432.0;
            this.caloriesBurnPerMinute = this.caloriesBurnPerHour / 60;
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

    // EFFECTS: returns the total amount of calories burnt by performing the exercise.
    public double totalCaloriesBurnt() {
        int totalTimeInMinutes = (this.hours * 60) + this.minutes;
        return this.caloriesBurnPerMinute * totalTimeInMinutes;
    }
}
