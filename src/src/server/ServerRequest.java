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

    public boolean check_null_username_password(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            System.err.println("Cannot give a username and password that is null!\n");
            return true;
        }
        return false;
    }

    public int login(String userName, String password) throws SQLException {
        if (check_null_username_password(userName, password)) return -1;
        int id;

        id = loginPE(userName, password);
        if (id != -1) return id;

        id = loginPM(userName, password);
        if (id != -1) return id;

        id = loginCE(userName, password);
        if (id != -1) return id;

        id = loginCM(userName, password);
        return id;
    }

    /*login Employees with username password*/
    public int loginPE(String userName, String password) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT password,EmployeeId FROM PermanentEducator WHERE username=?");
        statement.setString(1, userName);
        ResultSet result = statement.executeQuery();
        if (!result.next()) return -1;
        String pass = result.getString("password");
        PermanentEducator pe = getPE(result.getInt("EmployeeId"));
        if (!password.equals(pass) || pe == null) return -1;

        return result.getInt("EmployeeId");
    }

    /*select information from tables*/
    public PermanentEducator getPE(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (!resultEmployee.next()) {
            System.out.println("The person is not a UOC employee!\n");
            return null;
        }

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentEducator WHERE EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        if (!resultPE.next()) return null;

        PermanentEducator pEducator = new PermanentEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"), resultPE.getInt("EmployeeId"));

        return pEducator;
    }

    public int loginPM(String userName, String password) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT password,EmployeeId FROM PermanentManager WHERE username=?");
        statement.setString(1, userName);
        ResultSet result = statement.executeQuery();
        if (!result.next()) return -1;
        String pass = result.getString("password");
        PermanentManager pm = getPM(result.getInt("EmployeeId"));
        if (!password.equals(pass) || pm == null) return -1;

        System.out.println(pm.toString());
        return result.getInt("EmployeeId");
    }

    public PermanentManager getPM(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (!resultEmployee.next()) {
            System.out.println("The person is not a UOC employee!\n");
            return null;
        }

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentManager WHERE EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPM = statement2.executeQuery();
        if (!resultPM.next()) return null;

        PermanentManager pManager = new PermanentManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPM.getInt("PMId"), resultPM.getInt("EmployeeId"));

        return pManager;
    }

    public int loginCE(String userName, String password) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT password,EmployeeId FROM ContractorEducator WHERE username=?");
        statement.setString(1, userName);
        ResultSet result = statement.executeQuery();
        if (!result.next()) return -1;
        String pass = result.getString("password");
        ContractorEducator ce = getCE(result.getInt("EmployeeId"));
        if (!password.equals(pass) || ce == null) return -1;

        System.out.println(ce.toString());
        return result.getInt("EmployeeId");
    }

    public ContractorEducator getCE(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (!resultEmployee.next()) {
            System.out.println("The person is not a UOC employee!\n");
            return null;
        }

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM ContractorEducator WHERE EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultCE = statement2.executeQuery();
        if (!resultCE.next()) return null;

        ContractorEducator cEducator = new ContractorEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCE.getInt("CEId"), resultEmployee.getInt("EmployeeId"));

        return cEducator;
    }

    public int loginCM(String userName, String password) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT password,EmployeeId FROM ContractorManager WHERE username=?");
        statement.setString(1, userName);
        ResultSet result = statement.executeQuery();
        if (!result.next()) return -1;
        String pass = result.getString("password");
        ContractorManager cm = getCM(result.getInt("EmployeeId"));
        if (!password.equals(pass) || cm == null) return -1;

        System.out.println(cm.toString());
        return result.getInt("EmployeeId");
    }

    public ContractorManager getCM(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        if (!resultEmployee.next()) {
            System.out.println("The person is not a UOC employee!\n");
            return null;
        }

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM ContractorManager WHERE EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultCM = statement2.executeQuery();
        if (!resultCM.next()) return null;

        ContractorManager cManager = new ContractorManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCM.getInt("CMId"), resultCM.getInt("EmployeeId"));

        return cManager;
    }


    /*update information in base*/
    public void changeAddress(String address, int EmployeeId) throws SQLException {
        if (!check_EmployeeId_In_Database(EmployeeId)) {
            System.out.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET address=? WHERE EmployeeId=?");
        statement1.setString(1, address);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changePhoneNumber(int number, int EmployeeId) throws SQLException {
        if (!check_EmployeeId_In_Database(EmployeeId)) {
            System.err.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET phoneNumber=? WHERE EmployeeId=?");
        statement1.setInt(1, number);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changeFamilyState(String state, int kids, String ages, int EmployeeId) throws SQLException {
        if (!check_EmployeeId_In_Database(EmployeeId)) {
            System.err.println("The Person does not work in UOC!\n");
            return;
        }

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

    public double estimateEmployeesSalary(String currentDate, Date date, double InitialSalary, double familyBonus, double searchBonus, double libraryBonus) {
        String dateString = date.toString();
        String[] separateDate = dateString.split("-");
        String[] separateCurrentDate = currentDate.split("-");

        int oneMoreYear = (Integer.parseInt(separateCurrentDate[1]) < Integer.parseInt(separateDate[1])) ? 1 : 0;
        double newSalary = InitialSalary;

        for (int i = 0; i < (Integer.parseInt(separateCurrentDate[0]) - Integer.parseInt(separateDate[0]) + oneMoreYear); i++)
            newSalary += 0.15 * newSalary;

        return (newSalary + InitialSalary * familyBonus + searchBonus + libraryBonus);
    }

    public void insertSalary(double newSalary, double oldSalary, int employeeId) throws SQLException {
        if (newSalary <= oldSalary) return;

        PreparedStatement statement = selectStatement(connector,
                "UPDATE Employee SET salary=? WHERE EmployeeId=?");
        statement.setDouble(1, newSalary);
        statement.setInt(2, employeeId);
        statement.execute();
    }

    public ArrayList<String> payEmployees(String currentDate, double basicSalary, double contractSalary) throws SQLException {
        /**
         * Make a JOptionPane.showMessageDialog that shows "The Employees are paid 31-X-XXXX"
         * In this panel show for each Employee: EmployeeId, salary (basic or contract), familyBonus, searchBonus, libraryBonus, finalSalary
         */
        ArrayList<String> payments = new ArrayList<String>();

        int timesPaid = 0;
        int i = 0;

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee"); //salary,StateId,BonusId,beginHiringDate
        ResultSet resultEmployee = statement.executeQuery();

        while (resultEmployee.next()) {
            PreparedStatement statement1 = selectStatement(connector,
                    "SELECT timesPaid FROM BankInfo WHERE BankId=?"); //salary,StateId,BonusId,beginHiringDate
            statement1.setInt(1, resultEmployee.getInt("BankId"));
            ResultSet resultBank = statement1.executeQuery();
            resultBank.next();

            timesPaid = resultBank.getInt("timesPaid") + 1;
            PreparedStatement statement2 = selectStatement(connector,
                    "UPDATE BankInfo SET timesPaid=? WHERE BanKId=?");
            statement2.setDouble(1, timesPaid);
            statement2.setInt(2, resultEmployee.getInt("BankId"));
            statement2.execute();

            if ((timesPaid % 12) == 1) changeSalary(currentDate, basicSalary, contractSalary);

            PreparedStatement statement3 = selectStatement(connector,
                    "SELECT * FROM Bonus WHERE BonusId=?"); //salary,StateId,BonusId,beginHiringDate
            statement3.setInt(1, resultEmployee.getInt("BonusId"));
            ResultSet resultBonus = statement3.executeQuery();
            resultBonus.next();

            payments.add("EmployeeId: " + resultEmployee.getInt("EmployeeId") + " ,Salary: " + resultEmployee.getDouble("salary")
                    + " ,FamilyBonus: " + resultBonus.getDouble("familyBonus") + " ,SearchBonus: " + resultBonus.getDouble("searchBonus")
                    + " ,LibraryBonus: " + resultBonus.getDouble("libraryBonus"));
        }
        return payments;
    }

    public void changeSalary(String currentDate, double basicSalary, double contractSalary) throws SQLException {
        double newSalary = 0;

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee"); //salary,StateId,BonusId,beginHiringDate
        ResultSet resultEmployee = statement1.executeQuery();

        while (resultEmployee.next()) {
            PreparedStatement statement2 = selectStatement(connector,
                    "SELECT * FROM Bonus WHERE BonusId=?");
            statement2.setInt(1, resultEmployee.getInt("BonusId"));
            ResultSet resultBonus = statement2.executeQuery();
            if (!resultBonus.next()) continue;

            if (getPM(resultEmployee.getInt("EmployeeId")) != null)
                newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), basicSalary, resultBonus.getDouble("familyBonus"), 0, 0);

            if (getPE(resultEmployee.getInt("EmployeeId")) != null)
                newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), basicSalary, resultBonus.getDouble("familyBonus"), resultBonus.getDouble("searchBonus"), 0);

            if (getCE(resultEmployee.getInt("EmployeeId")) != null)
                newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), contractSalary, resultBonus.getDouble("familyBonus"), 0, resultBonus.getDouble("libraryBonus"));

            if (getCM(resultEmployee.getInt("EmployeeId")) != null)
                newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), contractSalary, resultBonus.getDouble("familyBonus"), 0, 0);

            newSalary = new BigDecimal(newSalary).setScale(2, RoundingMode.DOWN).doubleValue();

            insertSalary(newSalary, resultEmployee.getDouble("salary"), resultEmployee.getInt("EmployeeId"));
        }
    }

    public void changeSearchBonus(double searchBonus) throws SQLException {
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

    public double calculateFamilyBonus(String state, String kidsAges) {
        double famBonus = 0.0;

        if (state.equals("married")) famBonus += 0.05;

        String[] split = kidsAges.split(",");
        if (!split[0].equals("")) {
            for (int i = 0; i < split.length; i++) {
                if (Integer.parseInt(split[i]) < 18) famBonus += 0.05;
            }
        }

        famBonus = new BigDecimal(famBonus).setScale(2, RoundingMode.DOWN).doubleValue();
        return famBonus;
    }


    /*insert columns into tables*/
    public int insertEmployee(String[] infoStr, Date date, int[] infoInt, double salary) throws SQLException {
        //infoStr contains firstName, lastName, address
        //infoInt contains phoneNumber, BankId, StateId, BonusId
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Employee (EmployeeId,firstName,lastName,beginHiringDate,address,phoneNumber,salary,BankId,StateId,BonusId) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, infoStr[0]);
        statement1.setString(3, infoStr[1]);
        statement1.setDate(4, date);
        statement1.setString(5, infoStr[2]);
        statement1.setInt(6, infoInt[0]);
        statement1.setDouble(7, salary);
        statement1.setInt(8, infoInt[1]);
        statement1.setInt(9, infoInt[2]);
        statement1.setInt(10, infoInt[3]);
        statement1.executeUpdate();

        ResultSet resultEmployee = statement1.getGeneratedKeys();
        if (!resultEmployee.next()) return -1;
        return resultEmployee.getInt(1);
    }

    public void hireEmployee(int EmployeeId, String groupEmployer, String JobDepartment, String username, String password) throws SQLException {
        if (groupEmployer.equals("Permanent")) {
            if (JobDepartment.equals("Educator"))
                hirePermanentEducator(EmployeeId, username, password);
            else
                hirePermanentManager(EmployeeId, username, password);
        } else {
            if (JobDepartment.equals("Educator"))
                hireContractorEducator(EmployeeId, username, password);
            else
                hireContractorManager(EmployeeId, username, password);
        }
    }

    public void hirePermanentEducator(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO PermanentEducator (username,password,PEId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, username);
        statement1.setString(2, password);
        statement1.setString(3, null);
        statement1.setInt(4, EmployeeId);
        statement1.executeUpdate();
    }

    public void hirePermanentManager(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO PermanentManager (username,password,PMId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, username);
        statement1.setString(2, password);
        statement1.setString(3, null);
        statement1.setInt(4, EmployeeId);
        statement1.executeUpdate();
    }

    public void hireContractorEducator(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO ContractorEducator (username,password,CEId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, username);
        statement1.setString(2, password);
        statement1.setString(3, null);
        statement1.setInt(4, EmployeeId);
        statement1.executeUpdate();
    }

    public void hireContractorManager(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO ContractorManager (username,password,CMId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, username);
        statement1.setString(2, password);
        statement1.setString(3, null);
        statement1.setInt(4, EmployeeId);
        statement1.executeUpdate();
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

    public int insertBonus(double famBonus, double searchBonus, double libBonus, String group, String job) throws SQLException {
        if (group.equals("Permanent") && job.equals("Educator")) {
            libBonus = 0.0;
        } else if (group.equals("Contractor") && job.equals("Manager")) {
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

    /*Check EmployeeId in Database to change info of Employee*/
    public boolean check_EmployeeId_In_Database(int EmployeeId) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public double fireEmployee(int employeeId) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, employeeId);
        ResultSet resultEmployee = statement.executeQuery();
        resultEmployee.next();

        double salary = resultEmployee.getDouble("salary");

        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "DELETE FROM Bonus WHERE BonusId=?");
        statement1.setInt(1, resultEmployee.getInt("BonusId"));
        statement1.execute();

        PreparedStatement statement2 = connector.getConnection().prepareStatement(
                "DELETE FROM FamilyState WHERE StateId=?");
        statement2.setInt(1, resultEmployee.getInt("StateId"));
        statement2.execute();

        PreparedStatement statement3 = connector.getConnection().prepareStatement(
                "DELETE FROM BankInfo WHERE BankId=?");
        statement3.setInt(1, resultEmployee.getInt("BankId"));
        statement3.execute();

        PreparedStatement statement4 = connector.getConnection().prepareStatement(
                "DELETE FROM Employee WHERE EmployeeId=?");
        statement4.setInt(1, employeeId);
        statement4.execute();

        PreparedStatement statement5 = connector.getConnection().prepareStatement(
                "DELETE FROM PermanentEducator WHERE EmployeeId=?");
        statement5.setInt(1, employeeId);
        statement5.execute();

        PreparedStatement statement6 = connector.getConnection().prepareStatement(
                "DELETE FROM PermanentManager WHERE EmployeeId=?");
        statement6.setInt(1, employeeId);
        statement6.execute();

        PreparedStatement statement7 = connector.getConnection().prepareStatement(
                "DELETE FROM ContractorEducator WHERE EmployeeId=?");
        statement7.setInt(1, employeeId);
        statement7.execute();

        PreparedStatement statement8 = connector.getConnection().prepareStatement(
                "DELETE FROM ContractorManager WHERE EmployeeId=?");
        statement8.setInt(1, employeeId);
        statement8.execute();

        return salary;
    }

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

        Employee result = getPE(EmployeeId);
        double salary = resultEmployee.getDouble("salary");
        String Category = "Permanent Educator";

        if (result == null) {
            result = getPM(EmployeeId);
            Category = "Permanent Manager";
        }

        if (result == null) {
            result = getCM(EmployeeId);
            Category = "Contractor Manager";
        }

        if (result == null) {
            result = getCE(EmployeeId);
            Category = "Contractor Educator";
        }

        if (result == null) {
            JOptionPane.showMessageDialog(null, "Employee could not be found", "Incorrect Employee Id Given", JOptionPane.OK_OPTION);
            Category = "Undefined";
            return null;
        }
        result.setSalary(salary);
        categories.add(Category);
        return result;
    }
}