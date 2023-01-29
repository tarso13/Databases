package src.gui;

import src.server.ServerRequest;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class GUI {
    static ServerRequest request = new ServerRequest();
    static int employeeId = 0;
    static double libraryBONUS = 300.15; //only for CE employees
    static double searchBONUS = 250.82; //only for PE employees/CE
    static double basicSALARY = 1500; //only for PE/PM employees
    static double contractSALARY = 600; //only for CE/CM employees
    static boolean paidEveryone = false;
    static Date currentDate;

    public static JFrame panelForMessageDialog(String message, boolean root, boolean yesno) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JButton returnPage = new JButton("OK");
        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);
        label.setFont(new Font("Calibre", Font.ITALIC, 17));
        panel.add(label);
        if (!yesno) {
            returnPage.addActionListener(
                    e -> {
                        frame.setVisible(false);
                        frame.dispose();
                        if(message.equals("Login cancelled!")==false)
                            displayProcedures(root);
                    });
            panel.add(returnPage);
        } else {
            JButton[] yesNo = {new JButton("Yes"), new JButton("No")};
            yesNo[0].addActionListener(
                    e -> {
                        frame.setVisible(false);
                        frame.dispose();
                        try {
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
            yesNo[1].addActionListener(
                    e -> {
                        frame.setVisible(false);
                        frame.dispose();
                        displayProcedures(root);
                    });
            panel.add(yesNo[0]);
            panel.add(yesNo[1]);
        }

        frame.add(BorderLayout.CENTER, new JScrollPane(panel));
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }

    public static JPanel panelLoginAndFirePage(JTextField firstField, JTextField secondField, JLabel firstLabel, JLabel secondLabel) {
        firstField.setPreferredSize(new Dimension(334, 19));
        secondField.setPreferredSize(new Dimension(334, 19));
        firstLabel.setFont(new Font("Calibre", Font.ITALIC, 20));
        secondLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

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
        labels[0].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[1].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[2].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[3].setFont(new Font("Calibre", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

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
        labels[0].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[1].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[2].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[3].setFont(new Font("Calibre", Font.ITALIC, 20));
        labels[4].setFont(new Font("Calibre", Font.ITALIC, 20));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

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

        gbc.ipady = 20;
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
        labels[0].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[1].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[2].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[3].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[4].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[5].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[6].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[7].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[8].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[9].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[10].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[11].setFont(new Font("Calibre", Font.ITALIC, 17));
        labels[12].setFont(new Font("Calibre", Font.ITALIC, 17));

        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

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

        gbc.ipady = 20;
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

    public static boolean check_valid_dates(Date initialDate, Date finalDate, Date currentDate){
        try {
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(initialDate.toString(),
                    DateTimeFormatter.ofPattern("uuuu-M-d")
                            .withResolverStyle(ResolverStyle.STRICT)
            );

        } catch (DateTimeParseException e) {
            return false;
        }

        try {
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(finalDate.toString(),
                    DateTimeFormatter.ofPattern("uuuu-M-d")
                            .withResolverStyle(ResolverStyle.STRICT)
            );

        } catch (DateTimeParseException e) {
            System.out.println("invalid dates");
            return false;
        }

        return !currentDate.before(initialDate);
    }

    private static ArrayList<Date> getDates(boolean root) throws SQLException {
        JTextField initialDate = new JTextField();
        JTextField finalDate = new JTextField();
        JLabel labelInit = new JLabel("Initial Date: ");
        JLabel labelFinal = new JLabel("Final Date: ");

        ArrayList<Date> dates = new ArrayList<>();

        int option = JOptionPane.showConfirmDialog(null, panelLoginAndFirePage(initialDate, finalDate, labelInit, labelFinal), "Login Page", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (initialDate.getText().equals("") || finalDate.getText().equals("")
                    || !check_valid_dates(Date.valueOf(initialDate.getText()),Date.valueOf(finalDate.getText()),currentDate)) {
                int opt = JOptionPane.showConfirmDialog(null, panelForMessageDialog("Invalid Dates given!", true, false), "Message", JOptionPane.INFORMATION_MESSAGE);
                if (opt == JOptionPane.OK_OPTION)
                    displayProcedures(root);
                else
                    validateExit(root);
                return null;
            }

            dates.add(Date.valueOf(initialDate.getText()));
            dates.add(Date.valueOf(finalDate.getText()));
            if (currentDate.before(Date.valueOf(finalDate.getText()))) dates.add(currentDate);
        } else
            validateExit(root);
        return dates;
    }

    public static void loginPage() throws SQLException {
        JTextField username = new JTextField();
        JTextField password = new JTextField();

        JLabel labelUser = new JLabel("Username: ");
        JLabel labelPass = new JLabel("Password: ");

        currentDate = Date.valueOf("2023-02-01");
        int option = JOptionPane.showConfirmDialog(null, panelLoginAndFirePage(username, password, labelUser, labelPass), "Login Page", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (username.getText().equals("") || password.getText().equals("")) {
                panelForMessageDialog("Not given username or password!", false, false);
                loginPage();
            } else if (username.getText().equals("root") && password.getText().equals("root"))
                displayProcedures(true);
            else {
                employeeId = request.loginEmployee(username.getText(), password.getText());
                if (employeeId == -1) {
                    panelForMessageDialog("Login with false combination of username and password!", false, false);
                    loginPage();
                }
                displayProcedures(false);
            }
        } else
            panelForMessageDialog("Login cancelled!", false, false);
    }


    private static void validateExit(boolean root) throws SQLException {
        String message = "<html><body width='%1s'><h1>Would you like to exit?</h1><br><br>" +
                "[Yes: Go to login page<br><br>" +
                "No: Go to previous page]<br><br>";
        panelForMessageDialog(message, root, true);
    }

    private static void displayPaymentPerStaffCategory(ArrayList<ArrayList<String>> payments, ArrayList<String> Categories, boolean root) {
        StringBuilder message = new StringBuilder("<html><body width='%1s'><h1>Payment Staff Per Category</h1><br><br>");

        for (int category = 0; category < 4; category++) {
            message.append("<p>").append(Categories.get(category)).append(" {<br><br>");
            for (int person = 0; person < payments.get(category).size(); person++) {
                message.append(payments.get(category).get(person)).append("<br><br>");
            }
            message.append("}</p><br><br>");
        }
        panelForMessageDialog(message.toString(), root, false);
    }

    private static void displayEmployeeInfo(String info) {
        String message = "<html><body width='%1s'><h1>EmployeeInfo</h1><br><br>" + info + "<br><br>";
        panelForMessageDialog(message, true, false);
    }

    private static void displayResultsSalaries(ArrayList<Double> Salaries, ArrayList<String> Categories, boolean root, String stat) throws SQLException {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        JFrame frame = new JFrame();
        frame.setSize(600, 600);
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

        StringBuilder salaries = new StringBuilder("<html><body width='%1s'><h1>" + stat + "Salary Per Category</h1><br><br>");
        for (int i = 0; i < Salaries.size(); ++i)
            salaries.append(Categories.get(i)).append(": ").append(Salaries.get(i).toString()).append("<br><br>");
        int option = JOptionPane.showOptionDialog(frame, salaries.toString(), "Results", JOptionPane.OK_OPTION, 0, null, options, options[0]);
    }

    private static int getEmployeeId(boolean root) throws NumberFormatException {
        JTextField employeeId = new JTextField();
        Object[] message = {
                "Give me the employee id you want to check:", employeeId
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Employee Id", JOptionPane.OK_CANCEL_OPTION);
        if (employeeId.getText() == null || employeeId.getText().equals("") || Integer.parseInt(employeeId.getText()) < 0
                || !employeeId.getText().matches("[0-9]+")) {
            panelForMessageDialog("Incorrect Employee Id", root, false);
            return -1;
        }

        if (option == JOptionPane.CANCEL_OPTION) {
            displayProcedures(root);
            return -1;
        }
        return Integer.parseInt(employeeId.getText());
    }

    private static void displayAverageSalaryBonusIncrease(boolean root, ArrayList<Double> result) throws NumberFormatException {
        JButton ok = new JButton("OK");
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

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Average family Bonus increment");
        categories.add("Average search Bonus increment");
        categories.add("Average library Bonus increment");
        categories.add("Average salary increment");

        StringBuilder message = new StringBuilder("<html><body width='%1s'><h1>Average Salary and Bonuses Increase for a specific duration</h1><br><br>");
        for (int i = 0; i < result.size(); ++i)
            message.append(categories.get(i)).append(": ").append(result.get(i).toString()).append("<br><br>");

        panelForMessageDialog(message.toString(), root, false);
    }

    private static ArrayList<String> initCategoriesForStats() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Permanent Manager");
        categories.add("Permanent Educator");
        categories.add("Contractor Manager");
        categories.add("Contractor Educator");
        return categories;
    }

    private static void displayQueries(JFrame queriesFrame, boolean root) {
        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        ArrayList<String> categories = initCategoriesForStats();

        JPanel panel;
        JButton[] buttons = new JButton[11];
        panel = new JPanel(new GridLayout(12, 1));
        panel.add(dateLabel);
        String[] b = {"Payment State per Staff Category", "Max Salary per Staff Category", "Min Salary per Staff Category", "Average Salary per Staff Category",
                "Average Salary and Bonus Increase", "Employee Data and Salary", "Total Salary per Staff Category", "Count of active Employees", "Employee with max number of kids", "Active employee with most experience", "Back to Login Page"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                queriesFrame.dispose();
                switch (choice) {
                    case "Back to Previous Page":
                        queriesFrame.dispose();
                        displayProcedures(root);
                        break;
                    case "Back to Login Page":
                        try {
                            queriesFrame.dispose();
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Payment State per Staff Category":
                        try {
                            queriesFrame.dispose();
                            ArrayList<ArrayList<String>> employees = new ArrayList<>();
                            employees.add(request.getSalaryPerCategory("PermanentManager"));
                            employees.add(request.getSalaryPerCategory("PermanentEducator"));
                            employees.add(request.getSalaryPerCategory("ContractorManager"));
                            employees.add(request.getSalaryPerCategory("ContractorEducator"));

                            displayPaymentPerStaffCategory(employees, categories, root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Max Salary per Staff Category":
                        try {
                            queriesFrame.dispose();
                            ArrayList<Double> maxSalaries = request.getMaxSalaryStatisticsperCategory();
                            displayResultsSalaries(maxSalaries, categories, root, "Max");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Min Salary per Staff Category":
                        try {
                            queriesFrame.dispose();
                            ArrayList<Double> minSalaries = request.getMinSalaryStatisticsperCategory();
                            displayResultsSalaries(minSalaries, categories, root, "Min");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Average Salary per Staff Category":
                        try {
                            queriesFrame.dispose();
                            ArrayList<Double> avgSalaries = request.getAverageSalaryStatisticsperCategory();
                            displayResultsSalaries(avgSalaries, categories, root, "Average");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Average Salary and Bonus Increase":
                        try {
                            queriesFrame.dispose();
                            ArrayList<Date> dates = getDates(root);
                            ArrayList<Double> averageResults = new ArrayList<>();
                            assert dates != null;
                            averageResults.add(request.getAverageSalaryBonusIncrease(dates.get(0), dates.get(1), "raiseSalary"));
                            averageResults.add(request.getAverageSalaryBonusIncrease(dates.get(0), dates.get(1), "raiseFamBonus"));
                            averageResults.add(request.getAverageSalaryBonusIncrease(dates.get(0), dates.get(1), "raiseSearchBonus"));
                            averageResults.add(request.getAverageSalaryBonusIncrease(dates.get(0), dates.get(1), "raiseLibraryBonus"));
                            displayAverageSalaryBonusIncrease(root, averageResults);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Employee Data and Salary":
                        queriesFrame.dispose();
                        int employeeId = getEmployeeId(root);
                        try {
                            displayEmployeeInfo(request.getEmployeeSalaryData(employeeId));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Total Salary per Staff Category":
                        try {
                            queriesFrame.dispose();
                            ArrayList<Double> totalSalaries = request.getTotalSalaryperCategory();
                            displayResultsSalaries(totalSalaries, categories, root, "Total");
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Count of active Employees":
                        try {
                            queriesFrame.dispose();
                            int id = request.countActiveEmployees();
                            panelForMessageDialog("Count of Active Employees are: " + id, root, false);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Employee with max number of kids":
                        try {
                            queriesFrame.dispose();
                            String name = request.EmployeeWithMaxKids();
                            panelForMessageDialog("Employee with max number of kid is: " + name, root, false);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Active employee with most experience":
                        try {
                            queriesFrame.dispose();
                            String name = request.EmployeeWithMaxExperience();
                            panelForMessageDialog("Employee with most experience is: " + name, root, false);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
        StringBuilder message = new StringBuilder("<html><body width='%1s'><h1>Payment Info</h1>");

        for (String payment : payments) {
            message.append(payment).append("<br><br>");
        }

        panelForMessageDialog(String.format(message.toString(), 500, 500), true, false);
    }

    public static void calculate_last_day_Month(String current) {
        String[] date = current.split("-");
        int month = Integer.parseInt(date[1]);
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            currentDate = Date.valueOf(date[0] + "-" + date[1] + "-31");
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            currentDate = Date.valueOf(date[0] + "-" + date[1] + "-30");
        } else currentDate = Date.valueOf(date[0] + "-" + date[1] + "-28");
    }

    private static void displayProcedures(boolean root) {
        JLabel dateLabel = new JLabel("Current date: " + currentDate.toString());
        dateLabel.setFont(new Font("Calibri", Font.ITALIC, 20));

        JFrame proceduresFrame = new JFrame("Procedures");
        JPanel panel;
        JButton[] buttons = new JButton[7];
        panel = new JPanel(new GridLayout(8, 1));
        panel.add(dateLabel);
        String[] b = {"New Hire", "New Fire/Retirement", "Change Salary/Bonuses", "Payments", "Change Employee Info", "Queries", "Back to Login Page"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                switch (choice) {
                    case "Queries":
                        proceduresFrame.dispose();
                        JFrame queriesFrame = new JFrame("Queries Supported");
                        displayQueries(queriesFrame, root);
                        break;
                    case "Back to Login Page":
                        proceduresFrame.dispose();
                        try {
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "New Hire":
                        if (!root)
                            break;
                        try {
                            proceduresFrame.dispose();
                            displayHire(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Payments":
                        if (!root)
                            break;
                        proceduresFrame.dispose();
                        String[] date = currentDate.toString().split("-");
                        if (paidEveryone) {
                            panelForMessageDialog("Employees have already been paid!", true, false);
                            currentDate = Date.valueOf(((Integer.parseInt(date[1]) + 1) == 13) ? ((Integer.parseInt(date[0]) + 1) + "-01-" + date[2])
                                    : (date[0] + "-" + (Integer.parseInt(date[1]) + 1) + "-01"));
                        } else {
                            if (Integer.parseInt(date[2]) == 1)
                                calculate_last_day_Month(currentDate.toString());
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
                        proceduresFrame.dispose();
                        try {
                            displayChangeInfo(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Change Salary/Bonuses":
                        if (!root)
                            break;
                        try {
                            proceduresFrame.dispose();
                            displayChangeSalaryBonuses(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "New Fire/Retirement":
                        if (!root)
                            break;
                        try {
                            proceduresFrame.dispose();
                            displayFireRetire(root);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    default:
                        assert (false);
                }
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

    public static boolean check_correct_IBAN_Category(String IBAN, String groupEmployer, String jobDepartment) {
        if (!IBAN.matches("[0-9]+")) {
            panelForMessageDialog("Incorrect data from Employee's IBAN!", false, false);
            return false;
        } else {
            Integer.parseInt(IBAN);
        }

        if (groupEmployer.equals("") || !groupEmployer.equals("Permanent") && !groupEmployer.equals("Contractor")) {
            panelForMessageDialog("Incorrect data from Employee's category (Permanent/Contractor)!", false, false);
            return false;
        }
        if (jobDepartment.equals("") || !jobDepartment.equals("Educator") && !jobDepartment.equals("Manager")) {
            panelForMessageDialog("Incorrect data from Employee's category (Educator/Manager)!", false, false);
            return false;
        }

        return true;
    }

    public static boolean check_correct_family_state(String ages, String kids, String state) {
        String[] splitKidsAges = ages.split(",");
        if (state.equals("") || !kids.matches("[0-9]+") || Integer.parseInt(kids) > 10) {
            panelForMessageDialog("Incorrect data or not exists from Employee's family state!", false, false);
            return false;
        }

        int kidsNumber = Integer.parseInt(kids);
        if (splitKidsAges.length != kidsNumber && kidsNumber != 0) {
            panelForMessageDialog("Incorrect data from Employee's family state!", false, false);
            return false;
        }

        for (int i = 0; i < kidsNumber; i++) {
            if (state.equals("married") && (Integer.parseInt(splitKidsAges[i]) <= 0 || Integer.parseInt(splitKidsAges[i]) >= 100)) {
                panelForMessageDialog("Incorrect data from Employee's family state!", false, false);
                return false;
            }
        }

        return true;
    }

    public static boolean check_correct_phoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("[0-9]+")) {
            panelForMessageDialog("Incorrect data or not given data from Employee's phoneNumber!", false, false);
            return false;
        }
        return true;
    }

    public static boolean check_rest_info(String firstName, String lastName, String username, String password, String address, String bankName) {
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0
                || password.length() == 0 || address.length() == 0 || bankName.length() == 0) {
            panelForMessageDialog("Not given data for Employee's information!", false, false);
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
                    || !check_correct_IBAN_Category(IBAN.getText(), groupEmployer.getText(), jobDepartment.getText())
                    || !check_rest_info(firstName.getText(), lastName.getText(), username.getText(), password.getText(), address.getText(), bankName.getText())
                    || !check_correct_phoneNumber(phoneNumber.getText()))
                displayProcedures(root);
            else {
                String[] date = currentDate.toString().split("-");
                if (Integer.parseInt(date[2]) != 1) {
                    String dateS = ((Integer.parseInt(date[1]) + 1) == 13) ? ((Integer.parseInt(date[0]) + 1) + "-01-" + date[2])
                            : (date[0] + "-" + (Integer.parseInt(date[1]) + 1) + "-01");
                    currentDate = Date.valueOf(dateS);
                }

                if (Integer.parseInt(date[2]) == 31 || Integer.parseInt(date[2]) == 28 || Integer.parseInt(date[2]) == 30 && !paidEveryone) { //employees are not paid, user's mistake
                    ArrayList<String> payments = request.payEmployees(currentDate.toString(), basicSALARY, contractSALARY);
                    displayPaymentInGUI(payments);
                }

                int bankId = request.insertBankInfo(Integer.parseInt(IBAN.getText()), bankName.getText());
                int bonusId = request.insertBonus(request.calculateFamilyBonus(married.getText(), kidsAgesWithCommas.getText()), searchBONUS, libraryBONUS, groupEmployer.getText(), jobDepartment.getText());
                int stateId = request.insertFamilyState(married.getText(), Integer.parseInt(kids.getText()), kidsAgesWithCommas.getText());
                int loginId = request.insertLogin(username.getText(),password.getText());

                int[] infoInt = {Integer.parseInt(phoneNumber.getText()), loginId,bankId, stateId, bonusId};
                String[] infoStr = {firstName.getText(), lastName.getText(), address.getText()};

                if (groupEmployer.getText().equals("Permanent"))
                    employeeId = request.insertEmployee(infoStr, currentDate, infoInt, basicSALARY);
                else
                    employeeId = request.insertEmployee(infoStr, currentDate, infoInt, contractSALARY);

                request.hireEmployee(employeeId, groupEmployer.getText(), jobDepartment.getText());

                paidEveryone = false;
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
            if (!employeeId.getText().matches("[0-9]+") || Integer.parseInt(employeeId.getText()) == 1) {
               panelForMessageDialog("Incorrect Employee Id", true, false);
                return;
            }
            int givenEmployeeId = Integer.parseInt(employeeId.getText());
            double payment = request.fireEmployee(givenEmployeeId);

            String[] date = currentDate.toString().split("-");
            if (Integer.parseInt(date[2]) == 1)
                calculate_last_day_Month(currentDate.toString());

            if (paidEveryone) {
                panelForMessageDialog("EmployeeID " + givenEmployeeId + " has ALREADY been paid with "
                        + payment + " euros!", true, false);
            } else {
               panelForMessageDialog("EmployeeID " + givenEmployeeId +
                        " has been " + fireRetire.getText() + "d given " + payment + " euros!", true, false);
            }
            displayProcedures(root);
        } else
            validateExit(root);

    }

    private static void displayChangeInfo(boolean root) throws SQLException {
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
            if (address.getText().length() != 0) request.changeAddress(address.getText(), employeeId);
            if (check_correct_phoneNumber(phoneNumber.getText()))
                request.changePhoneNumber(Integer.parseInt(phoneNumber.getText()), employeeId);
            if (check_correct_family_state(kidsAgesWithCommas.getText(), kids.getText(), married.getText())) {
                request.changeFamilyState(married.getText(), Integer.parseInt(kids.getText()), kidsAgesWithCommas.getText(), employeeId, currentDate);
                request.changeSalary(currentDate.toString(), basicSALARY, contractSALARY);
            }
            displayProcedures(root);
        } else
            validateExit(root);

    }

    private static void displayChangeSalaryBonuses(boolean root) throws SQLException {
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
            if (!basicSalary.getText().matches("(\\d*\\.?\\d+)") && !contractSalary.getText().matches("(\\d*\\.?\\d+)")
                    && !libraryBonus.getText().matches("(\\d*\\.?\\d+)") && !searchBonus.getText().matches("(\\d*\\.?\\d+)"))
                return;
            if (!basicSalary.getText().equals("")) basicSALARY = Double.parseDouble(basicSalary.getText());
            if (!contractSalary.getText().equals(""))
                contractSALARY = Double.parseDouble(contractSalary.getText());

            if (!libraryBonus.getText().equals("")) {
                libraryBONUS = Double.parseDouble(libraryBonus.getText());
                request.change_Search_Library_Bonus(libraryBONUS, "libraryBonus", currentDate);
            }
            if (!searchBonus.getText().equals("")) {
                searchBONUS = Double.parseDouble(searchBonus.getText());
                request.change_Search_Library_Bonus(searchBONUS, "searchBonus", currentDate);
            }
            request.changeSalary(currentDate.toString(), basicSALARY, contractSALARY);
            displayProcedures(root);
        } else
            validateExit(root);
    }
}