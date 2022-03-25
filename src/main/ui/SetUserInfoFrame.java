package ui;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the JFrame interface for creating a new user when they first start
// the application with no saved user data.
public class SetUserInfoFrame extends JFrame implements ActionListener {
    private final JTextField nameField = new JTextField("What is your name?");
    private final JTextField ageField = new JTextField("How old are you?");
    private final JTextField genderField = new JTextField("What is your biological gender? Answer with 'F' for female, 'M' for male");
    private final JTextField weightField = new JTextField("How much do you weigh in kg?");
    private final JTextField heightField = new JTextField("How tall are you in cm?");
    private final JButton confirm = new JButton("Confirm All Data");

    private User user;

    public SetUserInfoFrame(User user) {
        super("Creating New User");

        add(nameField);
        add(ageField);
        add(genderField);
        add(weightField);
        add(heightField);
        add(confirm);

        this.user = user;

        setSize(400,400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        pack();
        setVisible(true);

        confirm.setActionCommand("setUser");
        confirm.addActionListener(this);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // MODIFIES: user
    // EFFECTS: creates a new user based on the information that the user gives
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("setUser")) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getText();
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            user.setName(name);
            user.setAge(age);
            user.setGender(gender);
            user.setWeight(weight);
            user.setHeightInCm(height);

            dispose();
        }
    }
}
