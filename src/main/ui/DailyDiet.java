package ui;

import model.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

// Represents the UI interface that the users will be using to interact with the DailyDiet application.
// Made some references to the TellerApp to see how some things need to be implemented.

public class DailyDiet {
    private FoodList diet;
    private ExerciseList workout;
    private Scanner sc;

    // EFFECTS: runs the DailyDiet application
    public DailyDiet() {
        runDailyDiet();
    }

    // MODIFIES: this
    // EFFECTS: takes in user input and converts them to usable data for
    //          the DailyDiet application to run.
    private void runDailyDiet() {
        // TODO: Need to completely re-haul the system here.
        initUtils();
        User user = attainUserInfo();
        userBodyMassIndexMetabolismStatement(user);

        reportFood();
        insertFood();
        reportExercise();
        insertExercise();

        askUserWeightGoal(user);
        double sumCalories = calorieStatus(user);
        losingOrGainingWeight(user, sumCalories);
        System.out.println("\nStrategize better if you did not meet your goal today."
                            + " However, if you did meet your goals today - keep up the good work!");
    }

    // EFFECTS: initiates the different food list, exercise list and the scanner to allow users to
    //          interact with the terminal.
    private void initUtils() {
        diet = new FoodList();
        workout = new ExerciseList();
        sc = new Scanner(System.in);
    }

    // REQUIRES: user may not choose any gender other than "M" or "F"
    // MODIFIES: this
    // EFFECTS: initializes the User with the user input given to cater to well-reflect the user.
    private User attainUserInfo() {
        System.out.println("What is your name?: ");
        String name = sc.nextLine();

        System.out.println("How old are you?: ");
        int age = sc.nextInt();

        System.out.println("What is your biological gender? Answer with 'F' for female, 'M' for male: ");
        String gender = sc.next();

        System.out.println("How much do you weigh in kg?: ");
        double weight = sc.nextDouble();

        System.out.println("How tall are you in cm? (You can answer up to one decimal point: ");
        double height = sc.nextDouble();

        System.out.println("When is your birthdate? Answer YYYYMMDD. (Ex 19900125): ");
        int birthDate = sc.nextInt();

        return new User(name, age, gender, weight, height, birthDate);
    }


    // EFFECTS: tells the user of their BMI and their basic metabolism to give them an idea
    //          about their current state of their body and how much calories they are burning passively.
    private void userBodyMassIndexMetabolismStatement(User user) {
        double userMetabolism = user.calculateMetabolism();
        double userBMI = user.calculateBMI(user.getWeight());
        System.out.println();
        System.out.println("=========================================");
        System.out.println("Your basic metabolic rate is automatically calculated to be: "
                + userMetabolism + " Calories");
        System.out.println("Your BMI is " + String.format("%.2f", userBMI));
        System.out.println();
        System.out.println("BMI less or equal to 18,5 means you're underweight.");
        System.out.println("BMI in the range of 18.5 and 24.99 means you're at normal weight.");
        System.out.println("BMI above or equal to 25 means you're overweight.");
    }

    // EFFECTS: prints out the list of food that the user has eaten throughout the day
    private void reportFood() {
        System.out.println("\n\nToday you ate these: ");
        for (Food f: diet.getList()) {
            System.out.println(f.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the food that the user has eaten throughout their day
    //          and places them inside an ArrayList. The user will provide the
    //          parameters of the name of food they have eaten and its calories.
    private void insertFood() {
        while (true) {
            System.out.println("If you need to add a food entry, type 'Y'. \n"
                    + "If you need to remove the last food entered, press 'R'.\n"
                    + "If you have no more entries, type 'N')");
            String userFoodResponse = sc.next();

            if (Objects.equals(userFoodResponse, "N")) {
                break;
            } else if (Objects.equals(userFoodResponse, "R") && !(diet.isEmpty())) {
                diet.removeLastFood();
                continue;
            }

            System.out.println("What foods have you eaten today (or as of now?)\nName of Food: ");
            String foodName = sc.next();

            System.out.println("Calorie of Food: ");
            double foodCalorie = sc.nextDouble();

            Food food = new Food(foodCalorie, foodName);
            diet.addFood(food);
            System.out.println("\n");
        }
    }

    // EFFECTS: prints out the exercises that the user has done throughout the day
    private void reportExercise() {
        System.out.println("Currently you did these exercises: ");
        for (Exercise e: workout.getList()) {
            System.out.println(e.getExerciseName());
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the exercise that done throughout their day and places them
    //          inside an ArrayList. There is a selection of which exercises the user has done
    //          and the user must choose one of the selections and provide how long they
    //          exercised for in hours and minutes.
    private void insertExercise() {
        while (true) {
            System.out.println("If you need to add an exercise entry, type 'Y'.\nIf you need to remove the last "
                    + "exercise entered, press 'R'.\nIf you have no more entries, type 'N'");
            String userExerciseResponse = sc.next().toUpperCase();

            if (Objects.equals(userExerciseResponse, "N")) {
                break;
            } else if (Objects.equals(userExerciseResponse, "R") && !(diet.isEmpty())) {
                workout.removeLastExercise();
                continue;
            }
            System.out.println("What exercises did you do today? Choose an exercise from the list below");
            System.out.println("Running\nSwimming\nBicycling\nWeightlifting\nCalisthenics\nHiking");
            System.out.println("Which did you do out of the above? ");

            String exerciseName = sc.next().toLowerCase();
            System.out.println("Hours: ");
            int exerciseHours = sc.nextInt();
            System.out.println("Minutes: ");
            int exerciseMinutes = sc.nextInt();

            Exercise exercise = new Exercise(exerciseName, exerciseHours, exerciseMinutes);
            workout.addExercise(exercise);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets weight goal that the user wants to reach from their current weight.
    private void askUserWeightGoal(User user) {
        System.out.println("What is your weight goal?" + " Your current weight is " + user.getWeight() + " kg.");
        double weightGoal = sc.nextDouble();
        user.updateWeightGoal(weightGoal);
    }

    // EFFECTS: returns the total sum of Calories that the user has left after eating and exercising
    //         throughout their day.
    private double calorieStatus(User user) {
        double caloriesConsumed = diet.totalCaloriesConsumed();
        double caloriesBurnt = workout.totalCaloriesBurnt();
        System.out.println("Total calories consumed: " + caloriesConsumed);
        System.out.println("Total calories burnt: " + caloriesBurnt);
        return diet.totalCaloriesConsumed() - workout.totalCaloriesBurnt() - user.getDailyMetabolism();
    }

    // EFFECTS: tells the user their net calories and tell them whether they are losing or gaining weight
    //          and whether they are making progress in regards to their weight goal.
    private void losingOrGainingWeight(User user, double sumCalories) {
        System.out.println("\nYour total net calorie right now is: " + sumCalories + "Calories.");
        if (user.getWeightGoal() > user.getWeight()) {
            if (sumCalories > 0) {
                System.out.println("You're successfully gaining weight to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
            } else {
                System.out.println("You're losing weight right now, "
                        + "as you are using more calories than your body can store it.");
            }
        } else {
            if (sumCalories > 0) {
                System.out.println("You're successfully gaining weight to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
            } else {
                System.out.println("You're losing weight right now, "
                        + "as you are eating less calories or are exercising too much.");
            }
        }
    }
}
