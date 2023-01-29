package src;

import src.gui.GUI;
import src.server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();
        GUI.loginPage();
    }
}