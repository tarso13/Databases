import classes.ContractorEducator;
import classes.ContractorManager;
import classes.PermanentEducator;
import classes.PermanentManager;
import server.ServerRequest;

import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        PermanentEducator permanentEducator = request.getPE(5);
        System.out.println(permanentEducator.toString());

        request.hirePM(10,12);

//        PermanentManager permanentManager = request.getPM(1);
//        System.out.println(permanentManager.toString());

//        ContractorEducator contractorEducator = request.getCE(7);
//        System.out.println(contractorEducator.toString());

//        ContractorManager contractorManager = request.getCM(6);
//        System.out.println(contractorManager.toString());
    }
}