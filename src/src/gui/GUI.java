package gui;

import classes.Employee;
import server.ServerRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class GUI {
    static ServerRequest request = new ServerRequest();
    static int employeeId = 0;
    static JFrame proceduresFrame = new JFrame("Procedures");
    static double libraryBonus = 300.15; //only for CE employees
    static double searchBonus = 250.82; //only for PE employees/CE
    static double basicSALARY = 1500; //only for PE/PM employees
    static double contractSALARY = 600; //only for CE/CM employees
    static Date currentDate = new Date(1990-10-10);

    private void showResultsEmployees(ArrayList<Employee> Employees, JFrame frame) {
        JButton ok = new JButton("OK");
        JButton[] options = {ok};
        frame = new JFrame();
        frame.setLocation(200, 200);
        frame.setResizable(false);
        String employeeEntries = "";
//        for (int i = 0; i < Employees.size(); ++i)
//            employeeEntries += (Employees.get(i).getFirstName() + " " +
//                    Employees.get(i).getLastName() + " " + Employees.get(i).getAddress() + " " + Employees.get(i).getPhoneNumber() + " " + Employees.get(i).getBeginHiringDate() +
//                    " " + Employees.get(i).getEmployeeId() + "\n");
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
                        try {
                            loginPage();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
                        should_dispose.set(true);
                        try {
                            displayHire();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
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
                        try {
                            displayChangeInfo();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Change Salary/Bonuses":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        try {
                            displayChangeSalaryBonuses();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "New Fire/Retirement":
                        if (!root) {
                            should_dispose.set(false);
                            break;
                        }
                        should_dispose.set(true);
                        try {
                            displayFireRetire();
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
        proceduresFrame.pack();
        proceduresFrame.setVisible(true);
        proceduresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void loginPage() throws SQLException {
        JTextField username = new JTextField();
        JTextField password = new JTextField();

        Object[] message = {
                "Username:", username,
                "Password:", password
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login Page", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            //change correct credentials
            if (username.getText().equals("root") && password.getText().equals("root"))
                displayProcedures(proceduresFrame, true);
            else {
                employeeId = request.login(username.getText(), password.getText());
                if (employeeId == -1) {
                    JOptionPane.showMessageDialog(null, "Incorrect login");
                    loginPage();
                }
                JOptionPane.showMessageDialog(null, "Employee with id " + employeeId + " made a successful login!");
                displayProcedures(proceduresFrame, false);
            }
        } else
            JOptionPane.showMessageDialog(null, "Login cancelled");
    }

    public static boolean check_correct_IBAN_category(String IBAN, String groupEmployer, String jobDepartment){
        if (IBAN.length() == 0 ||!IBAN.matches("[0-9]+") ||  Integer.parseInt(IBAN) > 2147483647){
            JOptionPane.showMessageDialog(null, "Incorrect data from Employee's IBAN!");
            return false;
        }

        if (!groupEmployer.equals("Permanent") && !groupEmployer.equals("Contractor") && !jobDepartment.equals("Educator") && !jobDepartment.equals("Manager")){
            JOptionPane.showMessageDialog(null, "Incorrect data from Employee's category!");
            return false;
        }

        return true;
    }

    public static boolean check_correct_family_state(String ages, String kids, String state){
        String[] splitKidsAges = ages.split(",");
        if (!kids.matches("[0-9]+") || Integer.parseInt(kids) > 10){
            JOptionPane.showMessageDialog(null, "Incorrect data from Employee's family state!");
            return false;
        }

        int kidsNumber = Integer.parseInt(kids);
        if ((splitKidsAges.length != kidsNumber && kidsNumber != 0) || (kidsNumber !=0 && state=="unmarried") || (kidsNumber ==0 && state=="married")){
            JOptionPane.showMessageDialog(null, "Incorrect data from Employee's family state!");
            return false;
        }

        for (int i=0; i<kidsNumber; i++){
            if (Integer.parseInt(splitKidsAges[i]) <= 0 || Integer.parseInt(splitKidsAges[i]) >= 100){
                JOptionPane.showMessageDialog(null, "Incorrect data from Employee's family state!");
                return false;
            }
        }

        return true;
    }

    public static boolean check_correct_phoneNumber(String phoneNumber){
        if (phoneNumber.length()==0 || !phoneNumber.matches("[0-9]+") ||  Integer.parseInt(phoneNumber) > 2147483647){
            JOptionPane.showMessageDialog(null, "Incorrect data from Employee's phoneNumber!");
            return false;
        }
        return true;
    }

    public static boolean check_rest_info(String firstName, String lastName, String username, String password, String address, String bankName){
        if (firstName.length() == 0 || lastName.length() == 0 || username.length() == 0
                || password.length() == 0 || address.length() == 0 || bankName.length() == 0){
            JOptionPane.showMessageDialog(null, "Not given data for Employye's information!");
            return false;
        }
        return true;
    }

    public static void displayHire() throws SQLException {
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
        Object[] message = {
                "First Name:", firstName,
                "Last Name:", lastName,
                "Username: ", username,
                "Password: ", password,
                "Address: ", address,
                "Phone number: ", phoneNumber,
                "Group Department: ", groupEmployer,
                "Job Department: ", jobDepartment,
                "Married: ", married,
                "Kids: ", kids,
                "Kids' Ages (With Commas!)", kidsAgesWithCommas,
                "IBAN:", IBAN,
                "Bank name:", bankName
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Hire", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (!check_correct_family_state(kidsAgesWithCommas.getText(),kids.getText(),married.getText())
                || !check_correct_IBAN_category(IBAN.getText(), groupEmployer.getText(), jobDepartment.getText())
                || !check_rest_info(firstName.getText(),lastName.getText(),username.getText(),password.getText(),address.getText(),bankName.getText())
                || !check_correct_phoneNumber(phoneNumber.getText()))
                displayProcedures(proceduresFrame,false);
            else {
                int bankId = request.insertBankInfo(Integer.parseInt(IBAN.getText()),bankName.getText());
                int bonusId = request.insertBonus(0.15, searchBonus, libraryBonus); //calculateFamilyBonus instead of 0.15
                int stateId = request.insertFamilyState(married.getText(),Integer.parseInt(kids.getText()),kidsAgesWithCommas.getText());

                int[] infoInt = {Integer.parseInt(phoneNumber.getText()),bankId,stateId,bonusId};
                String[] infoStr = {firstName.getText(),lastName.getText(),address.getText()};

                if (groupEmployer.getText().equals("Permanent"))
                    employeeId = request.insertEmployee(infoStr,currentDate, infoInt, basicSALARY);
                else
                    employeeId = request.insertEmployee(infoStr,currentDate, infoInt, contractSALARY);

                request.hireEmployee(employeeId, "Contractor", "Educator", username.getText(), password.getText());
            }
        } else {
            loginPage();
        }

    }

    public static void displayFireRetire() throws NumberFormatException, SQLException {
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

    public static void displayChangeInfo() throws SQLException {
        JTextField address = new JTextField();
        JTextField phoneNumber = new JTextField();
        JTextField married = new JTextField();
        JTextField kids = new JTextField();
        JTextField kidsAgesWithCommas = new JTextField();
        Object[] message = {
                "Address: ", address,
                "Phone number: ", phoneNumber,
                "Married: ", married,
                "Kids: ", kids,
                "Kids' Ages (With Commas!)", kidsAgesWithCommas
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (address.getText().length() != 0) request.changeAddress(address.getText(), employeeId);
            if (!check_correct_phoneNumber(phoneNumber.getText())) request.changePhoneNumber(Integer.parseInt(phoneNumber.getText()), employeeId);
            if (!check_correct_family_state(kidsAgesWithCommas.getText(), kids.getText(), married.getText()))
                request.changeFamilyState(married.getText(), Integer.parseInt(kids.getText()), kidsAgesWithCommas.getText(), employeeId, basicSALARY, contractSALARY);
        } else {
            loginPage();
        }

    }

    public static void displayChangeSalaryBonuses() throws SQLException {
        JTextField searchBonus = new JTextField();
        JTextField libraryBonus = new JTextField();
        JTextField basicSalary = new JTextField();
        JTextField contractSalary = new JTextField();
        Object[] message = {
                "Search Bonus:", searchBonus,
                "Library Bonus:", libraryBonus,
                "Basic Salary: ", basicSalary,
                "Contract Salary: ", contractSalary,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Change Employee Info", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // firstName.getText() etc get your info
        } else {
            loginPage();
        }

    }
}