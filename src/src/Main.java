import classes.*;
import server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;
    static double libraryBonus = 300.15;
    static double searchBonus = 250.82;
    static double basicSalary = 1500;
    static double contractSalary = 600;
    static Date currentdate = new Date(2000-1-1);

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        int id=request.login("15","15a");

          request.changeAddress("hulk",id);
          request.changePhoneNumber(21212121,id);
          request.changeFamilyState("unmarried",0,"", 5,3,basicSalary);



    }
}
