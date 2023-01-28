package src;

import src.gui.GUI;
import src.server.ServerRequest;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        int id = request.loginEmployee("15","15a");
        if (id!=-1) request.changePhoneNumber(34567,id);
        if (id!=-1) request.changeAddress("aa34567",id);
        if (id!=-1) request.changeFamilyState("married",3,"12,13,14",id);
        request.changeLibraryBonus(400);
        //GUI.loginPage();
    }
}