package gui;

import classes.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
    private void showResultsEmployees(ArrayList<Employee> Employees, JFrame frame) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);
        String employeeEntries="";
        for (int i = 0; i < Employees.size(); ++i)
            employeeEntries += (Employees.get(i).getFirstName() + " " +
                    Employees.get(i).getLastName()+ " " + Employees.get(i).getAddress() + " " + Employees.get(i).getPhoneNumber() + " " + Employees.get(i).getBeginHiringDate() +
                    " " + Employees.get(i).getEmployeeId() +"\n");
        int n = JOptionPane.showOptionDialog(frame, employeeEntries,
                "Results",
                JOptionPane.OK_OPTION,
                0,
                null,
                options,
                options[0]);

        JFrame finalFrame = frame;
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalFrame.setVisible(false);
                finalFrame.dispose();
            }
        });
    }

    private void showResultsSalaries(ArrayList<Integer> Salaries, JFrame frame) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);
        String salaries="";
        for (int i = 0; i < Salaries.size(); ++i)
            salaries += (Salaries.get(i).toString() + "\n");
        int n = JOptionPane.showOptionDialog(frame, salaries,
                "Results",
                JOptionPane.OK_OPTION,
                0,
                null,
                options,
                options[0]);

        JFrame finalFrame = frame;
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalFrame.setVisible(false);
                finalFrame.dispose();
            }
        });
    }

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