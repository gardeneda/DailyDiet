package ui;

import model.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

// Represents the UI interface that the users will be using to interact with the DailyDiet application.
// Made some references to the TellerApp to see how some things need to be implemented.

public class DailyDiet {
    private FoodList diet;
    private ExerciseList workout;
    private Scanner sc;

    public DailyDiet() {
        runDailyDiet();
    }

    private void runDailyDiet() {
        initUtils();
        User user = attainUserInfo();
        userBodyMassIndexMetabolismStatement(user);
        foodThroughoutTheDay();
        exerciseThroughoutTheDay();
        askUserWeightGoal(user);
        double sumCalories = calorieStatus(user);
        losingOrGainingWeight(user, sumCalories);
        System.out.println("\nStrategize better if you did not meet your goal today."
                            + " However, if you did meet your goals today - keep up the good work!");
    }

    private void initUtils() {
        diet = new FoodList();
        workout = new ExerciseList();
        sc = new Scanner(System.in);
    }

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

    private void foodThroughoutTheDay() {
        System.out.println("\n\n");
        while (true) {
            System.out.println("Today you ate these: ");
            diet.iterateItemsInList();

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

    private void exerciseThroughoutTheDay() {
        while (true) {
            System.out.println("Currently you did these exercises: ");
            workout.iterateItemsInList();

            System.out.println("If you need to add an exercise entry, type 'Y'.\nIf you need to remove the last "
                    + "exercise entered, press 'R'.\nIf you have no more entries, type 'N'");
            String userExerciseResponse = sc.next();

            if (Objects.equals(userExerciseResponse, "N")) {
                break;
            } else if (Objects.equals(userExerciseResponse, "R") && !(diet.isEmpty())) {
                workout.removeLastExercise();
                continue;
            }
            System.out.println("What exercises did you do today? Choose an exercise from the list below");
            System.out.println("Running\nSwimming\nBicycling\nWeightlifting\nCalisthenics\nHiking");
            System.out.println("Which did you do out of the above? ");

            String exerciseName = sc.next().toLowerCase(Locale.ROOT);
            System.out.println("Hours: ");
            int exerciseHours = sc.nextInt();
            System.out.println("Minutes: ");
            int exerciseMinutes = sc.nextInt();

            Exercise exercise = new Exercise(exerciseName, exerciseHours, exerciseMinutes);
            workout.addExercise(exercise);
        }
    }

    private void askUserWeightGoal(User user) {
        System.out.println("What is your weight goal?" + " Your current weight is " + user.getWeight() + " kg.");
        double weightGoal = sc.nextDouble();
        user.updateWeightGoal(weightGoal);
    }

    private double calorieStatus(User user) {
        double caloriesConsumed = diet.totalCaloriesConsumed();
        double caloriesBurnt = workout.totalCaloriesBurnt();
        System.out.println("Total calories consumed: " + caloriesConsumed);
        System.out.println("Total calories burnt: " + caloriesBurnt);
        return diet.totalCaloriesConsumed() - workout.totalCaloriesBurnt() - user.getDailyMetabolism();
    }

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
