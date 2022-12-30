package server;

import classes.PermanentEducator;
import classes.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerRequest {
    Connector connector;

    public ServerRequest() {
        connector = new Connector();
    }

    public PreparedStatement selectStatement(Connector c, String q) throws SQLException {
        return c.getConnection().prepareStatement(q, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    public void hirePM(int PMId, int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO PermanentManager (PMId, EmployeeId,username,password) VALUES (?,?,?,?)");
        statement1.setInt(1, PMId);
        statement1.setInt(2, EmployeeId);
        statement1.setString(3, "PMId");
        statement1.setString(4, "PMId");
        statement1.execute();
    }

    public PermanentEducator getPE(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentEducator where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        resultPE.next();

        PermanentEducator pEducator = new PermanentEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"));
        return pEducator;
    }

    public ArrayList<Employee> sortSalaryperStaffCategory(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Permanent") && SalariesperCategory.get(i).getString("department").equals("Management")) {
                PermanentManager PM = new PermanentManager(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("PMId"));
                sortedSalaryperStaffCategory.add(PM);
            }
        }

        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Permanent") && SalariesperCategory.get(i).getString("department").equals("Education")) {
                PermanentEducator PE = new PermanentEducator(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("PEId"));
                sortedSalaryperStaffCategory.add(PE);
            }
        }

        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Contractor") && SalariesperCategory.get(i).getString("department").equals("Management")) {
                ContractorManager CM = new ContractorManager(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("CMId"));
                sortedSalaryperStaffCategory.add(CM);
            }
        }

        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Contractor") && SalariesperCategory.get(i).getString("department").equals("Education")) {
                ContractorEducator CE = new ContractorEducator(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("CEId"));
                sortedSalaryperStaffCategory.add(CE);
            }
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> getSalaryperStaffCategory() throws SQLException {
        int EmployeeId = 0;
        ArrayList<ResultSet> SalariesperCategory = new ArrayList<>();
        ArrayList<Employee> sortedSalaryperStaffCategory = new ArrayList<>();

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        SalariesperCategory.add(resultEmployee);
        resultEmployee.next();

        sortedSalaryperStaffCategory = sortSalaryperStaffCategory(SalariesperCategory, sortedSalaryperStaffCategory);
        return sortedSalaryperStaffCategory;
    }

    public EmployeesSalary getEmployeeSalaryData(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId='" + EmployeeId + "'");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM EmployeesSalary where EmployeeId='" + EmployeeId + "'");
        statement2.setInt(1, EmployeeId);
        ResultSet resultES = statement2.executeQuery();
        resultES.next();

        EmployeesSalary employeesSalary = new EmployeesSalary(resultES.getInt("basicSalary"), resultES.getInt("contractSalary"), resultES.getInt("salaryId"));
        return employeesSalary;
    }
}