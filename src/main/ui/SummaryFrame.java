package ui;

import exceptions.NullGoalException;
import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SummaryFrame extends JFrame {
    private JLabel sumCalories;
    private JLabel losingOrGainingWeight;

    private JTable allExercises;
    private ExerciseList workout;

    private JTable allFoods;
    private FoodList diet;

    private User user;

    public SummaryFrame(ExerciseList workout, FoodList diet, User user) {
        super("Summary");
        this.workout = workout;
        this.diet = diet;
        this.user = user;

        initializeComponents();
        exerciseTableSetup();
        foodTableSetup();
        addComponents();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setSize(400, 400);
        pack();
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        double caloriesConsumed = diet.totalCaloriesConsumed();
        double caloriesBurnt = workout.totalCaloriesBurnt();
        double totalCalories = caloriesConsumed - caloriesBurnt - user.getDailyMetabolism();
        sumCalories = new JLabel("Total Calories: " + totalCalories);

        losingOrGainingWeight(totalCalories);

    }

    // EFFECTS: tells the user their net calories and tell them whether they are losing or gaining weight
    //          and whether they are making progress in regard to their weight goal.
    private void losingOrGainingWeight(double sumCalories) {
        if (user.getWeightGoal() == 0) {
            losingOrGainingWeight = new JLabel("No Weight Goal");
        }
        if (user.getWeightGoal() > user.getWeight()) {
            if (sumCalories > 0) {
                losingOrGainingWeight = new JLabel("You're successfully gaining weight to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
                user.setAchievingWeightGoal(true);
            } else {
                losingOrGainingWeight = new JLabel("You're losing weight right now, " + "as you are consuming too much calories.");
                user.setAchievingWeightGoal(false);
            }
        } else {
            if (sumCalories > 0) {
                losingOrGainingWeight = new JLabel("You're gaining weight right now!");
                user.setAchievingWeightGoal(false);
            } else {
                losingOrGainingWeight = new JLabel("You're losing weight right now to achieve your weight goal of "
                        + user.getWeightGoal() + "kg!");
                user.setAchievingWeightGoal(true);
            }
        }
    }

    private void addComponents() {
        JScrollPane scrollPaneExercises = new JScrollPane(allExercises);
        add(scrollPaneExercises, BorderLayout.CENTER);

        JScrollPane scrollPaneFoods = new JScrollPane(allFoods);
        add(scrollPaneFoods, BorderLayout.CENTER);

        add(sumCalories);
        add(losingOrGainingWeight);
    }

    private void exerciseTableSetup() {
        Vector<Vector> rowData = new Vector<Vector>();
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Exercise Name");
        columnNames.addElement("Calories Burnt");

        for (Exercise e : workout.getList()) {
            Vector<String> newRow = new Vector<String>();
            newRow.addElement(e.getExerciseName());
            newRow.addElement(Double.toString(e.totalCaloriesBurnt()));
            rowData.addElement(newRow);
        }

        allExercises = new JTable(rowData, columnNames);
    }

    private void foodTableSetup() {
        Vector<Vector> rowData = new Vector<Vector>();
        Vector<String> columnNames = new Vector<String>();
        columnNames.addElement("Food Name");
        columnNames.addElement("Calories");

        for (Food f : diet.getList()) {
            Vector<String> newRow = new Vector<String>();
            newRow.addElement(f.getName());
            newRow.addElement(Double.toString(f.getCalories()));
            rowData.addElement(newRow);
        }

        allFoods = new JTable(rowData, columnNames);
    }
}
