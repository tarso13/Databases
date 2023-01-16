import com.mysql.cj.exceptions.DataReadException;
import server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Main {
    static ServerRequest request;
    static double libraryBonus = 300.15; //only for CE employees
    static double searchBonus = 250.82; //only for PE employees/CE
    static double basicSalary = 1500; //only for PE/PM employees
    static double contractSalary = 600; //only for CE/CM employees
    static Date currentDate = new Date(1990-10-10);

    /* CHECK GIVEN INFO FROM GUI:
     * TO CHANGE FAMILY STATE:
     * Split ages into spaces
     * String[] split = getText().split(" ");
     * if (split.size() != kids || (kids !=0 && state=="unmarried") || (kids ==0 && state=="married")) System.out.println("Error giving correct number of ages!");
     *
     * int kids = Integer.parseString(getText());
     * if (!getText().matches("[0-9]+") ||  kids > 6)) System.out.println("Error giving kids!");
     *
     * for (int i=0; i<kids; i++){
     *     if (split[i] <= 0 || split[i] >=100) System.out.println("Error giving correct number of ages!");
     * }
     *
     * TO CHANGE ADDRESS:
     * int number = Integer.parseString(getText());
     * if (!getText().matches("[0-9]+") ||  number > 2147483647)) System.out.println("Error giving number!");
     *
     * TO HIRE EMPLOYEES:
     * check address/iban, familystate as above
     * */

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();
        //Date date = new Date(1990-10-10);
        //request.updateDate(currentDate);
        //System.out.println(currentDate);

        /*test login Employee*/
        int id=request.login("15","15a");

        /*test change Employee's info, such as address, phoneNumber and FamilyState*/
        request.changeAddress("hulk",id);
        request.changePhoneNumber(21212121,id);
        request.changeFamilyState("unmarried",0,"", 5,3,basicSalary);

        /*test hire Employee*/
        //basicSalary as an example, it will be the result of calculateSalary
        int bankId = request.insertBankInfo(666,"fi");
        int bonusId = request.insertBonus(0.1, searchBonus, libraryBonus); //calculateFamilyBonus instead of 0.15
        int stateId = request.insertFamilyState("married",1,"14");

        int[] infoInt = {222222,bankId,stateId,bonusId};
        String[] infoStr = {"Rafaela","Mari","Syntagma"};
        int employeeId = request.insertEmployee(infoStr,currentDate, infoInt, basicSalary);

        request.hireEmployee(employeeId, "Contractor", "Educator", "ce", "ce");
    }
}
