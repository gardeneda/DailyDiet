package ui;

import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private User user;

    private static final JButton change = new JButton("Change User Info");

    public ViewUserInfoFrame(User user) {
        super("User Info");

        this.user = user;
        initializeComponents();

        change.setActionCommand("change");
        change.addActionListener(this);

        addComponents();

        setSize(400, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initializeComponents() {
        name = new JLabel(user.getName());
        age = new JLabel(String.valueOf(user.getAge()));
        gender = new JLabel(user.getGender());
        weight = new JLabel(user.getWeight() + " kg");
        height = new JLabel(user.getHeightInCm() + " cm");
        user.calculateMetabolism();
        double bodyMassIndexValue = user.calculateBMI(user.getWeight());
        bodyMassIndexValue = Math.round(user.getBodyMassIndex() * 100.0 / 100.0);
        bodyMassIndex = new JLabel("BMI: " + bodyMassIndexValue);
        dailyMetabolism = new JLabel("Daily Metabolism: " + user.getDailyMetabolism() + " Cal");
    }

    private void addComponents() {
        add(name);
        add(age);
        add(gender);
        add(weight);
        add(height);
        add(bodyMassIndex);
        add(dailyMetabolism);
        add(change);
    }

    private class ChangeUserInfo extends JFrame implements ActionListener {
        private final JTextField ageField = new JTextField("Re-Enter Age");
        private final JTextField weightField = new JTextField("Re-Enter Weight");
        private final JTextField heightField = new JTextField("Re-Enter Height");
        private final JTextField weightGoalField = new JTextField("Re-Enter Weight Goal");
        private final JButton changeButton = new JButton("Confirm Change");
        private final JButton quit = new JButton("Cancel Change");

        private User user;

        private ChangeUserInfo(User user) {
            super("Change User Info");
            this.user = user;

            add(ageField);
            ageField.addActionListener(this);

            add(weightField);
            weightField.addActionListener(this);

            add(heightField);
            heightField.addActionListener(this);

            add(weightGoalField);
            weightGoalField.addActionListener(this);

            add(changeButton);
            changeButton.setActionCommand("change");
            changeButton.addActionListener(this);

            add(quit);
            quit.setActionCommand("quit");
            quit.addActionListener(this);

            setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            pack();
            setVisible(true);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("change")) {
                int age = Integer.parseInt(ageField.getText());
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                double weightGoal = Double.parseDouble(weightGoalField.getText());

                user.setAge(age);
                user.setWeight(weight);
                user.setHeightInCm(height);
                user.setWeightGoal(weightGoal);

                dispose();
            } else if (e.getActionCommand().equals("quit")) {
                dispose();
            }
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("change")) {
            new ChangeUserInfo(user);
        }
    }
}
