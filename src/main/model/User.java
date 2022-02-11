package model;

import java.util.Objects;
import java.time.LocalDate;

// Represents a user of the application.
// Contains their basic info required to calculate metabolism and body mass index.

public class User {
    private String name;
    private int age;
    private String gender;
    private double weight;
    private double heightInCm;
    private double heightInMeters;
    private double bodyMassIndex;
    private int dailyMetabolism;
    private double weightGoal;
    private int birthDate;

    public User(String name, int age, String gender, double weight, double height, int birthDate) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.heightInCm = height;
        this.heightInMeters = (height / 100);
        this.weightGoal = 0.0;
        this.birthDate = birthDate;
        this.bodyMassIndex = 0.0;
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

    public int getBirthDate() {
        return this.birthDate;
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


    // MODIFIES: this
    // EFFECTS: calculates the body mass index of the User based on their
    //          current height and weight.
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


    // MODIFIES: this
    // EFFECTS: automatically calculates the user's age based on the birthdate given.
    //          this would allow the user's age to be up-to-date even if time passes.
//    public int updateAge() {
//        LocalDate dateToday = LocalDate.now();
//
//        return 1; // stub
//    }
////    Will be implementing this later after studying a bit more.


    // REQUIRES: given weight > 0
    // MODIFIES: this
    // EFFECTS: updates the weight goal of the user by their target weight goal.
    public void updateWeightGoal(double weight) {
        if (calculateBMI(weight) <= 18.5) {
            System.out.println("You're aiming to be underweight.");
            System.out.println("Is this what you really want?");
            this.weightGoal = weight;
        } else if (calculateBMI(weight) > 18.5 && calculateBMI(weight) <= 24.99) {
            System.out.println("Successfully updated your new weight goal!");
            this.weightGoal = weight;
        } else {
            System.out.println("You're aiming to be obese.");
            System.out.println("Is this what you really want?");
            this.weightGoal = weight;
        }
    }
}

