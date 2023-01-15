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
        return c.getConnection().prepareStatement(q, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    public void hirePM(int PMId, int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "INSERT INTO PermanentManager (PMId, EmployeeId,username,password) VALUES (?,?,?,?)");
        statement1.setInt(1, PMId);
        statement1.setInt(2, EmployeeId);
        statement1.setString(3, "PMId");
        statement1.setString(4, "PMId");
        statement1.execute();
    }

    public PermanentEducator getPE(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();
        PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM PermanentEducator where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        PermanentEducator pEducator = null;
        if (resultPE.next() == false) {
            System.out.println("ResultSet in empty in Java");
        } else
            pEducator = new PermanentEducator(resultEmployee.getString("firstName"), resultEmployee.getString("lastName"), resultEmployee.getString("address"), resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"), resultEmployee.getInt("EmployeeId"));
        return pEducator;
    }


    public ArrayList<Employee> addPESalaries(ArrayList<Employee> sortedSalaryperStaffCategory) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM PermanentEducator");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false) {
            return sortedSalaryperStaffCategory;
        } else {
            ResultSet resultPE;
            int EmployeeId;
            PermanentEducator pEducator;
            do {
                do {
                    EmployeeId = resultEmployee.getInt("EmployeeId");
                    PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
                    statement2.setInt(1, EmployeeId);
                    resultPE = statement2.executeQuery();
                    if (resultPE.next() == false) {
                        return sortedSalaryperStaffCategory;
                    } else {
                        pEducator = new PermanentEducator(resultPE.getString("firstName"), resultPE.getString("lastName"), resultPE.getString("address"), resultPE.getInt("phoneNumber"), resultPE.getDate("beginHiringDate"), resultEmployee.getInt("PEId"), resultEmployee.getInt("EmployeeId"));
                        sortedSalaryperStaffCategory.add(pEducator);

                    }
                } while (resultPE.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public int getSalary(int EmployeeId, String category) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM EmployeesSalary where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (resultEmployee.next() == false)
            return -1;

        int BonusId = resultEmployee.getInt("BonusId");
        PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Bonus where BonusId=?");
        statement2.setInt(1, BonusId);
        ResultSet resultBonus = statement2.executeQuery();
        if (resultBonus.next() == false)
            return -1;

        int salary = (category.equals("PM") || category.equals("PE")) ? resultEmployee.getInt("basicSalary") : resultEmployee.getInt("contractSalary");
        int final_salary = 0;
//        final_salary += FamilyBonus;
        final_salary += resultBonus.getDouble("libraryBonus");
        final_salary += resultBonus.getDouble("searchBonus");
        return final_salary;
    }


    public ArrayList<Employee> addPMSalaries(ArrayList<Employee> sortedSalaryperStaffCategory) throws
            SQLException {

        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM PermanentManager");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false) {
            return sortedSalaryperStaffCategory;
        } else {
            ResultSet resultPM;
            int EmployeeId;
            PermanentManager pManager;
            do {
                do {
                    EmployeeId = resultEmployee.getInt("EmployeeId");
                    PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
                    statement2.setInt(1, EmployeeId);
                    resultPM = statement2.executeQuery();
                    if (resultPM.next() == false) {
                        return sortedSalaryperStaffCategory;
                    } else {
                        pManager = new PermanentManager(resultPM.getString("firstName"), resultPM.getString("lastName"), resultPM.getString("address"), resultPM.getInt("phoneNumber"), resultPM.getDate("beginHiringDate"), resultEmployee.getInt("PMId"), resultEmployee.getInt("EmployeeId"));
                        sortedSalaryperStaffCategory.add(pManager);
                    }
                } while (resultPM.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> addCMSalaries(ArrayList<Employee> sortedSalaryperStaffCategory) throws
            SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM ContractorManager");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false) return sortedSalaryperStaffCategory;
        else {
            ResultSet resultCM;
            int EmployeeId;
            ContractorManager cManager;
            do {
                EmployeeId = resultEmployee.getInt("EmployeeId");
                PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
                statement2.setInt(1, EmployeeId);
                resultCM = statement2.executeQuery();
                do {
                    if (resultCM.next() == false) {
                        return sortedSalaryperStaffCategory;
                    } else {
                        cManager = new ContractorManager(resultCM.getString("firstName"), resultCM.getString("lastName"), resultCM.getString("address"), resultCM.getInt("phoneNumber"), resultCM.getDate("beginHiringDate"), resultEmployee.getInt("CMId"), resultEmployee.getInt("EmployeeId"));
                        sortedSalaryperStaffCategory.add(cManager);
                    }
                } while (resultCM.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> addCESalaries(ArrayList<Employee> sortedSalaryperStaffCategory) throws
            SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM ContractorEducator");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false) return sortedSalaryperStaffCategory;
        else {
            ResultSet resultCE;
            int EmployeeId;
            ContractorEducator cEducator;
            do {
                EmployeeId = resultEmployee.getInt("EmployeeId");
                PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
                statement2.setInt(1, EmployeeId);
                resultCE = statement2.executeQuery();
                do {
                    if (resultCE.next() == false) {
                        return sortedSalaryperStaffCategory;
                    } else {
                        cEducator = new ContractorEducator(resultCE.getString("firstName"), resultCE.getString("lastName"), resultCE.getString("address"), resultCE.getInt("phoneNumber"), resultCE.getDate("beginHiringDate"), resultEmployee.getInt("CEId"), resultEmployee.getInt("EmployeeId"));
                        sortedSalaryperStaffCategory.add(cEducator);
                    }
                } while (resultCE.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    private ArrayList<Employee> defineCategory(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        switch (Category) {
            case "PM":
                SalaryCat = addPMSalaries(SalaryCat);
                break;
            case "PE":
                SalaryCat = addPESalaries(SalaryCat);
                break;
            case "CM":
                SalaryCat = addCMSalaries(SalaryCat);
                break;
            case "CE":
                SalaryCat = addCESalaries(SalaryCat);
                break;
            default:
                assert (false);
        }
        return SalaryCat;
    }


    public int getMinSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        int minSalary = getSalary(SalaryCat.get(0).getEmployeeId(), Category);
        //alternative to: SalaryCat.get(0).getEmployeesSalary().getSalary();
        for (int i = 1; i < SalaryCat.size(); ++i) {
            //if (SalaryCat.get(i).getEmployeesSalary().getSalary() < minSalary)
            //    minSalary = SalaryCat.get(i).getEmployeesSalary().getSalary();
            //if this is not set then alternative:
            int salary = getSalary(SalaryCat.get(0).getEmployeeId(), Category);
            if (salary < minSalary)
                minSalary = salary;

        }
        return minSalary;
    }

    public int getMaxSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        int maxSalary = -1;
        int salary = 0;
        for (int i = 0; i < SalaryCat.size(); ++i) {
//            if (SalaryCat.get(i).getEmployeesSalary().getSalary() > maxSalary)
//                maxSalary = SalaryCat.get(i).getEmployeesSalary().getSalary();
            //if this is not set then alternative:
            salary = getSalary(SalaryCat.get(0).getEmployeeId(), Category);
            if (salary > maxSalary)
                maxSalary = salary;

        }
        return maxSalary;
    }

    public int getAverageSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        int averageSalary = 0;
        for (int i = 0; i < SalaryCat.size(); ++i)
            // averageSalary += SalaryCat.get(i).getEmployeesSalary().getSalary();
            averageSalary += getSalary(SalaryCat.get(i).getEmployeeId(), Category);
        return averageSalary / SalaryCat.size();
    }

    public ArrayList<Integer> getSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Integer> sortedSalaries = new ArrayList<>();
        ArrayList<Employee> Employees = new ArrayList<>();

        sortedSalaries.add(getMinSalary(Employees, "PM"));
        sortedSalaries.add(getMaxSalary(Employees, "PM"));
        sortedSalaries.add(getAverageSalary(Employees, "PM"));

        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "PE"));
        sortedSalaries.add(getMaxSalary(Employees, "PE"));
        sortedSalaries.add(getAverageSalary(Employees, "PE"));

        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "CM"));
        sortedSalaries.add(getMaxSalary(Employees, "CM"));
        sortedSalaries.add(getAverageSalary(Employees, "CM"));

        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "CE"));
        sortedSalaries.add(getMaxSalary(Employees, "CE"));
        sortedSalaries.add(getAverageSalary(Employees, "CE"));

        return sortedSalaries;
    }

    public ArrayList<Employee> getSalaryperStaffCategory() throws SQLException {
        ArrayList<Employee> sortedSalaryperStaffCategory = new ArrayList<>();

        sortedSalaryperStaffCategory = addPMSalaries(sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = addPESalaries(sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = addCMSalaries(sortedSalaryperStaffCategory);
        sortedSalaryperStaffCategory = addCESalaries(sortedSalaryperStaffCategory);

        return sortedSalaryperStaffCategory;
    }

    public Employee getEmployeeSalaryData(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (resultEmployee.next() == false) return null;

        Employee result = getPE(EmployeeId);
//   Implementations tis Konstantinas ta gets apo edw kai katw otan einai etoima uncomment
//        if (result == null)
//            result = getPM(EmployeeId);
//        if (result == null)
//            result = getCM(EmployeeId);
//        if (result == null)
//            result = getCE(EmployeeId);

        PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM EmployeesSalary where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultES = statement2.executeQuery();
        if (resultES.next() == false)
            System.out.println("[FATAL!] EMPLOYEE COULD NOT BE VERIFIED IN EMPLOYEESSALARY FROM DB");

        return result;
    }
}