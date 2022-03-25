package ui;

import model.FoodList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertFoodFrame extends JFrame implements ActionListener {
    private JTextField foodName;
    private JTextField foodCalorie;

    public InsertFoodFrame(FoodList diet) {
        super("Insert Food Eaten Today");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
