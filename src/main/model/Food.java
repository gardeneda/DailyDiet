package model;

// Represents the food that the user eats, which they will specify the calories and name of food eaten.
public class Food {
    private double calories;
    private String name;

    public Food(double calories, String name) {
        this.name = name;
        this.calories = calories;
    }

    // Getters
    public double getCalories() {
        return this.calories;
    }

    public String getName() {
        return this.name;
    }
}
