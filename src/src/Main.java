package src;

import src.gui.GUI;
import src.server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        //TODO fix AVG()==0.0 all the time
        //System.out.println(request.getAverageSalaryBonusIncrease(Date.valueOf("2020-3-12"),Date.valueOf("2023-3-19"),"raiseSearchBonus"));
        GUI.loginPage();
    }
}