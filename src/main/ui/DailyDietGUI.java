package ui;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// SOURCE: https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
public class DailyDietGUI extends JFrame implements ActionListener {
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

    JButton userInfo = new JButton("View User Info");
    JButton insertFood = new JButton("Add or Remove Food");
    JButton insertExercise = new JButton("Add or Remove Exercise");
    JButton setWeightGoal = new JButton("Set a New Weight Goal");
    JButton summary = new JButton("Summary of Total Calorie Consumption / Burnt");
    JButton save = new JButton("Save");
    JButton load = new JButton("Load");
    JButton quit = new JButton("Quit");

    public DailyDietGUI() {
        super("DailyDiet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        add(userInfo);
        add(insertFood);
        add(insertExercise);
        add(setWeightGoal);
        add(summary);
        add(save);
        add(load);
        add(quit);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setVisible(true);

        runDailyDietGUI();
    }

    public void runDailyDietGUI() {
        initProgram();
    }

    private void initProgram() {
        initUtils();
        initUser();
    }

    // EFFECTS: initiates the different food list, exercise list and the scanner to allow users to
    //          interact with the terminal.
    private void initUtils() {
        diet = new FoodList();
        workout = new ExerciseList();
        LocalDate nowDate = LocalDate.now();
        date = nowDate.format(DateTimeFormatter.ISO_DATE);
        dayList = new ArrayList<>();

        initButtons();
    }

    private void initButtons() {
        userInfo.setActionCommand("userInfo");
        userInfo.addActionListener(this);

        insertExercise.setActionCommand("insertExercise");
        insertExercise.addActionListener(this);

        insertFood.setActionCommand("insertFood");
        insertFood.addActionListener(this);

        setWeightGoal.setActionCommand("setWeightGoal");
        setWeightGoal.addActionListener(this);

        summary.setActionCommand("summary");
        summary.addActionListener(this);

        save.setActionCommand("save");
        save.addActionListener(this);

        load.setActionCommand("load");
        load.addActionListener(this);

        quit.setActionCommand("quit");
        quit.addActionListener(this);
    }

    // EFFECTS: initializes user profile if it exists, or makes the user create a new one if it does not exist.
    private void initUser() {
        try {
            loadUser();
            // updateUserBodyMassIndexAndMetabolism(user);
        } catch (NullPointerException | JSONException e) {
            user = new User("none", 0, "none", 0, 0);
            new SetUserInfoFrame(user);
            // updateUserBodyMassIndexAndMetabolism(user);
        }
    }

    private void loadUser() {
        try {
            reader = new JsonReader(userJsonStore);
            user = reader.userRead();
            System.out.println("User data loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: user
    // EFFECTS: updates user's BMI and daily metabolism
    private void updateUserBodyMassIndexAndMetabolism(User user) {
        double userMetabolism = user.calculateMetabolism();
        double userBMI = user.calculateBMI(user.getWeight());
    }

    // EFFECTS: saves all data necessary to run the program,
    //          (user data, and day data)
    private void save() {
        saveDay();
        saveUser();
        JOptionPane.showMessageDialog(this, "Data Successfully Saved!");
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
            JOptionPane.showMessageDialog(this, "Data Successfully Loaded!");
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
    // EFFECTS: wipes the stored JSON file for the day if the user states that their day is finished
    private void finalizeDay() {
        try {
            wipeDay(isDayFinished());
        } catch (FileNotFoundException e) {
            System.out.println("The data path is not found!");
        }
    }

    // EFFECTS: asks user if their day is complete
    // SOURCE: https://gist.github.com/zachomedia/4365663
    private boolean isDayFinished() {
        int returnValue;
        returnValue = JOptionPane.showConfirmDialog(null,
                "Is your day complete? Press NO if you are coming back to add more.",
                "Are you sure?", JOptionPane.YES_NO_OPTION);

        return returnValue == JOptionPane.YES_OPTION;
    }

    // MODIFIES: this
    // EFFECTS: wipes the stored JSON file for day; dayJsonStore if given boolean is true
    private void wipeDay(boolean isDayFinished) throws FileNotFoundException {
        if (isDayFinished) {
            new PrintWriter(dayJsonStore).close();
        }
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "userInfo":
                new ViewUserInfoFrame(user);
                break;
            case "insertFood":
                new InsertFoodFrame(diet);
                break;
            case "insertExercise":
                new InsertExerciseFrame(workout);
                break;
            case "save":
                save();
                break;
            case "summary":
                new SummaryFrame(workout, diet, user);
                break;
            case "load":
                load();
                break;
            case "quit":
                finalizeDay();
        }
    }
}
