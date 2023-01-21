package src.gui;

import jdk.jfr.Category;
import src.classes.Employee;
import src.server.ServerRequest;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI {
    static ServerRequest request = new ServerRequest();
    static int employeeId = 0;
    static double libraryBONUS = 300.15; //only for CE employees
    static double searchBONUS = 250.82; //only for PE employees/CE
    static double basicSALARY = 1500; //only for PE/PM employees
    static double contractSALARY = 600; //only for CE/CM employees
    static boolean paidEveryone = false;
    static boolean hireAlready = false;
    static Date currentDate;

    public static JPanel panelForMessageDialog(String message) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 600));
        JLabel label = new JLabel(message);
        label.setFont(new Font("Calibri", Font.ITALIC, 17));
        panel.add(label);
        return panel;
    }

    public static JPanel panelLoginAndFirePage(JTextField firstField, JTextField secondField, JLabel firstLabel, JLabel secondLabel) {
        firstField.setPreferredSize(new Dimension(334, 19));
        secondField.setPreferredSize(new Dimension(334, 19));
        firstLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
        secondLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JPanel panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(grid);
        panel.setPreferredSize(new Dimension(600, 600));

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(firstLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(firstField, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(secondLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(secondField, gbc);

        return panel;
    }

    public static JPanel panelChangeBonusSalaryPage(JTextField[] fields, JLabel[] labels) {
        fields[0].setPreferredSize(new Dimension(334, 19));
        fields[1].setPreferredSize(new Dimension(334, 19));
        fields[2].setPreferredSize(new Dimension(334, 19));
        fields[3].setPreferredSize(new Dimension(334, 19));
        labels[0].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[1].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[2].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[3].setFont(new Font("Calibri", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JPanel panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(grid);
        panel.setPreferredSize(new Dimension(600, 600));

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(fields[0], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labels[1], gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fields[1], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labels[2], gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(fields[2], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labels[3], gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(fields[3], gbc);

        return panel;
    }

    public static JPanel panelChangeInfoPage(JTextField[] fields, JLabel[] labels) {
        fields[0].setPreferredSize(new Dimension(334, 19));
        fields[1].setPreferredSize(new Dimension(334, 19));
        fields[2].setPreferredSize(new Dimension(334, 19));
        fields[3].setPreferredSize(new Dimension(334, 19));
        fields[4].setPreferredSize(new Dimension(334, 19));
        labels[0].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[1].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[2].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[3].setFont(new Font("Calibri", Font.ITALIC, 20));
        labels[4].setFont(new Font("Calibri", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JPanel panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(grid);
        panel.setPreferredSize(new Dimension(600, 600));

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(fields[0], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labels[1], gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fields[1], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labels[2], gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(fields[2], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labels[3], gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(fields[3], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labels[4], gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(fields[4], gbc);

        return panel;
    }

    public static JPanel panelHirePage(JTextField[] fields, JLabel[] labels) {
        fields[0].setPreferredSize(new Dimension(234, 7));
        fields[1].setPreferredSize(new Dimension(234, 7));
        fields[2].setPreferredSize(new Dimension(234, 7));
        fields[3].setPreferredSize(new Dimension(234, 7));
        fields[4].setPreferredSize(new Dimension(234, 7));
        fields[5].setPreferredSize(new Dimension(234, 7));
        fields[6].setPreferredSize(new Dimension(234, 7));
        fields[7].setPreferredSize(new Dimension(234, 7));
        fields[8].setPreferredSize(new Dimension(234, 7));
        fields[9].setPreferredSize(new Dimension(234, 7));
        fields[10].setPreferredSize(new Dimension(234, 7));
        fields[11].setPreferredSize(new Dimension(234, 7));
        fields[12].setPreferredSize(new Dimension(234, 7));
        labels[0].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[1].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[2].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[3].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[4].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[5].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[6].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[7].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[8].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[9].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[10].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[11].setFont(new Font("Calibri", Font.ITALIC, 17));
        labels[12].setFont(new Font("Calibri", Font.ITALIC, 17));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JPanel panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setLayout(grid);
        panel.setPreferredSize(new Dimension(600, 600));

        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(dateLabel);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labels[0], gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(fields[0], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labels[1], gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fields[1], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labels[2], gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(fields[2], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labels[3], gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(fields[3], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labels[4], gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(fields[4], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(labels[5], gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(fields[5], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(labels[6], gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(fields[6], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(labels[7], gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(fields[7], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(labels[8], gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(fields[8], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 10;
        panel.add(labels[9], gbc);

        gbc.gridx = 1;
        gbc.gridy = 10;
        panel.add(fields[9], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 11;
        panel.add(labels[10], gbc);

        gbc.gridx = 1;
        gbc.gridy = 11;
        panel.add(fields[10], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 12;
        panel.add(labels[11], gbc);

        gbc.gridx = 1;
        gbc.gridy = 12;
        panel.add(fields[11], gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = 13;
        panel.add(labels[12], gbc);

        gbc.gridx = 1;
        gbc.gridy = 13;
        panel.add(fields[12], gbc);

        return panel;
    }

    public static void loginPage() throws SQLException {
        JTextField username = new JTextField();
        JTextField password = new JTextField();

        JLabel labelUser = new JLabel("Username: ");
        JLabel labelPass = new JLabel("Password: ");

        currentDate = Date.valueOf("2023-02-01");
        int option = JOptionPane.showConfirmDialog(null, panelLoginAndFirePage(username, password, labelUser, labelPass), "Login Page", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (username.getText().equals("root") && password.getText().equals("root"))
                displayProcedures(true);
            else {
                employeeId = request.login(username.getText(), password.getText());
                if (employeeId == -1) {
                    JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect login!"), "Message", JOptionPane.INFORMATION_MESSAGE);
                    loginPage();
                }
                JOptionPane.showMessageDialog(null, panelForMessageDialog("Employee with id " + employeeId + " made a successful login!"), "Message", JOptionPane.INFORMATION_MESSAGE);
                displayProcedures(false);
            }
        } else
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Login cancelled!"), "Message", JOptionPane.INFORMATION_MESSAGE);
    }


    private static void validateExit(boolean root) throws SQLException {
        String message = "<html><body width='%1s'><h1>Would you like to exit?</h1><br><br>" +
                "[Yes: Go to login page<br><br>" +
                "No: Go to previous page]<br><br>";

        int option = JOptionPane.showConfirmDialog(null, panelForMessageDialog(message), "Exit", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION)
            loginPage();
        else
            displayProcedures(root);
    }

    private static void displayResultsEmployees(ArrayList<Employee> Employees, ArrayList<String> Categories, boolean root) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        JFrame frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);

        ok.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            try {
                validateExit(root);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        String employeeEntries = "First Name | Last Name | Address | Phone Number | Begin Hiring Date | Employee Id | Salary | Group/Job Department\n\n";

        for (int i = 0; i < Employees.size(); ++i)
            employeeEntries += (Employees.get(i).getFirstName() + " | " +
                    Employees.get(i).getLastName() + " | " + Employees.get(i).getAddress() + " | " + Employees.get(i).getPhoneNumber() + " | " + Employees.get(i).getBeginHiringDate() +
                    " | " + Employees.get(i).getEmployeeId() + " | " + Categories.get(i) + "\n");

        int option = JOptionPane.showOptionDialog(frame, employeeEntries,
                "Results",
                JOptionPane.OK_OPTION,
                0,
                null,
                options,
                options[0]);

    }

    private static void EmployeeNotFound(boolean root) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        JFrame frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);

        ok.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            try {
                validateExit(root);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        int option = JOptionPane.showOptionDialog(frame, "Employee does not belong to any group/job department.",
                "Results",
                JOptionPane.OK_OPTION,
                0,
                null,
                options,
                options[0]);

    }

    private static void displayResultsSalaries(ArrayList<Double> Salaries, ArrayList<String> Categories, boolean root) throws SQLException {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        JFrame frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);

        ok.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
            try {
                validateExit(root);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        String salaries = "";

        for (int i = 0; i < Salaries.size(); ++i)
            salaries += (Categories.get(i) + ": " + Salaries.get(i).toString() + "\n");

        int option = JOptionPane.showOptionDialog(frame, salaries,
                "Results",
                JOptionPane.OK_OPTION,
                0,
                null,
                options,
                options[0]);
    }

    private static int getEmployeeId(boolean root) throws NumberFormatException {
        JTextField employeeId = new JTextField();
        Object[] message = {
                "Give me the employee id you want to check:", employeeId
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Employee Id", JOptionPane.OK_CANCEL_OPTION);
        int givenEmployeeId = Integer.parseInt(employeeId.getText());
        if (givenEmployeeId < 0) {
            JOptionPane.showMessageDialog(null, "Incorrect Employee Id");
            return -1;
        }
        if (option == JOptionPane.CANCEL_OPTION) {
            displayProcedures(root);
            return -1;
        }
        return givenEmployeeId;
    }

    private static ArrayList<String> initCategoriesForStats(ArrayList<String> categories) {
        categories = new ArrayList<>();
        categories.add("Permanent Manager");
        categories.add("Permanent Educator");
        categories.add("Contractor Manager");
        categories.add("Contractor EducatorS");
        return categories;
    }

    private static void displayQueries(JFrame queriesFrame, boolean root) {
        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JPanel panel;
        JButton[] buttons = new JButton[8];
        panel = new JPanel(new GridLayout(9, 1));
        panel.add(dateLabel);
        String b[] = {"Payment State per Staff Category", "Max Salary per Staff Category", "Min Salary per Staff Category",
                "Average Salary per Staff Category", "Average Salary and Bonus Increase", "Employee Data and Salary", "Total Salary Increase per Staff Category", "Back to Login Page"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                queriesFrame.dispose();
                switch (choice) {
                    case "Back to Previous Page":
                        displayProcedures(root);
                        break;
                    case "Back to Login Page":
                        try {
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Payment State per Staff Category":
                        try {
                            ArrayList<String> categories = new ArrayList<>();
                            ArrayList<Employee> employees = request.getSalaryperStaffCategory(categories);
                            displayResultsEmployees(employees, categories, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Max Salary per Staff Category":
                        try {
                            ArrayList<String> categories = null;
                            categories = initCategoriesForStats(categories);
                            ArrayList<Double> maxSalaries = request.getMaxSalaryStatisticsperCategory();
                            displayResultsSalaries(maxSalaries, categories, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Min Salary per Staff Category":
                        try {
                            ArrayList<String> categories = null;
                            categories = initCategoriesForStats(categories);
                            ArrayList<Double> minSalaries = request.getMinSalaryStatisticsperCategory();
                            displayResultsSalaries(minSalaries, categories, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Average Salary per Staff Category":
                        try {
                            ArrayList<String> categories = null;
                            categories = initCategoriesForStats(categories);
                            ArrayList<Double> avgSalaries = request.getAverageSalaryStatisticsperCategory();
                            displayResultsSalaries(avgSalaries, categories, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Average Salary and Bonus Increase":
                        break;
                    case "Employee Data and Salary":
                         try {
                             int employeeId = getEmployeeId(root);
                             ArrayList<Employee> employees = new ArrayList<>();
                             ArrayList<String> categories = new ArrayList<>();
                             Employee employee = request.getEmployeeSalaryData(employeeId,  categories);
                             employees.add(employee);
                            if(categories.get(0).equals("Undefined")){
                                EmployeeNotFound(root);
                                break;
                            }
                            displayResultsEmployees(employees, categories, root);
                          } catch (SQLException ex) {
                              throw new RuntimeException(ex);
                          }
                        break;
                    case "Total Salary Increase per Staff Category":
                        break;
                    default:
                        assert (false);
                }
            });
            panel.add(buttons[i]);
        }

        queriesFrame.add(panel);
        queriesFrame.setPreferredSize(new Dimension(600, 600));
        queriesFrame.pack();
        queriesFrame.setLocationRelativeTo(null);
        queriesFrame.setVisible(true);
        queriesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void displayPaymentInGUI(ArrayList<String> payments) {
        String message = "<html><body width='%1s'><h1>Payment Info</h1>";

        for (int i = 0; i < payments.size(); i++) {
            message += payments.get(i) + "<br><br>";
        }

        JOptionPane.showMessageDialog(null, panelForMessageDialog(String.format(message, 500, 500)), "Message", JOptionPane.INFORMATION_MESSAGE);

    }

    private static void displayProcedures(boolean root) {
        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JFrame proceduresFrame = new JFrame("Procedures");
        JPanel panel;
        JButton[] buttons = new JButton[7];
        panel = new JPanel(new GridLayout(8, 1));
        panel.add(dateLabel);
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
                        displayQueries(queriesFrame, root);
                        should_dispose.set(true);
                        break;
                    case "Back to Login Page":
                        proceduresFrame.dispose();
                        should_dispose.set(true);
                        try {
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "New Hire":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        try {
                            should_dispose.set(true);
                            displayHire(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Payments":
                        //TODO check payment -> payment, payment -> hire, payment ->fire
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        hireAlready = false;
                        String[] date = currentDate.toString().split("-");
                        if (paidEveryone == true) {
                            JOptionPane.showMessageDialog(null, panelForMessageDialog("Employees have already been paid!"), "Message", JOptionPane.INFORMATION_MESSAGE);
                            currentDate = Date.valueOf(((Integer.parseInt(date[1]) + 1) == 13) ? ((Integer.parseInt(date[0]) + 1) + "-01-" + date[2])
                                    : (date[0] + "-" + (Integer.parseInt(date[1]) + 1) + "-01"));
                        } else {
                            currentDate = Date.valueOf(date[0] + "-" + date[1] + "-31");
                            try {
                                ArrayList<String> payments = request.payEmployees(currentDate.toString(), basicSALARY, contractSALARY);
                                displayPaymentInGUI(payments);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            paidEveryone = true;
                        }
                        displayProcedures(root);
                        break;
                    case "Change Employee Info":
                        should_dispose.set(true);
                        try {
                            displayChangeInfo(proceduresFrame, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Change Salary/Bonuses":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        try {
                            should_dispose.set(true);
                            displayChangeSalaryBonuses(proceduresFrame, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "New Fire/Retirement":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        try {
                            should_dispose.set(true);
                            displayFireRetire(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
        proceduresFrame.setPreferredSize(new Dimension(600, 400));
        proceduresFrame.pack();
        proceduresFrame.setLocationRelativeTo(null);
        proceduresFrame.setVisible(true);
        proceduresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static boolean check_correct_IBAN_category(String IBAN, String groupEmployer, String jobDepartment) {
        if (IBAN.length() == 0 || !IBAN.matches("[0-9]+") || Integer.parseInt(IBAN) > 2147483647) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data from Employee's IBAN!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (!groupEmployer.equals("Permanent") && !groupEmployer.equals("Contractor") && !jobDepartment.equals("Educator") && !jobDepartment.equals("Manager")) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data from Employee's category!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        return true;
    }

    public static boolean check_correct_family_state(String ages, String kids, String state) {
        String[] splitKidsAges = ages.split(",");
        if (!kids.matches("[0-9]+") || Integer.parseInt(kids) > 10) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data or not exists from Employee's family state!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        int kidsNumber = Integer.parseInt(kids);
        if ((splitKidsAges.length != kidsNumber && kidsNumber != 0) || (kidsNumber != 0 && state == "unmarried") || (kidsNumber == 0 && state == "married")) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data from Employee's family state!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        for (int i = 0; i < kidsNumber; i++) {
            if (state == "married" && (Integer.parseInt(splitKidsAges[i]) <= 0 || Integer.parseInt(splitKidsAges[i]) >= 100)) {
                JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data from Employee's family state!"), "Message", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }

        return true;
    }

    public static boolean check_correct_phoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 0 || !phoneNumber.matches("[0-9]+") || Integer.parseInt(phoneNumber) > 2147483647) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect data or not given data from Employee's phoneNumber!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean check_rest_info(String firstName, String lastName, String username, String password, String address, String bankName) {
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0
                || password.length() == 0 || address.length() == 0 || bankName.length() == 0) {
            JOptionPane.showMessageDialog(null, panelForMessageDialog("Not given data for Employee's information!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    private static void displayHire(boolean root) throws SQLException {
        JTextField firstName = new JTextField();
        JTextField lastName = new JTextField();
        JTextField username = new JTextField();
        JTextField password = new JTextField();
        JTextField address = new JTextField();
        JTextField phoneNumber = new JTextField();
        JTextField groupEmployer = new JTextField();
        JTextField jobDepartment = new JTextField();
        JTextField married = new JTextField();
        JTextField kids = new JTextField();
        JTextField kidsAgesWithCommas = new JTextField();
        JTextField IBAN = new JTextField();
        JTextField bankName = new JTextField();
        JLabel labelFirstName = new JLabel("First Name: ");
        JLabel labelLastName = new JLabel("Last Name : ");
        JLabel labelUsername = new JLabel("Username : ");
        JLabel labelPassword = new JLabel("Password : ");
        JLabel labelAddress = new JLabel("Address : ");
        JLabel labelPhoneNumber = new JLabel("Phone number : ");
        JLabel labelGroupEmployer = new JLabel("Group Employer: ");
        JLabel labelJobDepartment = new JLabel("Job Department : ");
        JLabel labelMarried = new JLabel("Married: ");
        JLabel labelKids = new JLabel("Kids: ");
        JLabel labelKidsAgesWithCommas = new JLabel("Kids ages (Commas): ");
        JLabel labelIBAN = new JLabel("IBAN: ");
        JLabel labelBankName = new JLabel("Bank Name: ");
        JTextField[] fields = {firstName, lastName, username, password, address, phoneNumber, groupEmployer, jobDepartment, married, kids, kidsAgesWithCommas, IBAN, bankName};
        JLabel[] labels = {labelFirstName, labelLastName, labelUsername, labelPassword, labelAddress, labelPhoneNumber, labelGroupEmployer, labelJobDepartment, labelMarried,
                labelKids, labelKidsAgesWithCommas, labelIBAN, labelBankName};


        int option = JOptionPane.showConfirmDialog(null, panelHirePage(fields, labels), "Hire", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!check_correct_family_state(kidsAgesWithCommas.getText(), kids.getText(), married.getText())
                    || !check_correct_IBAN_category(IBAN.getText(), groupEmployer.getText(), jobDepartment.getText())
                    || !check_rest_info(firstName.getText(), lastName.getText(), username.getText(), password.getText(), address.getText(), bankName.getText())
                    || !check_correct_phoneNumber(phoneNumber.getText()))
                displayProcedures(root);
            else {
                String[] date = currentDate.toString().split("-");
                //TODO below statement
                if (!paidEveryone) { //employees are not paid, user's mistake
                    ArrayList<String> payments = request.payEmployees(currentDate.toString(), basicSALARY, contractSALARY);
                    displayPaymentInGUI(payments);
                }

                if (!hireAlready) {
                    String dateS = ((Integer.parseInt(date[1]) + 1) == 13) ? ((Integer.parseInt(date[0]) + 1) + "-01-" + date[2])
                            : (date[0] + "-" + (Integer.parseInt(date[1]) + 1) + "-01");
                    currentDate = Date.valueOf(dateS);
                }

                //TODO must check IBAN != all Employees in Database???
                int bankId = request.insertBankInfo(Integer.parseInt(IBAN.getText()), bankName.getText());
                int bonusId = request.insertBonus(request.calculateFamilyBonus(married.getText().toString(), kids.getText().toString()), searchBONUS, libraryBONUS, groupEmployer.getText().toString(), jobDepartment.getText().toString()); //calculateFamilyBonus instead of 0.15
                int stateId = request.insertFamilyState(married.getText(), Integer.parseInt(kids.getText()), kidsAgesWithCommas.getText());

                int[] infoInt = {Integer.parseInt(phoneNumber.getText()), bankId, stateId, bonusId};
                String[] infoStr = {firstName.getText(), lastName.getText(), address.getText()};

                //TODO check string for groupEmployer and jobDepartment, in general all info given from user
                if (groupEmployer.getText().equals("Permanent"))
                    employeeId = request.insertEmployee(infoStr, currentDate, infoInt, basicSALARY);
                else
                    employeeId = request.insertEmployee(infoStr, currentDate, infoInt, contractSALARY);

                request.hireEmployee(employeeId, groupEmployer.getText().toString(), jobDepartment.getText().toString(), username.getText(), password.getText());

                paidEveryone = false;
                hireAlready = true;
                displayProcedures(root);
            }
        } else {
            try {
                validateExit(root);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void displayFireRetire(boolean root) throws NumberFormatException, SQLException {
        JTextField employeeId = new JTextField();
        JTextField fireRetire = new JTextField();
        JLabel labelId = new JLabel("EmployeeId: ");
        JLabel labelOption = new JLabel("Fire/Retire: ");

        int option = JOptionPane.showConfirmDialog(null, panelLoginAndFirePage(employeeId, fireRetire, labelId, labelOption), "Fire", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            hireAlready = false;

            if (employeeId.getText().equals("") || Integer.parseInt(employeeId.getText()) < 0) {
                JOptionPane.showMessageDialog(null, panelForMessageDialog("Incorrect Employee Id"), "Message", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int givenEmployeeId = Integer.parseInt(employeeId.getText());
            double payment = request.fireEmployee(givenEmployeeId);
            if (paidEveryone == true) {
                JOptionPane.showMessageDialog(null, panelForMessageDialog("EmployeeID " + givenEmployeeId + " has already been paid with "
                        + payment + " euros!"), "Message", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, panelForMessageDialog("EmployeeID " + givenEmployeeId +
                        " is " + fireRetire.getText().toString() + "d with " + payment + " euros!"), "Message", JOptionPane.INFORMATION_MESSAGE);

            displayProcedures(root);
        } else
            validateExit(root);

    }

    private static void displayChangeInfo(JFrame proceduresFrame, boolean root) throws SQLException {
        JTextField address = new JTextField();
        JTextField phoneNumber = new JTextField();
        JTextField married = new JTextField();
        JTextField kids = new JTextField();
        JTextField kidsAgesWithCommas = new JTextField();
        JLabel labelAddress = new JLabel("Address: ");
        JLabel labelPhoneNumber = new JLabel("Phone number: ");
        JLabel labelMarried = new JLabel("Married: ");
        JLabel labelKids = new JLabel("Kids: ");
        JLabel labelKidsAgesWithCommas = new JLabel("Kids ages (Commas): ");
        JTextField[] fields = {address, phoneNumber, married, kids, kidsAgesWithCommas};
        JLabel[] labels = {labelAddress, labelPhoneNumber, labelMarried, labelKids, labelKidsAgesWithCommas};

        int option = JOptionPane.showConfirmDialog(null, panelChangeInfoPage(fields, labels), "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            hireAlready = false;
            if (address.getText().length() != 0) request.changeAddress(address.getText(), employeeId);
            if (check_correct_phoneNumber(phoneNumber.getText()))
                request.changePhoneNumber(Integer.parseInt(phoneNumber.getText()), employeeId);
            if (check_correct_family_state(kidsAgesWithCommas.getText(), kids.getText(), married.getText())) {
                request.changeFamilyState(married.getText(), Integer.parseInt(kids.getText()), kidsAgesWithCommas.getText(), employeeId);
                request.changeSalary(currentDate.toString(), basicSALARY, contractSALARY);
            }
            displayProcedures(root);
        } else
            validateExit(root);

    }

    private static void displayChangeSalaryBonuses(JFrame proceduresFrame, boolean root) throws SQLException {
        JTextField searchBonus = new JTextField();
        JTextField libraryBonus = new JTextField();
        JTextField basicSalary = new JTextField();
        JTextField contractSalary = new JTextField();
        JLabel labelSearchBonus = new JLabel("Search Bonus: ");
        JLabel labelLibraryBonus = new JLabel("Library Bonus: ");
        JLabel labelBasicSalary = new JLabel("Basic Salary: ");
        JLabel labelContractSalary = new JLabel("Contract Salary: ");
        JTextField[] fields = {searchBonus, libraryBonus, basicSalary, contractSalary};
        JLabel[] labels = {labelSearchBonus, labelLibraryBonus, labelBasicSalary, labelContractSalary};

        int option = JOptionPane.showConfirmDialog(null, panelChangeBonusSalaryPage(fields, labels), "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!basicSalary.getText().equals("")) basicSALARY = Double.parseDouble(basicSalary.getText().toString());
            if (!contractSalary.getText().equals(""))
                contractSALARY = Double.parseDouble(contractSalary.getText().toString());
            if (!libraryBonus.getText().equals("")) {
                libraryBONUS = Double.parseDouble(libraryBonus.getText().toString());
                request.changeLibraryBonus(libraryBONUS);
            }
            if (!searchBonus.getText().equals("")) {
                searchBONUS = Double.parseDouble(searchBonus.getText().toString());
                request.changeSearchBonus(searchBONUS);
            }
            request.changeSalary(currentDate.toString(), basicSALARY, contractSALARY);
            displayProcedures(root);
        } else
            validateExit(root);
    }
}