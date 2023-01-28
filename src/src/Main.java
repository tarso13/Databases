package src;

import src.gui.GUI;
import src.server.ServerRequest;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    static ServerRequest request;
    static double libraryBONUS = 300.15; //only for CE employees
    static double searchBONUS = 250.82; //only for PE employees/CE
    static double basicSALARY = 1500; //only for PE/PM employees
    static double contractSALARY = 600; //only for CE/CM employees


    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        //GUI.loginPage();
    }
}