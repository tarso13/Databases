import classes.*;
import server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        EmployeesSalary salary = new EmployeesSalary(1500,600,0,0);
        salary.setInitialSalary(1500,600);

        Bonus bonus = new Bonus(0,0.5,0.10,0);
        //request.login("username","password");
        //request.changeAddress("aaaaa",1);
        //request.changePhoneNumber(21022222,15);
        //request.changeFamilyState("married",3,"15 15 18", 5,3,salary);
        PermanentEducator permanentEducator= new PermanentEducator("firstName", "lastName", "address", 1234, new Date(2022-1-1),25);
        permanentEducator.setEmployeesSalary(salary);
        permanentEducator.setBonus(bonus);
        BankInfo bankInfo = request.insertBankInfo(873833838,"alpha");
        FamilyState state = request.insertFamilyState("unmarried",0,"",salary);
        request.hirePermanentEmployee(permanentEducator,"aaaa","bbb",bankInfo,state);

    }
}
