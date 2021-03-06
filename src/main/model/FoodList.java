package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents the food that the user has eaten throughout the day
public class FoodList {
    private List<Food> diet;

    public FoodList() {
        diet = new ArrayList<>();
    }

    // GETTER METHODS
    public List<Food> getList() {
        return diet;
    }

    // ================================================================
    // EFFECTS: returns true if diet list is empty, otherwise false
    public boolean isEmpty() {
        return diet.size() == 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the specified exercise to the workout list.
    public void addFood(Food food) {
        diet.add(food);
    }

    // REQUIRES: the diet list is NOT empty
    // MODIFIES: this
    // EFFECTS: removes the last food added into the diet list.
    public void removeLastFood() {
        int lastIndex = diet.size() - 1;
        diet.remove(lastIndex);
    }

    // EFFECTS: takes the calorie intake of all the food in the diet list
    //          and returns the total amount of calorie intake of the user for the day.
    public double totalCaloriesConsumed() {
        double sumOfTotalCaloriesConsumed = 0;
        for (Food i : diet) {
            sumOfTotalCaloriesConsumed += i.getCalories();
        }
        return sumOfTotalCaloriesConsumed;
    }
}
