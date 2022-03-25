package ui;

import model.Food;
import model.FoodList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

// Represents the JFrame interface for collecting data of the foods that the
// user has eaten throughout the day
public class InsertFoodFrame extends JFrame implements ActionListener {
    private JTextField foodName;
    private JTextField foodCalorie;
    private JTable allFoods;
    private JButton removeFood;
    private JButton addFood;

    private FoodList diet;

    public InsertFoodFrame(FoodList diet) {
        super("Insert Food Eaten Today");
        this.diet = diet;

        initializeComponents();
        foodTableSetup();
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
        foodName = new JTextField("What food did you eat?");
        foodCalorie = new JTextField("Calorie of Food: ");

        removeFood = new JButton("Remove Recently Added Food");
        removeFood.setActionCommand("remove");
        removeFood.addActionListener(this);

        addFood = new JButton("Add Food Eaten to the List");
        addFood.setActionCommand("add");
        addFood.addActionListener(this);
    }

    // EFFECTS: sets up a JTable for the foods listed inside the FoodList
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

    // MODIFIES: this
    // EFFECTS: adds Java Swing components to the container
    private void addComponents() {
        JScrollPane scrollPane = new JScrollPane(allFoods);
        add(scrollPane, BorderLayout.CENTER);

        add(foodName);
        add(foodCalorie);
        add(addFood);
        add(removeFood);
    }

    // MODIFIES: diet
    // EFFECTS: adds or removes Food object from the diet FoodList
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            String name = foodName.getText();
            int calorie = Integer.parseInt(foodCalorie.getText());

            Food food = new Food(calorie, name);
            diet.addFood(food);
            dispose();
            new InsertFoodFrame(diet);
        } else if (e.getActionCommand().equals("remove")) {
            diet.removeLastFood();
            dispose();
            new InsertFoodFrame(diet);
        }
    }
}
