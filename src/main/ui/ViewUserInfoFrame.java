package ui;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewUserInfoFrame extends JFrame implements ActionListener {
    private JLabel name;
    private JLabel age;
    private JLabel gender;
    private JLabel weight;
    private JLabel height;
    private JLabel bodyMassIndex;
    private JLabel dailyMetabolism;

    private JButton change;

    public ViewUserInfoFrame(User user) {
        super("User Info");

        name = new JLabel(user.getName());
        age = new JLabel(String.valueOf(user.getAge()));
        gender = new JLabel(user.getGender());
        weight = new JLabel(user.getWeight() + " kg");
        height = new JLabel(user.getHeightInCm() + " cm");
        user.calculateMetabolism();
        user.calculateBMI(user.getWeight());
        bodyMassIndex = new JLabel(String.valueOf(user.getBodyMassIndex()));
        dailyMetabolism = new JLabel(String.valueOf(user.getDailyMetabolism()));

        change = new JButton("Change User Info");
        change.addActionListener(this);

        setSize(400, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private class ChangeUserInfo extends JFrame {
        private final JTextField nameField = new JTextField("What is your name?");
        private final JTextField ageField = new JTextField("How old are you?");
        private final JTextField genderField = new JTextField("What is your biological gender? Answer with 'F' for female, 'M' for male");
        private final JTextField weightField = new JTextField("How much do you weigh in kg?");
        private final JTextField heightField = new JTextField("How tall are you in cm?");


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
