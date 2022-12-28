package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.System.exit;

public class GUI {
    private static void displayProcedures() {
        JPanel panel;
        JButton[] buttons = new JButton[8];
        panel = new JPanel(new GridLayout(1, 8));
        String b[] = {"New Hire", "New Contract", "Change Employee Info", "Change Salary/Bonus", "New Fire/Retirement", "Payment", "Questions", "Statistics"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(b[i]);
            buttons[i].setSize(80, 80);
            buttons[i].setActionCommand(b[i]);
            buttons[i].addActionListener(e -> {
                String choice = e.getActionCommand();
                JOptionPane.showMessageDialog(null, "You have clicked: " + choice);
                exit(1);
            });
            panel.add(buttons[i]);
        }

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
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
