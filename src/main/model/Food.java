package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the food that the user eats, which they will specify the calories and name of food eaten.
public class Food implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("calories", calories);
        return json;
    }

}
