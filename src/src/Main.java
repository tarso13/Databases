import classes.ContractorEducator;
import classes.ContractorManager;
import classes.PermanentEducator;
import classes.PermanentManager;
import classes.Employee;

import gui.GUI;
import server.ServerRequest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) {
        request = new ServerRequest();

        GUI.loginPage();
    }
}