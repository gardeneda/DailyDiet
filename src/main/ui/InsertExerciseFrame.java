package ui;

import model.ExerciseList;
import model.Exercise;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Objects;
import java.util.Vector;

// Represents the JFrame interface for adding and removing exercises from the day's list.
public class InsertExerciseFrame extends JFrame implements ActionListener {
    private JComboBox<String> exerciseName;
    private JTextField exerciseHour;
    private JTextField exerciseMinutes;

    private JTable allExercises;
    private JButton removeExercise;
    private JButton addExercise;

    private ExerciseList workout;

    public InsertExerciseFrame(ExerciseList workout) {
        super("Insert Exercise Performed Today");
        this.workout = workout;

        initializeComponents();
        exerciseTableSetup();
        addComponents();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setSize(400, 400);
        pack();
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // EFFECTS: initializes the components that will be placed on this JFrame
    private void initializeComponents() {
        exerciseName = new JComboBox<>();
        exerciseName.addItem("Running");
        exerciseName.addItem("Swimming");
        exerciseName.addItem("Bicycling");
        exerciseName.addItem("Weightlifting");
        exerciseName.addItem("Calisthenics");
        exerciseName.addItem("Hiking");

        exerciseHour = new JTextField("Hours");
        exerciseMinutes = new JTextField("Minutes");

        removeExercise = new JButton("Remove Recently Added Exercise");
        removeExercise.setActionCommand("remove");
        removeExercise.addActionListener(this);

        addExercise = new JButton("Add Exercise to the List");
        addExercise.setActionCommand("add");
        addExercise.addActionListener(this);
    }

    // EFFECTS: sets up a JTable for the exercises listed inside the ExerciseList
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

    // MODIFIES: this
    // EFFECTS: adds Java Swing components to the container
    private void addComponents() {
        JScrollPane scrollPane = new JScrollPane(allExercises);
        add(scrollPane, BorderLayout.CENTER);

        add(exerciseName);
        add(exerciseHour);
        add(exerciseMinutes);
        add(addExercise);
        add(removeExercise);
    }

    // MODIFIES: workout
    // EFFECTS: adds or removes Exercise object from the workout ExerciseList
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            String name = Objects.requireNonNull(exerciseName.getSelectedItem()).toString();
            name = name.toLowerCase(Locale.ROOT);
            int totalHours = Integer.parseInt(exerciseHour.getText());
            int totalMinutes = Integer.parseInt(exerciseMinutes.getText());


            Exercise exercise = new Exercise(name, totalHours, totalMinutes);
            workout.addExercise(exercise);
            dispose();
            new InsertExerciseFrame(workout);
        } else if (e.getActionCommand().equals("remove")) {
            workout.removeLastExercise();
            dispose();
            new InsertExerciseFrame(workout);
        }
    }
}
