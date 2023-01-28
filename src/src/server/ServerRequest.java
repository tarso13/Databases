package src.server;

import src.classes.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerRequest {
    src.server.Connector connector;

    public ServerRequest() {
        connector = new src.server.Connector();
    }

    public PreparedStatement selectStatement(src.server.Connector c, String q) throws SQLException {
        return c.getConnection().prepareStatement(q, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    public double convert_to_2_Decimals(double value){
        if ((BigDecimal.valueOf(value).scale() > 2) == false) return value;
        return new BigDecimal(value).setScale(2, RoundingMode.DOWN).doubleValue();
    }

    /*login Employee with username password*/
    public int loginEmployee(String userName, String password) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT password,LoginId FROM LoginInfo WHERE username=?");
        statement.setString(1, userName);
        ResultSet result = statement.executeQuery();
        if (!result.next()) return -1;
        String pass = result.getString("password");
        if (!password.equals(pass)) return -1;

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT EmployeeId FROM Employee WHERE LoginId=?");
        statement1.setInt(1, result.getInt("LoginId"));
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();
        return resultEmployee.getInt("EmployeeId");
    }


    /*update information in base*/
    public void changeAddress(String address, int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET address=? WHERE EmployeeId=?");
        statement1.setString(1, address);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changePhoneNumber(int number, int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET phoneNumber=? WHERE EmployeeId=?");
        statement1.setInt(1, number);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changeFamilyState(String state, int kids, String ages, int EmployeeId) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT StateID FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "UPDATE FamilyState SET state=?, numberKids=?, ages=? WHERE stateId=?");
        statement2.setString(1, state);
        statement2.setInt(2, kids);
        statement2.setString(3, ages);
        statement2.setInt(4, resultEmployee.getInt("StateId"));
        statement2.execute();

        changeFamilyBonus(state, ages, EmployeeId);
    }

    public double changeFamilyBonus(String state, String kidsAges, int employeeId) throws SQLException {
        double famBonus = calculateFamilyBonus(state, kidsAges);

        PreparedStatement statement = selectStatement(connector,
                "SELECT BonusId FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, employeeId);
        ResultSet resultBonus = statement.executeQuery();
        resultBonus.next();

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Bonus SET familyBonus=? WHERE BonusId=?");
        statement1.setDouble(1, famBonus);
        statement1.setInt(2, resultBonus.getInt("BonusId"));
        statement1.execute();

        return famBonus;
    }

    public void changeSearchBonus(double searchBonus) throws SQLException {
        searchBonus = convert_to_2_Decimals(searchBonus);

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Bonus");
        ResultSet resultBonus = statement.executeQuery();

        while (resultBonus.next()) {
            if (resultBonus.getInt("searchBonus") == 0) continue;

            PreparedStatement statement1 = selectStatement(connector,
                    "UPDATE Bonus SET searchBonus=? WHERE BonusId=?");
            statement1.setDouble(1, searchBonus);
            statement1.setInt(2, resultBonus.getInt("BonusId"));
            statement1.execute();
        }
    }

    public void changeLibraryBonus(double libraryBonus) throws SQLException {
        libraryBonus = convert_to_2_Decimals(libraryBonus);

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Bonus");
        ResultSet resultBonus = statement.executeQuery();

        while (resultBonus.next()) {
            if (resultBonus.getInt("libraryBonus") == 0) continue;

            PreparedStatement statement1 = selectStatement(connector,
                    "UPDATE Bonus SET libraryBonus=? WHERE BonusId=?");
            statement1.setDouble(1, libraryBonus);
            statement1.setInt(2, resultBonus.getInt("BonusId"));
            statement1.execute();
        }
    }

    /*Calculation of Family Bonus*/
    public double calculateFamilyBonus(String state, String kidsAges) {
        double famBonus = 0.0;

        if (state.equals("married")) famBonus += 0.05;

        String[] split = kidsAges.split(",");
        if (!split[0].equals("")) {
            for (int i = 0; i < split.length; i++) {
                if (Integer.parseInt(split[i]) < 18) famBonus += 0.05;
            }
        }

        famBonus = convert_to_2_Decimals(famBonus);
        return famBonus;
    }

    /*insert columns into tables*/
    public int insertEmployee(String[] infoStr, Date date, int[] infoInt) throws SQLException {
        //infoStr contains firstName, lastName, category, address
        //infoInt contains phoneNumber, LoginId, BankId, StateId, BonusId
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Employee (EmployeeId,firstName,lastName,beginHiringDate,category,address,phoneNumber,salary,active,LoginId,BankId,StateId,BonusId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, infoStr[0]);
        statement1.setString(3, infoStr[1]);
        statement1.setDate(4, date);
        statement1.setString(5, infoStr[2]);
        statement1.setString(6, infoStr[3]);
        statement1.setInt(7, infoInt[0]);
        statement1.setDouble(8, 0.0);
        statement1.setInt(9, 1);
        statement1.setInt(10, infoInt[1]);
        statement1.setInt(11, infoInt[2]);
        statement1.setInt(12, infoInt[3]);
        statement1.setInt(13, infoInt[4]);
        statement1.executeUpdate();

        ResultSet resultEmployee = statement1.getGeneratedKeys();
        if (!resultEmployee.next()) return -1;
        return resultEmployee.getInt(1);
    }

    public int insertFamilyState(String state, int numberKids, String ages) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO FamilyState (StateId,state,numberKids,ages) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, state);
        statement1.setInt(3, numberKids);
        statement1.setString(4, ages);
        statement1.executeUpdate();

        ResultSet resultState = statement1.getGeneratedKeys();
        if (!resultState.next()) return -1;
        return resultState.getInt(1);
    }

    public int insertBankInfo(int IBAN, String bankName) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO BankInfo (BankID,IBAN,bankName,timesPaid) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setInt(2, IBAN);
        statement1.setString(3, bankName);
        statement1.setInt(4, 0);
        statement1.executeUpdate();

        ResultSet resultBankInfo = statement1.getGeneratedKeys();
        if (!resultBankInfo.next()) return -1;
        return resultBankInfo.getInt(1);
    }

    public int insertBonus(double famBonus, double searchBonus, double libBonus, String category) throws SQLException {
        famBonus = convert_to_2_Decimals(famBonus);
        searchBonus = convert_to_2_Decimals(searchBonus);
        libBonus = convert_to_2_Decimals(libBonus);

        String[] split = category.split(" ");
        if (split[0].equals("Permanent") && split[1].equals("Educator")) {
            libBonus = 0.0;
        } else if (split[0].equals("Contractor") && split[1].equals("Manager")) {
            searchBonus = 0.0;
        } else {
            searchBonus = 0.0;
            libBonus = 0.0;
        }

        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Bonus (BonusId,familyBonus,searchBonus,libraryBonus) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setDouble(2, famBonus);
        statement1.setDouble(3, searchBonus);
        statement1.setDouble(4, libBonus);
        statement1.executeUpdate();

        ResultSet resultBonus = statement1.getGeneratedKeys();
        if (!resultBonus.next()) return -1;
        return resultBonus.getInt(1);
    }

    public void insertSalary(double newSalary, double oldSalary, int employeeId) throws SQLException {
        oldSalary = convert_to_2_Decimals(oldSalary);
        newSalary = convert_to_2_Decimals(newSalary);
        if (newSalary <= oldSalary) return;

        PreparedStatement statement = selectStatement(connector,
                "UPDATE Employee SET salary=? WHERE EmployeeId=?");
        statement.setDouble(1, newSalary);
        statement.setInt(2, employeeId);
        statement.execute();
    }

    public int insertLoginInfo(String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO LoginInfo (LoginId,username,password) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, username);
        statement1.setString(3, password);
        statement1.executeUpdate();

        ResultSet resultLogin = statement1.getGeneratedKeys();
        if (!resultLogin.next()) return -1;
        return resultLogin.getInt(1);
    }

    public double fireEmployee(int employeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET active=? WHERE EmployeeId=?");
        statement1.setInt(1, 0);
        statement1.setInt(2, employeeId);
        statement1.execute();

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, employeeId);
        ResultSet resultEmployee = statement.executeQuery();
        if (!resultEmployee.next()) return 0.0;

        double salary = resultEmployee.getDouble("salary");

        return salary;
    }

    public void changeSalary(String currentDate, double basicSalary, double contractSalary) throws SQLException {
        double newSalary = 0;

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee");
        ResultSet resultEmployee = statement1.executeQuery();

        while (resultEmployee.next()) {
            PreparedStatement statement2 = selectStatement(connector,
                    "SELECT * FROM Bonus WHERE BonusId=?");
            statement2.setInt(1, resultEmployee.getInt("BonusId"));
            ResultSet resultBonus = statement2.executeQuery();
            if (!resultBonus.next()) continue;

            if (resultEmployee.getInt("active") == 1){
                if (resultEmployee.getString("category").contains("Permanent"))
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), basicSalary, resultBonus.getDouble("familyBonus"), resultBonus.getDouble("searchBonus"), resultBonus.getDouble("libraryBonus"),true);
                else
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), contractSalary, resultBonus.getDouble("familyBonus"), resultBonus.getDouble("searchBonus"), resultBonus.getDouble("libraryBonus"),false);
            }

            insertSalary(newSalary, resultEmployee.getDouble("salary"), resultEmployee.getInt("EmployeeId"));
        }
    }

    public double estimateEmployeesSalary(String currentDate, Date date, double InitialSalary, double familyBonus, double searchBonus, double libraryBonus, boolean permanent) {
        String dateString = date.toString();
        String[] separateDate = dateString.split("-");
        String[] separateCurrentDate = currentDate.split("-");

        int oneMoreYear = (Integer.parseInt(separateCurrentDate[1]) > Integer.parseInt(separateDate[1]) ||
                          (Integer.parseInt(separateCurrentDate[1]) == Integer.parseInt(separateDate[1]) &&
                                  Integer.parseInt(separateCurrentDate[2]) >= Integer.parseInt(separateDate[2]))) ? 1 : 0;
        double newSalary = InitialSalary;

        if (permanent == true){
            int times15up = Integer.parseInt(separateCurrentDate[0]) - Integer.parseInt(separateDate[0]) + oneMoreYear - 1; //-1 to not count 1st year from hire
            newSalary += times15up * 0.15 * InitialSalary;
        }

        return convert_to_2_Decimals(newSalary + InitialSalary * familyBonus + searchBonus + libraryBonus);
    }

    public ArrayList<String> payEmployees(String currentDate, double basicSalary, double contractSalary) throws SQLException {
        changeSalary(currentDate,basicSalary,contractSalary);

        ArrayList<String> payments = new ArrayList<String>();

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee");
        ResultSet resultEmployee = statement.executeQuery();

        while (resultEmployee.next()) {
            PreparedStatement statement3 = selectStatement(connector,
                    "SELECT * FROM Bonus WHERE BonusId=?");
            statement3.setInt(1, resultEmployee.getInt("BonusId"));
            ResultSet resultBonus = statement3.executeQuery();
            resultBonus.next();


            payments.add("EmployeeId: " + resultEmployee.getInt("EmployeeId") + ", Category: " + resultEmployee.getString("category")
                    + " ,Salary: " + resultEmployee.getDouble("salary")
                    + " ,FamilyBonus: " + resultBonus.getDouble("familyBonus") + " ,SearchBonus: " + resultBonus.getDouble("searchBonus")
                    + " ,LibraryBonus: " + resultBonus.getDouble("libraryBonus"));
        }
        return payments;
    }

    /*Tarso's queries, not checked*/
    public ArrayList<Employee> addPESalaries(ArrayList<Employee> sortedSalaryperStaffCategory, ArrayList<String> categories) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM PermanentEducator");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false)
            return sortedSalaryperStaffCategory;
        else {
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
                        pEducator = new PermanentEducator(resultPE.getString("firstName"), resultPE.getString("lastName"), resultPE.getString("address"), resultPE.getInt("phoneNumber"), resultPE.getDate("beginHiringDate"), resultEmployee.getInt("PEId"), resultEmployee.getInt("PEId"));
                        pEducator.setSalary(resultPE.getDouble("salary"));
                        sortedSalaryperStaffCategory.add(pEducator);
                        if (categories != null)
                            categories.add("Permanent Educator");
                    }
                } while (resultPE.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> addPMSalaries(ArrayList<Employee> sortedSalaryperStaffCategory, ArrayList<String> categories) throws
            SQLException {

        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM PermanentManager");
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next() == false)
            return sortedSalaryperStaffCategory;
        else {
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
                        pManager.setSalary(resultPM.getDouble("salary"));
                        sortedSalaryperStaffCategory.add(pManager);
                        if (categories != null)
                            categories.add("Permanent Manager");
                    }
                } while (resultPM.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> addCMSalaries(ArrayList<Employee> sortedSalaryperStaffCategory, ArrayList<String> categories) throws
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
                        cManager.setSalary(resultCM.getDouble("salary"));
                        sortedSalaryperStaffCategory.add(cManager);
                        if (categories != null)
                            categories.add("Contractor Manager");
                    }
                } while (resultCM.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    public ArrayList<Employee> addCESalaries(ArrayList<Employee> sortedSalaryperStaffCategory, ArrayList<String> categories) throws
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
                        cEducator.setSalary(resultCE.getDouble("salary"));
                        sortedSalaryperStaffCategory.add(cEducator);

                        if (categories != null)
                            categories.add("Contractor Educator");
                    }
                } while (resultCE.next());
            } while (resultEmployee.next());
        }
        return sortedSalaryperStaffCategory;
    }

    private ArrayList<Employee> defineCategory(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        switch (Category) {
            case "Permanent Manager":
                SalaryCat = addPMSalaries(SalaryCat, null);
                break;
            case "Permanent Educator":
                SalaryCat = addPESalaries(SalaryCat, null);
                break;
            case "Contractor Manager":
                SalaryCat = addCMSalaries(SalaryCat, null);
                break;
            case "Contractor Educator":
                SalaryCat = addCESalaries(SalaryCat, null);
                break;
            default:
                assert (false);
        }
        return SalaryCat;
    }


    public double getMinSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        double minSalary = SalaryCat.get(0).getSalary();
        double salary = 0;
        for (int i = 1; i < SalaryCat.size(); ++i) {
            salary = SalaryCat.get(i).getSalary();
            if (salary < minSalary)
                minSalary = salary;
        }
        return minSalary;
    }

    public double getMaxSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        double maxSalary = -1;
        double salary = 0;
        for (int i = 0; i < SalaryCat.size(); ++i) {
            salary = SalaryCat.get(0).getSalary();
            if (salary > maxSalary)
                maxSalary = salary;
        }
        return maxSalary;
    }

    public double getAverageSalary(ArrayList<Employee> SalaryCat, String Category) throws SQLException {
        SalaryCat = defineCategory(SalaryCat, Category);
        if (SalaryCat.size() == 0)
            return 0;
        double averageSalary = 0;
        for (int i = 0; i < SalaryCat.size(); ++i)
            averageSalary += SalaryCat.get(i).getSalary();
        return averageSalary / SalaryCat.size();
    }

    public ArrayList<Double> getMinSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();
        ArrayList<Employee> Employees = new ArrayList<>();

        sortedSalaries.add(getMinSalary(Employees, "PM"));

        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "PE"));


        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "CM"));

        Employees.clear();
        sortedSalaries.add(getMinSalary(Employees, "CE"));

        return sortedSalaries;
    }

    public ArrayList<Double> getMaxSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();
        ArrayList<Employee> Employees = new ArrayList<>();

        sortedSalaries.add(getMaxSalary(Employees, "Permanent Manager"));

        Employees.clear();
        sortedSalaries.add(getMaxSalary(Employees, "Permanent Educator"));

        Employees.clear();
        sortedSalaries.add(getMaxSalary(Employees, "Contractor Manager"));


        Employees.clear();
        sortedSalaries.add(getMaxSalary(Employees, "Contractor Educator"));

        return sortedSalaries;
    }

    public ArrayList<Double> getAverageSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();
        ArrayList<Employee> Employees = new ArrayList<>();

        sortedSalaries.add(getAverageSalary(Employees, "Permanent Manager"));

        Employees.clear();
        sortedSalaries.add(getAverageSalary(Employees, "Permanent Educator"));

        Employees.clear();
        sortedSalaries.add(getAverageSalary(Employees, "Contractor Manager"));

        Employees.clear();
        sortedSalaries.add(getAverageSalary(Employees, "Contractor Educator"));

        return sortedSalaries;
    }

    public ArrayList<Employee> getSalaryperStaffCategory(ArrayList<String> categories) throws SQLException {
        ArrayList<Employee> sortedSalaryperStaffCategory = new ArrayList<>();

        sortedSalaryperStaffCategory = addPMSalaries(sortedSalaryperStaffCategory, categories);
        sortedSalaryperStaffCategory = addPESalaries(sortedSalaryperStaffCategory, categories);
        sortedSalaryperStaffCategory = addCMSalaries(sortedSalaryperStaffCategory, categories);
        sortedSalaryperStaffCategory = addCESalaries(sortedSalaryperStaffCategory, categories);

        return sortedSalaryperStaffCategory;
    }

    public Employee getEmployeeSalaryData(int EmployeeId, ArrayList<String> categories) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (resultEmployee.next() == false) return null;

//        Employee result = getPE(EmployeeId);
        double salary = resultEmployee.getDouble("salary");
        String Category = "Permanent Educator";

//        if (result == null) {
//            result = getPM(EmployeeId);
//            Category = "Permanent Manager";
//        }
//
//        if (result == null) {
//            result = getCM(EmployeeId);
//            Category = "Contractor Manager";
//        }
//
//        if (result == null) {
//            result = getCE(EmployeeId);
//            Category = "Contractor Educator";
//        }

//        if (result == null) {
//            JOptionPane.showMessageDialog(null, "Employee could not be found", "Incorrect Employee Id Given", JOptionPane.OK_OPTION);
//            Category = "Undefined";
//            return null;
//        }
//        result.setSalary(salary);
//        categories.add(Category);
//        return result;
        return null;
    }
}