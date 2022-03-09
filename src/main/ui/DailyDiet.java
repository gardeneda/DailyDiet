package ui;

import exceptions.*;
import model.*;

import java.util.Objects;
import java.util.Scanner;

// Represents the UI interface that the users will be using to interact with the DailyDiet application.
// Made some references to the TellerApp to see how some things need to be implemented.

public class DailyDiet {
    private FoodList diet;
    private ExerciseList workout;
    private Scanner sc;
    private User user;
    private Day day;

    // EFFECTS: runs the DailyDiet application
    public DailyDiet() {
        runDailyDiet();
    }

    // MODIFIES: this
    // EFFECTS: takes in user input and converts them to usable data for
    //          the DailyDiet application to run.
    private void runDailyDiet() {
        // TODO: Need to completely re-haul this method here.
        boolean keepGoing = true;
        String command;


        try {
            initUtils();
            user = attainUserInfo();

            while (keepGoing) {
                displayMenu();
                command = sc.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    processCommands(command, user);
                }
            }
            System.out.println("Thanks for using DailyDiet!");
        } catch (InvalidInputException e) {
            System.out.println("You must enter as instructed.");
        } catch (NullGoalException e) {
            System.out.println("You have to enter a weight goal to see if you are meeting your daily expectations.");
        } catch (InvalidHoursException e) {
            //
        } catch (InvalidMinutesException e) {
            //
        }
    }

    // EFFECTS: displays the menu that the user can navigate through.
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tuser -> Display My Info (includes metabolism and BMI information");
        System.out.println("\tfood -> Insert Food into Today's Diet");
        System.out.println("\texer -> Insert Exercise into Today's Schedule");
        System.out.println("\tnewgoal -> Update Weight Goal");
        System.out.println("\tsummary -> Display total calories consumed and burnt, "
                + "food(s) eaten, and Exercise(s) done");
        System.out.println("\tsaveday -> Save the entire progress");
        System.out.println("\tselectday -> Select the date of your ");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes the commands the user inputs into the menu,
    //          the application will call forth methods according to the user's selection
    private void processCommands(String command, User user)
            throws NullGoalException, InvalidMinutesException, InvalidHoursException {
        switch (command) {
            case "user":
                displayUserInfo(user);
                displayUserBodyMassIndexAndMetabolism(user);
                break;
            case "food":
                insertFood();
                break;
            case "exer":
                insertExercise();
                break;
            case "newgoal":
                askUserWeightGoal(user);
                break;
            case "summary":
                showFood();
                showExercise();
                double sumCalories = calorieStatus(user);
                losingOrGainingWeight(user, sumCalories);
                break;
        }
    }

    // EFFECTS: initiates the different food list, exercise list and the scanner to allow users to
    //          interact with the terminal.
    private void initUtils() {
        diet = new FoodList();
        workout = new ExerciseList();
        sc = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: initializes the User with the user input given to cater to well-reflect the user.
    private User attainUserInfo() throws InvalidInputException {
        System.out.println("What is your name?: ");
        String name = sc.nextLine();

        System.out.println("How old are you?: ");
        int age = sc.nextInt();

        System.out.println("What is your biological gender? Answer with 'F' for female, 'M' for male: ");
        String gender = sc.next().toUpperCase();
        if (!gender.equals("M") && !gender.equals("F")) {
            throw new InvalidInputException();
        }

        System.out.println("How much do you weigh in kg?: ");
        double weight = sc.nextDouble();

        System.out.println("How tall are you in cm? (You can answer up to one decimal point: ");
        double height = sc.nextDouble();

        return new User(name, age, gender, weight, height);
    }

    // EFFECTS: Displays basic user information
    private void displayUserInfo(User user) {
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Weight: " + user.getWeight() + "kg");
        System.out.println("Height: " + user.getHeightInCm() + "cm");
    }

    // EFFECTS: tells the user of their BMI and their basic metabolism to give them an idea
    //          about their current state of their body and how much calories they are burning passively.
    private void displayUserBodyMassIndexAndMetabolism(User user) {
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
    private void showFood() {
        System.out.println("\n\nToday you ate these: ");
        for (Food f: diet.getList()) {
            System.out.println("\tName of Food: " + f.getName() + "  Calories: " + f.getCalories() + " kCal");
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the food that the user has eaten throughout their day
    //          and places them inside an ArrayList. The user will provide the
    //          parameters of the name of food they have eaten and its calories.
    private void insertFood() {
        while (true) {
            showFood();
            System.out.println("\nIf you need to add a food entry, type 'Y'. \n"
                    + "If you need to remove the last food entered, press 'R'.\n"
                    + "If you have no more entries, type 'N')");
            String userFoodResponse = sc.next().toUpperCase();

            if (Objects.equals(userFoodResponse, "Y")) {
                System.out.println("What foods have you eaten today (or as of now?)\nName of Food: ");
                String foodName = sc.next();

                System.out.println("Calorie of Food: ");
                double foodCalorie = sc.nextDouble();

                Food food = new Food(foodCalorie, foodName);
                diet.addFood(food);
                System.out.println("\n");
            } else if (Objects.equals(userFoodResponse, "R") && !(diet.isEmpty())) {
                diet.removeLastFood();
            } else if (userFoodResponse.equals("N")) {
                break;
            }
        }
    }

    // EFFECTS: prints out the exercises that the user has done throughout the day
    private void showExercise() {
        System.out.println("Currently you did these exercises: ");
        for (Exercise e: workout.getList()) {
            System.out.println("\tName of Exercise: " + e.getExerciseName()
                    + "  Burnt Calories: " + e.totalCaloriesBurnt() + "kCal");
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the exercise that done throughout their day and places them
    //          inside an ArrayList. There is a selection of which exercises the user has done
    //          and the user must choose one of the selections and provide how long they
    //          exercised for in hours and minutes.
    private void insertExercise() throws InvalidHoursException, InvalidMinutesException {
        while (true) {
            showExercise();
            System.out.println("\nIf you need to add an exercise entry, type 'Y'.\nIf you need to remove the last "
                    + "exercise entered, press 'R'.\nIf you have no more entries, type 'N'");
            String userExerciseResponse = sc.next().toUpperCase();
            if (Objects.equals(userExerciseResponse, "Y")) {
                System.out.println("What exercises did you do today? Choose an exercise from the list below");
                System.out.println("Running\nSwimming\nBicycling\nWeightlifting\nCalisthenics\nHiking");

                String exerciseName = sc.next().toLowerCase();
                int exerciseHours = insertExerciseHours();
                int exerciseMinutes = insertExerciseMinutes();

                Exercise exercise = new Exercise(exerciseName, exerciseHours, exerciseMinutes);
                workout.addExercise(exercise);
            } else if (Objects.equals(userExerciseResponse, "R") && !(diet.isEmpty())) {
                workout.removeLastExercise();
            } else if (userExerciseResponse.equals("N")) {
                break;
            }
        }
    }

    // EFFECTS: Enters the amount of hours the user had performed of the exercise.
    private int insertExerciseHours() throws InvalidHoursException {
        System.out.println("Hours: ");
        int exerciseHours = sc.nextInt();
        if (exerciseHours > 24 || exerciseHours < 0) {
            System.out.println("Hours cannot go beyond 24, or be negative!");
            throw new InvalidHoursException();
        }
        return exerciseHours;
    }

    // EFFECTS: Enters the amount of minutes the user had performed of the exercise.
    private int insertExerciseMinutes() throws InvalidMinutesException {
        System.out.println("Minutes: ");
        int exerciseMinutes = sc.nextInt();
        if (exerciseMinutes > 59 || exerciseMinutes < 0) {
            System.out.println("Minutes cannot go beyond 60 minutes, or be negative!");
            throw new InvalidMinutesException();
        }
        return exerciseMinutes;
    }

    // MODIFIES: this
    // EFFECTS: sets weight goal that the user wants to reach from their current weight.
    private void askUserWeightGoal(User user) {
        System.out.println("What is your weight goal?" + " Your current weight is " + user.getWeight() + " kg.");
        double weightGoal = sc.nextDouble();
        user.updateWeightGoal(weightGoal);
        if (user.getWeightGoal() <= 18.5) {
            System.out.println("You're aiming to be underweight.");
            System.out.println("We recommend against this weight goal, but you make your own decisions");
        } else if (user.getWeightGoal() <= 18.5 && user.getWeightGoal() > 24.99) {
            System.out.println("You're aiming to be obese.");
            System.out.println("We recommend against this weight goal, but you make your own decisions");
        } else {
            System.out.println("You've selected a healthy weight for your goal!");
            System.out.println("Keep up the good work, and I hope you make progress!");
        }
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
    //          and whether they are making progress in regard to their weight goal.
    private void losingOrGainingWeight(User user, double sumCalories) throws NullGoalException {
        System.out.println("\nYour total net calorie right now is: " + sumCalories + "Calories.");
        if (user.getWeightGoal() == 0) {
            throw new NullGoalException();
        }
        if (user.getWeightGoal() > user.getWeight()) {
            if (sumCalories > 0) {
                System.out.println("You're successfully gaining weight to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
                user.setAchievingWeightGoal(true);
            } else {
                System.out.println("You're losing weight right now, " + "as you are consuming too much calories.");
                user.setAchievingWeightGoal(false);
            }
        } else {
            if (sumCalories > 0) {
                System.out.println("You're gaining weight right now!");
                user.setAchievingWeightGoal(false);
            } else {
                System.out.println("You're losing weight right now to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
                user.setAchievingWeightGoal(true);
            }
        }
    }
}
