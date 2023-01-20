package src;

import src.classes.ContractorEducator;
import src.classes.ContractorManager;
import src.classes.PermanentEducator;
import src.classes.PermanentManager;
import src.classes.Employee;

import src.gui.GUI;
import src.server.ServerRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        GUI.loginPage();
    }
}