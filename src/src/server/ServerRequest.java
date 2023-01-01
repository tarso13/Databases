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
        PermanentEducator pEducator = null;
        if (resultPE.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else
            pEducator = new PermanentEducator(resultEmployee.getString("firstName"),
                    resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                    resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"));
        return pEducator;
    }

    public ArrayList<Employee> sortSalaryperStaffCategoryPE(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Permanent") && SalariesperCategory.get(i).getString("department").equals("Education")) {
                PermanentManager PE = new PermanentManager(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("PMId"));
                sortedSalaryperStaffCategory.add(PE);
            }
        }

        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> sortSalaryperStaffCategoryPM(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Permanent") && SalariesperCategory.get(i).getString("department").equals("Management")) {
                PermanentManager PM = new PermanentManager(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("PEId"));
                sortedSalaryperStaffCategory.add(PM);
            }
        }

        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> sortSalaryperStaffCategoryCM(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
        for (int i = 0; i < SalariesperCategory.size(); ++i) {
            if (SalariesperCategory.get(i).getString("group").equals("Contractor") && SalariesperCategory.get(i).getString("department").equals("Management")) {
                ContractorManager CM = new ContractorManager(SalariesperCategory.get(i).getString("firstName"),
                        SalariesperCategory.get(i).getString("lastName"), SalariesperCategory.get(i).getString("address"),
                        SalariesperCategory.get(i).getInt("phoneNumber"), SalariesperCategory.get(i).getDate("beginHiringDate"), SalariesperCategory.get(i).getInt("CMId"));
                sortedSalaryperStaffCategory.add(CM);
            }
        }

        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> sortSalaryperStaffCategoryCE(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
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

    public ArrayList<Employee> sortSalaryperStaffCategory(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {

        sortedSalaryperStaffCategory = sortSalaryperStaffCategoryPM(SalariesperCategory, sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = sortSalaryperStaffCategoryPE(SalariesperCategory, sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = sortSalaryperStaffCategoryCM(SalariesperCategory, sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = sortSalaryperStaffCategoryCE(SalariesperCategory, sortedSalaryperStaffCategory);

        return sortedSalaryperStaffCategory;
    }

    public int getMinSalary(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> SalaryCat) throws SQLException {
        SalaryCat = sortSalaryperStaffCategoryPM(SalariesperCategory, SalaryCat);
        int minSalary = SalaryCat.get(0).getEmployeesSalary().getSalary();
        for (int i = 0; i < SalaryCat.size(); ++i) {
            if (SalaryCat.get(i).getEmployeesSalary().getSalary() < minSalary)
                minSalary = SalaryCat.get(i).getEmployeesSalary().getSalary();
        }
        return minSalary;
    }

    public int getMaxSalary(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> SalaryPM) throws SQLException {
        SalaryPM = sortSalaryperStaffCategoryPM(SalariesperCategory, SalaryPM);
        int maxSalary = -1;
        for (int i = 0; i < SalaryPM.size(); ++i) {
            if (SalaryPM.get(i).getEmployeesSalary().getSalary() > maxSalary)
                maxSalary = SalaryPM.get(i).getEmployeesSalary().getSalary();
        }
        return maxSalary;
    }

    public int getAverageSalary(ArrayList<ResultSet> SalariesperCategory, ArrayList<Employee> SalaryCat) throws SQLException {
        SalaryCat = sortSalaryperStaffCategoryPM(SalariesperCategory, SalaryCat);
        int averageSalary = 0;
        for (int i = 0; i < SalaryCat.size(); ++i)
            averageSalary += SalaryCat.get(i).getEmployeesSalary().getSalary();

        return averageSalary / SalaryCat.size();
    }

    public ArrayList<Integer> getSalaryStatisticsperCategory() throws SQLException {
        int EmployeeId = 0;
        ArrayList<ResultSet> Employees = new ArrayList<>();
        ArrayList<Employee> sortedSalaryEmployees = new ArrayList<>();
        ArrayList<Integer> sortedSalaries = new ArrayList<>();

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        //Employees.add(resultEmployee);
        while (resultEmployee.next())
            Employees.add(resultEmployee);

        sortedSalaryEmployees = sortSalaryperStaffCategoryPM(Employees, sortedSalaryEmployees);
        sortedSalaries.add(getMinSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getMaxSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getAverageSalary(Employees, sortedSalaryEmployees));

        sortedSalaryEmployees.clear();
        sortedSalaryEmployees = sortSalaryperStaffCategoryPE(Employees, sortedSalaryEmployees);
        sortedSalaries.add(getMinSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getMaxSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getAverageSalary(Employees, sortedSalaryEmployees));

        sortedSalaryEmployees.clear();
        sortedSalaryEmployees = sortSalaryperStaffCategoryCM(Employees, sortedSalaryEmployees);
        sortedSalaries.add(getMinSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getMaxSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getAverageSalary(Employees, sortedSalaryEmployees));

        sortedSalaryEmployees.clear();
        sortedSalaryEmployees = sortSalaryperStaffCategoryCE(Employees, sortedSalaryEmployees);
        sortedSalaries.add(getMinSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getMaxSalary(Employees, sortedSalaryEmployees));
        sortedSalaries.add(getAverageSalary(Employees, sortedSalaryEmployees));

        return sortedSalaries;
    }

    public ArrayList<Employee> getSalaryperStaffCategory() throws SQLException {
        int EmployeeId = 0;
        ArrayList<ResultSet> SalariesperCategory = new ArrayList<>();
        ArrayList<Employee> sortedSalaryperStaffCategory = new ArrayList<>();

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        //SalariesperCategory.add(resultEmployee);
        while (resultEmployee.next())
            SalariesperCategory.add(resultEmployee);

        sortedSalaryperStaffCategory = sortSalaryperStaffCategory(SalariesperCategory, sortedSalaryperStaffCategory);
        return sortedSalaryperStaffCategory;
    }

    public EmployeesSalary getEmployeeSalaryData(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        ResultSet resultES = null;
        EmployeesSalary employeesSalary = null;
        if (resultEmployee.next() == false)
            System.out.println("Empty Result Set");
        else {
            PreparedStatement statement2 = selectStatement(connector,
                    "SELECT * FROM EmployeesSalary where EmployeeId=?");
            statement2.setInt(1, EmployeeId);
            resultES = statement2.executeQuery();
            if (resultES.next() == false)
                System.out.println("Empty Result Set");
            employeesSalary = new EmployeesSalary(resultES.getInt("basicSalary"), resultES.getInt("contractSalary"), resultES.getInt("salaryId"));
        }
        return employeesSalary;
    }
}