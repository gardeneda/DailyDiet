package ui;

import model.*;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    JButton quit = new JButton("quit");

    public DailyDietGUI() {
        super("DailyDiet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400, 400);
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
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


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "userInfo":
                new ViewUserInfoFrame(user);
                break;
            case "insertExercise":
                break;
        }
    }
}
