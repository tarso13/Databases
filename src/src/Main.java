import classes.*;
import server.ServerRequest;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {
        request = new ServerRequest();

        int id=request.login("user","pass111");
        System.out.println(id);

//        EmployeesSalary salary = new EmployeesSalary(1500,600,0,0);
//        salary.setInitialSalary(1500,600);

//        Bonus bonus = new Bonus(0,0.5,0.10,0);
//        request.login("username","password");
//        /*Change info related to the same user with username-password*/
//        request.changeAddress("bbbb",1);
//        request.changePhoneNumber(2133333,1);
        //request.changeFamilyState("married",3,"15 15 18", 5,3,salary);
//        PermanentEducator permanentEducator= new PermanentEducator("firstName", "lastName", "address", 1234, new Date(2022-1-1),25);
//        permanentEducator.setEmployeesSalary(salary);
//        permanentEducator.setBonus(bonus);
//        BankInfo bankInfo = request.insertBankInfo(873833838,"alpha");
//        FamilyState state = request.insertFamilyState("unmarried",0,"",salary);
//        request.hirePermanentEmployee(permanentEducator,"aaaa","bbb",bankInfo,state);

    }
}
