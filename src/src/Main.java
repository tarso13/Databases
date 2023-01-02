import classes.Employee;
import classes.EmployeesSalary;
import classes.PermanentEducator;
import gui.GUI;
import server.ServerRequest;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    static ServerRequest request;

    public static void main(String[] args) throws SQLException {

        // GUI.loginPage();
        request = new ServerRequest();

        Employee employee = request.getEmployeeSalaryData(2);
        System.out.println(employee.getFirstName() + " " + employee.getLastName() + employee.getEmployeesSalary());
//        ArrayList<Employee> getSalaryperStaffCategory = request.getSalaryperStaffCategory();
//        for (int i = 0; i < getSalaryperStaffCategory.size(); ++i)
//            System.out.println(getSalaryperStaffCategory.get(i).JobDepartment() + " " + getSalaryperStaffCategory.get(i).groupEmployer() + " " + getSalaryperStaffCategory.get(i).getEmployeesSalary());
    }
}