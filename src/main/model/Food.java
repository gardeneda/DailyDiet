package model;

public class Food {
    private double calories;
    private String name;

    public Food(double calories, String name) {
        this.name = name;
        this.calories = calories;
    }

    public double getCalories() {
        return this.calories;
    }

    public String getName() {
        return this.name;
    }
}
