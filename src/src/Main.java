import classes.PermanentEducator;
import gui.GUI;
import server.ServerRequest;

import java.sql.SQLException;

public class Main {
    static ServerRequest request;
    public static void main(String[] args) throws SQLException {

       // GUI.loginPage();
        request = new ServerRequest();

        //PermanentEducator permanentEducator = request.getPE(5);
        //System.out.println(permanentEducator.toString());
    }
}