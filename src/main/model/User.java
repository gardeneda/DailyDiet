package model;

public class User {

    private String name;
    private int birthDate;
    private String gender;
    private double weight;
    private double height;
    private double bodyMassIndex;
    private int dailyMetabolism;
    private double weightGoal;

    public User(String name, int birthDate, String gender, double weight, double height) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height / 100;
        this.weightGoal = 0.0;
    }


    // Getters
    public String getName() {
        return this.name;
    }

    public int getBirthDate() {
        return this.birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public double getWeight() {
        return this.weight;
    }

    public double getHeight() {
        return this.height;
    }

    public double getBodyMassIndex() {
        return this.bodyMassIndex;
    }

    public int getDailyMetabolism() {
        return this.dailyMetabolism;
    }

    // MODIFIES: this
    // EFFECTS: calculates the body mass index of the User based on their
    //          current height and weight.
    public double calculateBMI() {
        return 1.1; // stub
    }

    // MODIFIES: this
    // EFFECTS: calculates the metabolism of the user based on their
    //          current age and gender.
    public double calculateMetabolism() {
        return 1.1; // stub
    }


    // MODIFIES: this
    // EFFECTS: automatically calculates the user's age based on the birthdate given.
    public int updateBirthDate() {
        return 1; // stub
    }

    // MODIFIES: this
    // EFFECTS: updates the weight goal of the user by their target weight goal.
    public double updateWeightGoal() {
        return 1.1; // stub
    }
}

