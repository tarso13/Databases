import server.ServerRequest;
import util.HelperFunctions;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;
    static double libraryBonus = 300.15; //only for CE employees
    static double searchBonus = 250.82; //only for PE employees
    static double basicSalary = 1500; //only for PE/PM employees
    static double contractSalary = 600; //only for CE/CM employees
    static Date currentDate = new Date(2000-1-1);

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        /*test login Employee*/
        int id=request.login("15","15a");

        /*test change Employee's info, such as address, phoneNumber and FamilyState*/
        request.changeAddress("hulk",id);
        request.changePhoneNumber(21212121,id);
        request.changeFamilyState("unmarried",0,"", 5,3,basicSalary);

        /*test hire Employee*/
        HelperFunctions helperFunctions = new HelperFunctions();
        int employeeId = helperFunctions.hireEmployee("Contractor", "Educator", "ce", "ce");
        int bankId = request.insertBankInfo(666,"fi");
        int bonusId = request.insertBonus(0.1, searchBonus, libraryBonus); //calculateFamilyBonus instead of 0.15
        int stateId = request.insertFamilyState("married",1,"14");

        int[] infoInt = {222222,bankId,stateId,bonusId};
        String[] infoStr = {"Rafaela","Mari","Syntagma"};

        //basicSalary as an example, it will be the result of calculateSalary
        request.insertEmployee(employeeId, infoStr,currentDate, infoInt, basicSalary);
    }
}
