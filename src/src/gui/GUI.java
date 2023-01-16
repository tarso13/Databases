package gui;

import classes.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI {
    private void showResultsEmployees(ArrayList<Employee> Employees, JFrame frame) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);
        String employeeEntries = "";
        for (int i = 0; i < Employees.size(); ++i)
            employeeEntries += (Employees.get(i).getFirstName() + " " +
                    Employees.get(i).getLastName() + " " + Employees.get(i).getAddress() + " " + Employees.get(i).getPhoneNumber() + " " + Employees.get(i).getBeginHiringDate() +
                    " " + Employees.get(i).getEmployeeId() + "\n");
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
        String salaries = "";
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

    private static void displayProcedures(JFrame proceduresFrame, boolean root) {
        JPanel panel;
        JButton[] buttons = new JButton[7];
        panel = new JPanel(new GridLayout(7, 1));
        AtomicBoolean should_dispose = new AtomicBoolean(true);
        String b[] = {"New Hire", "New Fire/Retirement", "Change Salary/Bonuses", "Payments", "Change Employee Info", "Queries", "Back to Login Page"};
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
                        should_dispose.set(true);
                        break;
                    case "Back to Login Page":
                        proceduresFrame.dispose();
                        should_dispose.set(true);
                        loginPage();
                        break;
                    case "New Hire":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        displayHire();
                        break;
                    case "Payments":
                        if (!root) {
                            should_dispose.set(false);
                            // do what is needed
                            break;
                        }
                        should_dispose.set(true);
                        // do what is needed
                        break;
                    case "Change Employee Info":
                        should_dispose.set(true);
                        displayChangeInfo();
                        break;
                    case "Change Salary/Bonuses":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        displayChangeSalaryBonuses();
                        break;
                    case "New Fire/Retirement":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        displayFireRetire();
                        break;
                    default:
                        assert (false);
                }
                if (should_dispose.get() == true)
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
        JTextField password = new JTextField();
        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login Page", JOptionPane.OK_CANCEL_OPTION);

        JFrame proceduresFrame = new JFrame("Procedures");
        if (option == JOptionPane.OK_OPTION) {
            //change correct credentials
            if (username.getText().equals("root") && password.getText().equals("root"))
                displayProcedures(proceduresFrame, true);
            else {
                if (false /* INCORRECT LOGIN*/) {
                    JOptionPane.showMessageDialog(null, "Incorrect login");
                    loginPage();
                }
                displayProcedures(proceduresFrame, false);
            }
        } else
            JOptionPane.showMessageDialog(null, "Login cancelled");
    }

    public static void displayHire() {
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField address = new JTextField();
        JTextField groupDepartment = new JTextField();
        JTextField jobDepartment = new JTextField();
        JTextField married = new JTextField();
        JTextField kids = new JTextField();
        JTextField kidsAgesWithCommas = new JTextField();
        Object[] message = {
                "First Name:", firstName,
                "Last Name:", lastName,
                "Address: ", address,
                "Group Department: ", groupDepartment,
                "Job Department: ", jobDepartment,
                "Married: ", married,
                "Kids: ", kids,
                "Kids' Ages (With Commas!)", kidsAgesWithCommas
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Hire", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // firstName.getText() etc get your info and call hire Konstantina
        } else {
            loginPage();
        }

    }

    public static void displayFireRetire() throws NumberFormatException {
        JTextField employeeId = new JTextField();
        JTextField fireRetire = new JTextField();
        Object[] message = {
                "Give me the employee id to fire:", employeeId,
                "Fire/Retire: ", fireRetire
        };
        int option = JOptionPane.showConfirmDialog(null, message, "Fire", JOptionPane.OK_CANCEL_OPTION);
        int givenEmployeeId = Integer.parseInt(employeeId.getText());
        if (givenEmployeeId < 0) {
            JOptionPane.showMessageDialog(null, "Incorrect Employee Id");
            return;
        }
        if (option == JOptionPane.OK_OPTION) {
            //call fire request Marilena or retire
            // !! fireRetire.getText() should be either "Fire" or "Retire"
        } else {
            loginPage();
        }

    }

    public static void displayChangeInfo() {
        JTextField searchBonus = new JTextField();
        JTextField libraryBonus = new JTextField();
        JTextField basicSalary = new JTextField();
        JTextField contractSalary = new JTextField();
        JTextField address = new JTextField();
        JTextField currentDate = new JTextField();
        JTextField married = new JTextField();
        JTextField kids = new JTextField();
        JTextField kidsAgesWithCommas = new JTextField();
        Object[] message = {
                "First Name:", searchBonus,
                "Last Name:", libraryBonus,
                "Group Department: ", basicSalary,
                "Job Department: ", contractSalary,
                "Address: ", address,
                "Current Date (Payments): ", currentDate,
                "Married: ", married,
                "Kids: ", kids,
                "Kids' Ages (With Commas!)", kidsAgesWithCommas
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // firstName.getText() etc get your info
        } else {
            loginPage();
        }

    }

    public static void displayChangeSalaryBonuses() {
        JTextField searchBonus = new JTextField();
        JTextField libraryBonus = new JTextField();
        JTextField basicSalary = new JTextField();
        JTextField contractSalary = new JTextField();
        JTextField employeeId = new JTextField();
        Object[] message = {
                "First Name:", searchBonus,
                "Last Name:", libraryBonus,
                "Group Department: ", basicSalary,
                "Job Department: ", contractSalary,
                "Employee Id: ", employeeId,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // firstName.getText() etc get your info
        } else {
            loginPage();
        }

    }
}