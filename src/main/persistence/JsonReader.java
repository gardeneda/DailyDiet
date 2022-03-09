package persistence;

import model.*;
import exceptions.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Referenced from JSON Serialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads User data, and Day data from its JSON data
// stored at its local data file.
public class JsonReader {
    private String source;
    private String sourceUser;

    // EFFECTS: constructs a reader that reads from the local data file.
    public JsonReader(String source, String sourceUser) throws IOException {
        this.source = source;
        this.sourceUser = sourceUser;
    }


    // EFFECTS: Reads User string data file as a string and returns it
    //          Throws an IOException if error occurs reading data from file
    private String stringifyUser(String sourceUser) throws IOException {
        StringBuilder userBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceUser), StandardCharsets.UTF_8)) {
            stream.forEach(s -> userBuilder.append(s));
        }

        return userBuilder.toString();
    }

    // EFFECTS: Reads FoodList string data file as a string and returns it
    private String stringifyDay(String source) throws IOException {
        StringBuilder dayBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> dayBuilder.append(s));
        }

        return dayBuilder.toString();
    }

    // EFFECTS: reads User data from file and returns it
    private User userRead() throws IOException {
        String jsonData = stringifyUser(sourceUser);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseUser(jsonObject);
    }

    // EFFECTS: reads FoodList data from file and returns it
    private Day dayRead() throws IOException {
        String jsonData = stringifyDay(source);
        JSONObject jsonObject = new JSONObject(jsonData);

        return parseDay(jsonObject);
    }

    // EFFECTS: parses user from JSON Object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int age = jsonObject.getInt("age");
        String gender = jsonObject.getString("gender");
        double weight = jsonObject.getDouble("weight");
        double heightInCm = jsonObject.getDouble("heightInCm");

        return new User(name, age, gender, weight, heightInCm);
    }

    private Day parseDay(JSONObject jsonObject) {
        int date = jsonObject.getInt("date");
        JSONArray exerciseJson = jsonObject.getJSONArray("exerciseList");
        ExerciseList exerciseList = convertToExerciseList(exerciseJson);

        JSONArray foodJson = jsonObject.getJSONArray("foodList");
        FoodList foodList = convertToFoodList(foodJson);

        return new Day(date, exerciseList, foodList);
    }

    private ExerciseList convertToExerciseList(JSONArray jsonArray) {
        ExerciseList exerciseList = new ExerciseList();
        for (Object j : jsonArray) {
            JSONObject json = (JSONObject) j;
            exerciseList.addExercise(convertToExercise(json));
        }

        return exerciseList;
    }

    private Exercise convertToExercise(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int hours = jsonObject.getInt("hours");
        int minutes = jsonObject.getInt("minutes");

        return new Exercise(name, hours, minutes);
    }

    private FoodList convertToFoodList(JSONArray jsonArray) {
        FoodList foodList = new FoodList();
        for (Object j: jsonArray) {
            JSONObject json = (JSONObject) j;
            foodList.addFood(convertToFood(json));
        }

        return foodList;
    }

    private Food convertToFood(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double calories = jsonObject.getDouble("calories");

        return new Food(calories, name);
    }
}


