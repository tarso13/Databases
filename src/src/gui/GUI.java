package gui;

import javax.swing.*;

public class GUI {

    private static void displayProcedures() {
        String[] options = {"New Hire", "New Contract", "Change Employee Info", "Change Salary/Bonus", "New Fire/Retirement","Payment", "Questions", "Statistics"};
        int ret = JOptionPane.showOptionDialog(null, "Choose one of the availabe options",
                "Procedures",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

    }

    public static void login_page() {
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
                //System.out.println("Login successful");
                displayProcedures();
            } else
                System.out.println("login failed");

        } else
            System.out.println("Login cancelled");

    }
}
