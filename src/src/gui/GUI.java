package gui;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private static void displayQueries(JFrame queriesFrame) {
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
                switch (choice) {
                    case "Back to Login Page":
                        loginPage();
                        break;
                    case "Payment State per Staff Category":
                        break;
                    case "Max Salary per Staff Category":
                        break;
                    case "Min Salary per Staff Category":
                        break;
                    case "Average Salary per Staff Category":
                        break;
                    case "Average Salary and Bonus Increase":
                        break;
                    case "Employee Data and Salary":
                        break;
                    case "Total Salary Increase per Staff Category":
                        break;
                    default:
                        assert (false);
                }
                queriesFrame.dispose();
            });
            panel.add(buttons[i]);
        }

        queriesFrame.add(panel);
        queriesFrame.pack();
        queriesFrame.setVisible(true);
        queriesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void displayProcedures(JFrame proceduresFrame) {
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
                switch (choice) {
                    case "Queries":
                        JFrame queriesFrame = new JFrame("Queries Supported");
                        displayQueries(queriesFrame);
                        break;
                    case "Back to Login Page":
                        proceduresFrame.dispose();
                        loginPage();
                        break;
                    case "New Hire":
                        break;
                    case "New Contract":
                        break;
                    case "Change Employee Info":
                        break;
                    case "Change Salary/Bonus":
                        break;
                    case "New Fire/Retirement":
                        break;
                    case "Payment":
                        break;
                    case "Statistics":
                        break;
                    default:
                        assert (false);
                }
                proceduresFrame.dispose();
            });
            panel.add(buttons[i]);
        }

        proceduresFrame.add(panel);
        proceduresFrame.pack();
        proceduresFrame.setVisible(true);
        proceduresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                JFrame proceduresFrame = new JFrame("Procedures");
                displayProcedures(proceduresFrame);
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect login");
                loginPage();
            }
        } else
            JOptionPane.showMessageDialog(null, "Login cancelled");
    }
}