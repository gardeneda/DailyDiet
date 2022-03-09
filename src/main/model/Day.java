package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Referenced from JSON Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents the record of the entire progress the user has made during a day.
// Holds onto information about what they ate that day and what exercises they performed on that day.
public class Day implements Writable {

    private int date;
    private ExerciseList exerciseList;
    private FoodList foodList;

    // EFFECTS: constructs a day with the date the day was made, the list of exercise done that day
    //          and the list of food eaten during that day.
    public Day(int date, ExerciseList exerciseList, FoodList foodList) {
        this.date = date;
        this.exerciseList = exerciseList;
        this.foodList = foodList;
    }

    public int getDate() {
        return date;
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("exerciseList", exerciseToJson());
        json.put("foodList", foodToJson());
        return json;
    }

    public JSONArray exerciseToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Exercise e : exerciseList.getList()) {
            jsonArray.put(e.toJson());
        }

        return jsonArray;
    }

    public JSONArray foodToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Food f: foodList.getList()) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}
