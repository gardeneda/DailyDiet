package ui;

import exceptions.*;
import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.time.LocalDate;

// Represents the UI interface that the users will be using to interact with the DailyDiet application.
// Made some references to the TellerApp to see how some things need to be implemented.

public class DailyDietApp {
    private static final String userJsonStore = "./data/user.json";
    private static final String dayJsonStore = "./data/day.json";


    private FoodList diet;
    private ExerciseList workout;
    private Scanner sc;
    private User user;
    private Day day;
    private JsonReader reader;
    private JsonWriter writer;
    private String date;
    private List<Day> dayList;

    // EFFECTS: runs the DailyDiet application
    public DailyDietApp() {
        runDailyDiet();
    }

    // MODIFIES: this
    // EFFECTS: takes in user input and converts them to usable data for
    //          the DailyDiet application to run.
    private void runDailyDiet() {
        boolean keepGoing = true;
        String command;
        try {
            initProgram();

            while (keepGoing) {
                displayMenu();
                command = sc.next();
                command = command.toLowerCase();

                if (command.equals("q")) {
                    keepGoing = false;
                    finalizeDay();
                } else {
                    processCommands(command, user);
                }
            }
            System.out.println("Thank you for using DailyDiet!");
        } catch (NullGoalException e) {
            System.out.println("You have to enter a weight goal to see if you are meeting your daily expectations.");
        } catch (InvalidInputException e) {
            System.out.println("You must enter as instructed.");
        } catch (InvalidTimeException e) {
            //
        }
    }

    // EFFECTS: displays the menu that the user can navigate through.
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tuser -> Display My Info (includes metabolism and BMI information)");
        System.out.println("\tfood -> Insert Food into Today's Diet");
        System.out.println("\texer -> Insert Exercise into Today's Schedule");
        System.out.println("\tnewgoal -> Update Weight Goal");
        System.out.println("\tsummary -> Display total calories consumed and burnt, "
                + "food(s) eaten, and Exercise(s) done");
        System.out.println("\tsave -> Save current food and workouts for the day");
        System.out.println("\tload -> Load previously saved days back to the application.");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: processes the commands the user inputs into the menu,
    //          the application will call forth methods according to the user's selection
    private void processCommands(String command, User user)
            throws NullGoalException, InvalidTimeException {
        switch (command) {
            case "user":
                userDataControl();
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
                summaryOfDay();
                break;
            case "save":
                save();
                break;
            case "load":
                load();
                break;
        }
    }

    // EFFECTS: initializes all the necessary variables for the program to run.
    private void initProgram() throws InvalidInputException {
        initUtils();
        initUser();
    }

    // EFFECTS: initiates the different food list, exercise list and the scanner to allow users to
    //          interact with the terminal.
    private void initUtils() {
        diet = new FoodList();
        workout = new ExerciseList();
        sc = new Scanner(System.in);
        LocalDate nowDate = LocalDate.now();
        date = nowDate.format(DateTimeFormatter.ISO_DATE);
        dayList = new ArrayList<>();
    }

    // EFFECTS: initializes user profile if it exists, or makes the user create a new one if it does not exist.
    private void initUser() throws InvalidInputException {
        try {
            loadUser();
            updateUserBodyMassIndexAndMetabolism(user);
        } catch (NullPointerException | JSONException e) {
            user = attainUserInfo();
            updateUserBodyMassIndexAndMetabolism(user);
        }
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

    // EFFECTS: displays basic user information including BMI and daily metabolism
    //          and also asks if they want to change their user information
    private void userDataControl() {
        displayUserInfo(user);
        displayUserBodyMassIndexAndMetabolism(user);
        changeUserInfo(user);
    }

    // EFFECTS: shows the summary of the food eaten, exercise done
    //          as well as the total calories burnt and consumed.
    //          Also tells the user whether they are losing or gaining weight.
    private void summaryOfDay() throws NullGoalException {
        showFood(diet);
        showExercise(workout);
        double sumCalories = calorieStatus(user);
        losingOrGainingWeight(user, sumCalories);
    }

    // EFFECTS: Displays basic user information
    private void displayUserInfo(User user) {
        System.out.println("Name: " + user.getName());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender());
        System.out.println("Weight: " + user.getWeight() + "kg");
        System.out.println("Height: " + user.getHeightInCm() + "cm");
    }

    // MODIFIES: user
    // EFFECTS: updates user's BMI and daily metabolism
    private void updateUserBodyMassIndexAndMetabolism(User user) {
        double userMetabolism = user.calculateMetabolism();
        double userBMI = user.calculateBMI(user.getWeight());
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
    private void showFood(FoodList diet) {
        System.out.println("\n\nToday you ate these: ");
        for (Food f : diet.getList()) {
            System.out.println("\tName of Food: " + f.getName() + "  Calories: " + f.getCalories() + " kCal");
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the food that the user has eaten throughout their day
    //          and places them inside an ArrayList. The user will provide the
    //          parameters of the name of food they have eaten and its calories.
    private void insertFood() {
        while (true) {
            showFood(diet);
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
    private void showExercise(ExerciseList workout) {
        System.out.println("Currently you did these exercises: ");
        for (Exercise e : workout.getList()) {
            System.out.println("\tName of Exercise: " + e.getExerciseName()
                    + "  Burnt Calories: " + e.totalCaloriesBurnt() + "kCal");
        }
    }

    // MODIFIES: this
    // EFFECTS: registers the exercise that done throughout their day and places them
    //          inside an ArrayList. There is a selection of which exercises the user has done
    //          and the user must choose one of the selections and provide how long they
    //          exercised for in hours and minutes.
    private void insertExercise() throws InvalidTimeException {
        while (true) {
            showExercise(workout);
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
    private int insertExerciseHours() throws InvalidTimeException {
        System.out.println("Hours: ");
        int exerciseHours = sc.nextInt();
        if (exerciseHours > 24 || exerciseHours < 0) {
            System.out.println("Hours cannot go beyond 24, or be negative!");
            throw new InvalidTimeException();
        }
        return exerciseHours;
    }

    // EFFECTS: Enters the amount of minutes the user had performed of the exercise.
    private int insertExerciseMinutes() throws InvalidTimeException {
        System.out.println("Minutes: ");
        int exerciseMinutes = sc.nextInt();
        if (exerciseMinutes > 59 || exerciseMinutes < 0) {
            System.out.println("Minutes cannot go beyond 60 minutes, or be negative!");
            throw new InvalidTimeException();
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

    // MODIFIES: user
    // EFFECTS: allows the user to change any information about themselves.
    private void changeUserInfo(User user) {
        if (isUserChangeInfo()) {
            processChangingUserInfo(user, showOptionsChangingUserInfo());
        }
    }

    // EFFECTS: asks the user if they want to change their information
    //          and returns a boolean accordingly to their answer
    private boolean isUserChangeInfo() {
        System.out.println("\n\nChange user info?");
        System.out.println("\tyes");
        System.out.println("\tno");

        String input = sc.next().toLowerCase();
        return input.equals("yes");
    }

    // EFFECTS: asks the user which information they want to change
    //          and returns a string with that corresponding answer
    private String showOptionsChangingUserInfo() {

        System.out.println("\nSelect information that you want to change:");
        System.out.println("\tage");
        System.out.println("\tweight");
        System.out.println("\theight");
        System.out.println("\tweightGoal");
        System.out.println("\tq -> quit");

        String input = sc.next();
        return input.toLowerCase();
    }

    // MODIFIES: user
    // EFFECTS: changes the information that the user wants to change
    //          with the user input
    private void processChangingUserInfo(User user, String input) {
        switch (input) {
            case "age":
                int age = sc.nextInt();
                user.setAge(age);
                updateUserBodyMassIndexAndMetabolism(user);
                break;
            case "weight":
                double weight = sc.nextDouble();
                user.setWeight(weight);
                updateUserBodyMassIndexAndMetabolism(user);
                break;
            case "height":
                double height = sc.nextDouble();
                user.setHeightInCm(height);
                updateUserBodyMassIndexAndMetabolism(user);
                break;
            case "weightGoal":
                double weightGoal = sc.nextDouble();
                user.setWeightGoal(weightGoal);
                break;
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

    // EFFECTS: saves all data necessary to run the program,
    //          (user data, and day data)
    private void save() {
        saveDay();
        saveUser();
    }

    // EFFECTS : saves the exercise done and food eaten as a day
    private void saveDay() {
        try {
            writer = new JsonWriter(dayJsonStore);
            writer.open();
            day = new Day(date, workout, diet);
            writer.write(day);
            writer.close();
            System.out.println("Data saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file.");
        }
    }

    // EFFECTS: saves the user data to a local file
    private void saveUser() {
        try {
            writer = new JsonWriter(userJsonStore);
            writer.open();
            writer.write(user);
            writer.close();
            System.out.println("User data saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: retrieves user data and the list of days saved.
    private void load() {
        try {
            loadDay();
        } catch (JSONException e) {
            System.out.println("There is no saved data to load!");
        }
    }

    // MODIFIES: this
    // EFFECTS: retrieves days saved in local data file
    private void loadDay() {
        try {
            reader = new JsonReader(dayJsonStore);
            day = reader.dayRead();
            workout = day.getExerciseList();
            diet = day.getFoodList();
        } catch (IOException e) {
            System.out.println("Unable to read from file");
        } catch (NullPointerException e) {
            System.out.println("You did not enter any information for the day!");
        }
    }

    // MODIFIES: this
    // EFFECTS: retrieves user information saved in local data file
    private void loadUser() {
        try {
            reader = new JsonReader(userJsonStore);
            user = reader.userRead();
            System.out.println("User data loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: wipes the stored JSON file for the day if the user states that their day is finished
    private void finalizeDay() {
        try {
            wipeDay(isDayFinished());
        } catch (FileNotFoundException e) {
            System.out.println("The data path is not found!");
        }
    }

    // EFFECTS: asks user if their day is complete
    private boolean isDayFinished() {
        System.out.println("\nIs your day complete?");
        System.out.println("\n\nIf so, type 'yes' to wipe all saved data.");
        System.out.println("Otherwise, type 'no' to save current data.");

        String input = sc.next();
        return input.equals("yes");
    }

    // MODIFIES: this
    // EFFECTS: wipes the stored JSON file for day; dayJsonStore if given boolean is true
    private void wipeDay(boolean isDayFinished) throws FileNotFoundException {
        if (isDayFinished) {
            new PrintWriter(dayJsonStore).close();
        }
    }
}