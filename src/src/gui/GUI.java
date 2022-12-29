package gui;

import javax.swing.*;
import java.awt.*;

import static java.lang.System.exit;

public class GUI {

    private static void displayQueries() {
        JFrame frame = new JFrame("Queries Supported");
        JPanel panel;
        JButton[] buttons = new JButton[8];
        panel = new JPanel(new GridLayout(8, 1));
        String b[] = {"Payment State per Staff Category", "Max Salary per Staff Category", "Min Salary per Staff Category",
                "Average Salary per Staff Category", "Average Salary and Bonus Increase", "Employee Data and Salary", "Total Salary Increase per Staff Category", "Back to Login Page"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                if (choice.equals("Back to Login Page"))
                    loginPage();
                frame.dispose();
            });
            panel.add(buttons[i]);
        }
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void displayProcedures() {
        JFrame frame = new JFrame("Procedures");
        JPanel panel;
        JButton[] buttons = new JButton[9];
        panel = new JPanel(new GridLayout(9, 1));
        String b[] = {"New Hire", "New Contract", "Change Employee Info", "Change Salary/Bonus", "New Fire/Retirement", "Payment", "Queries", "Statistics", "Back to Login Page"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                if (choice.equals("Queries")) {
                    displayQueries();
                } else if (choice.equals("Back to Login Page"))
                    loginPage();
                frame.dispose();
            });
            panel.add(buttons[i]);
        }

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void loginPage() {
        JTextField username = new JTextField();
        JTextField password = new JPasswordField();
        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login Page", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            //change correct credentials
            if (username.getText().equals("root") && password.getText().equals("root")) {
                displayProcedures();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect login");
                loginPage();
            }
        } else
            JOptionPane.showMessageDialog(null, "Login cancelled");

    }
}
