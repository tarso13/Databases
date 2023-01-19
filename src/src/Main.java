package src;

import src.server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main {
    static ServerRequest request;
    static double libraryBonus = 300.15; //only for CE employees
    static double searchBonus = 250.82; //only for PE employees/CE
    static double basicSalary = 1500; //only for PE/PM employees
    static double contractSalary = 600; //only for CE/CM employees
    static boolean allPaid = false;
    static Date currentDate;

    public static void main(String[] args) throws SQLException, ParseException {
        request = new ServerRequest();

        currentDate = Date.valueOf("2023-02-01");
        //int id=request.login("15","15a");

        /**
         * Change each initial salary in actionListener of displayChangeBonusSalaryPage
         * basicSalary = 1600;
         * contractSalary = 2000;
         * request.changeLibraryBonus(500); //in GUI in changeBonusSalary
         * request.changeSearchBonus(600); //in GUI in changeBonusSalary
         */

        currentDate = request.payEmployees(currentDate.toString(),basicSalary,contractSalary);

        /**
         * In change bonus salary, do
         * request.changSalary(Integer.parseInt(dateString[0]),basicSalary,contractSalary);
         */

        /** HIRE STATEMENT
         * if (31/XX/XX and paidAll=false) payALL before you hire
         * In hireEmployee, make searchBonus == 0 || libraryBonus == 0
         * set beginHiringDate as new Date(currentDate.year()-currentDate.month()-01)
         * currentDate 1
         * paidAll = false
         */

        /**
         * in changeInfo when call
         * request.changeFamilyState(String state, int kids, String ages, int stateId, int EmployeeId);
         * request.changeSalary(Integer.parseInt(dateString[0]),basicSalary,contractSalary);
         * */

        /**     CHECKED
         * When pay Employees,
         * if (paidAll == true) NEXT DAY: XXXX/XX/01
         * pay Employees first
         * set current date Month+1, if (month>12) then year+1
         * 31/XX/XXXX
         *
         * paidAll = true; in gui file
         */

        /**
         * CAN ONLY DELECT ALL INFO IN DATABASE;
         * if (PAIDALL==true) employeeId has already been paid
         */
    }
}
