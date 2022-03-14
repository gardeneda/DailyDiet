package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

// Represents a user of the application.
// Contains their basic info required to calculate metabolism and body mass index.

public class User implements Writable {
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double heightInCm;
    private double heightInMeters;
    private double bodyMassIndex;
    private int dailyMetabolism;
    private double weightGoal;
    private boolean isAchievingWeightGoal;

    public User(String name, int age, String gender, double weight, double height) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.heightInCm = height;
        this.heightInMeters = (height / 100);
        this.isAchievingWeightGoal = false;
    }


    // Getters
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getGender() {
        return this.gender;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getHeightInCm() {
        return this.heightInCm;
    }

    public double getHeightInMeters() {
        return this.heightInMeters;
    }

    public double getBodyMassIndex() {
        return this.bodyMassIndex;
    }

    public int getDailyMetabolism() {
        return this.dailyMetabolism;
    }

    public double getWeightGoal() {
        return this.weightGoal;
    }

    public boolean isAchievingWeightGoal() {
        return this.isAchievingWeightGoal;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        if (gender.equals("M") || gender.equals("F")) {
            this.gender = gender;
        }
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeightInCm(double height) {
        this.heightInCm = height;
    }

    public void setWeightGoal(double weight) {
        this.weightGoal = weight;
    }

    // MODIFIES: this
    // EFFECTS: calculates the body mass index of the User based on their
    //          current height and weight.
    //          The parameter is used with given weight instead of the user's (this.weight),
    //          as the user may want to calculate their weight goal.
    public double calculateBMI(double weight) {
        this.bodyMassIndex = (weight / (this.heightInMeters * this.heightInMeters));
        return this.bodyMassIndex;
    }

    // REQUIRES: given gender needs to be "F" or "M"
    // MODIFIES: this
    // EFFECTS: calculates the metabolism of the user based on their
    //          current age and gender.
    public double calculateMetabolism() {
        double metabolism;
        if (Objects.equals(this.gender, "F")) {
            metabolism = 447.593 + (9.247 * this.weight) + (3.089 * this.heightInCm) - (4.330 * this.age);
            this.dailyMetabolism = (int)metabolism;
        } else  if (Objects.equals(this.gender, "M")) {
            metabolism = 88.362 + (13.397 * this.weight) + (4.799 * this.heightInCm) - (5.677 * this.age);
            this.dailyMetabolism = (int)metabolism;
        }
        return this.dailyMetabolism;
    }

    // REQUIRES: given weight > 0
    // MODIFIES: this
    // EFFECTS: updates the weight goal of the user by their target weight goal.
    public void updateWeightGoal(double weight) {
        this.weightGoal = weight;
    }

    public void setAchievingWeightGoal(boolean bool) {
        isAchievingWeightGoal = bool;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("gender", gender);
        json.put("weight", weight);
        json.put("heightInCm", heightInCm);
        json.put("heightInMeters", heightInMeters);
        json.put("dailyMetabolism", dailyMetabolism);
        json.put("bodyMassIndex", bodyMassIndex);
        json.put("weightGoal", weightGoal);
        json.put("isAchievingWeightGoal", isAchievingWeightGoal);
        return json;
    }
}
