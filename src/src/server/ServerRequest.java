package src.server;

import src.classes.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;

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
        if (!(BigDecimal.valueOf(value).scale() > 2) || value == 0) return value;
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

        return new PermanentEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"), resultPE.getInt("EmployeeId"));
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

        return new PermanentManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPM.getInt("PMId"), resultPM.getInt("EmployeeId"));
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

        return new ContractorEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCE.getInt("CEId"), resultEmployee.getInt("EmployeeId"));
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

        return new ContractorManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCM.getInt("CMId"), resultCM.getInt("EmployeeId"));
    }


    /*update information in base for phoneNumber, familyState, address, each bonus*/
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

    public void changeFamilyState(String state, int kids, String ages, int EmployeeId, Date currentDate) throws SQLException {
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

        changeFamilyBonus(state, ages, EmployeeId,currentDate);
    }

    /*for each individual Employee change each salary depending on bonuses, raises, etc*/
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
                if (getPM(resultEmployee.getInt("EmployeeId")) != null)
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), basicSalary, resultBonus.getDouble("familyBonus"), 0, 0,true);

                if (getPE(resultEmployee.getInt("EmployeeId")) != null)
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), basicSalary, resultBonus.getDouble("familyBonus"), resultBonus.getDouble("searchBonus"), 0,true);

                if (getCE(resultEmployee.getInt("EmployeeId")) != null)
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), contractSalary, resultBonus.getDouble("familyBonus"), 0, resultBonus.getDouble("libraryBonus"),false);

                if (getCM(resultEmployee.getInt("EmployeeId")) != null)
                    newSalary = estimateEmployeesSalary(currentDate, resultEmployee.getDate("beginHiringDate"), contractSalary, resultBonus.getDouble("familyBonus"), 0, 0,false);
            }

            newSalary = convert_to_2_Decimals(newSalary);
            insertSalary(newSalary, resultEmployee.getDouble("salary"), resultEmployee.getInt("EmployeeId"));
            insertRaise(Date.valueOf(currentDate),newSalary - resultEmployee.getDouble("salary"),0,0,0);
        }
    }

    /*change info in base for Bonus table*/
    public void change_Search_Library_Bonus(double value, String bonusType, Date currentDate) throws SQLException {
        double oldBonus=0;

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Bonus");
        ResultSet resultBonus = statement.executeQuery();

        while (resultBonus.next()) {
            if (resultBonus.getDouble(bonusType) == 0) continue;

            PreparedStatement statement1 = selectStatement(connector,
                    "UPDATE Bonus SET " + bonusType+ "=? WHERE BonusId=?");
            statement1.setDouble(1, value);
            statement1.setInt(2, resultBonus.getInt("BonusId"));
            statement1.execute();

            oldBonus = resultBonus.getDouble(bonusType);
        }

        if (bonusType.equals("searchBonus"))
            insertRaise(currentDate, 0,0,value-oldBonus,0);
        else
            insertRaise(currentDate, 0,0,0,value-oldBonus);
    }

    public void changeFamilyBonus(String state, String kidsAges, int employeeId, Date currentDate) throws SQLException {
        double famBonus = calculateFamilyBonus(state, kidsAges);

        PreparedStatement statement = selectStatement(connector,
                "SELECT BonusId FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, employeeId);
        ResultSet resultBonus = statement.executeQuery();
        resultBonus.next();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT familyBonus FROM Bonus WHERE BonusId=?");
        statement2.setInt(1, resultBonus.getInt("BonusId"));
        ResultSet resultBonuss = statement2.executeQuery();
        resultBonuss.next();

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Bonus SET familyBonus=? WHERE BonusId=?");
        statement1.setDouble(1, famBonus);
        statement1.setInt(2, resultBonus.getInt("BonusId"));
        statement1.execute();

        insertRaise(currentDate,0,famBonus - resultBonuss.getDouble("familyBonus"),0,0);

    }


    /*functions that calculate salary and family bonus*/
    public double estimateEmployeesSalary(String currentDate, Date date, double InitialSalary, double familyBonus, double searchBonus, double libraryBonus, boolean permanent) {
        String dateString = date.toString();
        String[] separateDate = dateString.split("-");
        String[] separateCurrentDate = currentDate.split("-");

        //1 if one year passed from begigHiringDate
        int oneMoreYear = (Integer.parseInt(separateCurrentDate[1]) > Integer.parseInt(separateDate[1]) ||
                (Integer.parseInt(separateCurrentDate[1]) == Integer.parseInt(separateDate[1]) &&
                        Integer.parseInt(separateCurrentDate[2]) >= Integer.parseInt(separateDate[2]))) ? 1 : 0;
        double newSalary = InitialSalary;

        //how many times we must have 15% raise in InitialSalary if Permanent Employee
        if (permanent){
            int times15up = Integer.parseInt(separateCurrentDate[0]) - Integer.parseInt(separateDate[0]) + oneMoreYear;
            newSalary += 0.15 * InitialSalary * times15up;
        }

        return (newSalary + InitialSalary * familyBonus + searchBonus + libraryBonus);
    }

    public double calculateFamilyBonus(String state, String kidsAges) {
        double famBonus = 0.0;

        if (state.equals("married")) famBonus += 0.05;

        String[] split = kidsAges.split(",");
        if (!split[0].equals("")) {
            for (String s : split) {
                if (Integer.parseInt(s) < 18) famBonus += 0.05;
            }
        }

        famBonus = convert_to_2_Decimals(famBonus);
        return famBonus;
    }


    /*hire employee, find which category is the employee and insert into its table the data given*/
    public void hireEmployee(int EmployeeId, String groupEmployer, String JobDepartment) throws SQLException {
        String XXId = groupEmployer.substring(0, 1) + JobDepartment.substring(0,1) + "Id";
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO " + groupEmployer + JobDepartment + " (" +  XXId + ",EmployeeId) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setInt(2, EmployeeId);
        statement1.executeUpdate();
    }

    public int insertLogin(String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO LoginInfo (LoginId,userName,password) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, username);
        statement1.setString(3, password);
        statement1.executeUpdate();

        ResultSet resultLogin = statement1.getGeneratedKeys();
        if (!resultLogin.next()) return -1;
        return resultLogin.getInt(1);
    }

    /*insert columns into tables*/
    public int insertEmployee(String[] infoStr, Date date, int[] infoInt, double salary) throws SQLException {
        //infoStr contains firstName, lastName, address
        //infoInt contains phoneNumber, LoginId, BankId, StateId, BonusId
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Employee (EmployeeId,firstName,lastName,beginHiringDate,address,phoneNumber,salary,active,LoginId,BankId,StateId,BonusId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setString(2, infoStr[0]);
        statement1.setString(3, infoStr[1]);
        statement1.setDate(4, date);
        statement1.setString(5, infoStr[2]);
        statement1.setInt(6, infoInt[0]);
        statement1.setDouble(7, salary);
        statement1.setInt(8,1);
        statement1.setInt(9, infoInt[1]);
        statement1.setInt(10, infoInt[2]);
        statement1.setInt(11, infoInt[3]);
        statement1.setInt(12, infoInt[4]);
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
                "INSERT INTO BankInfo (BankId,IBAN,bankName) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1, null);
        statement1.setInt(2, IBAN);
        statement1.setString(3, bankName);
        statement1.executeUpdate();

        ResultSet resultBankInfo = statement1.getGeneratedKeys();
        if (!resultBankInfo.next()) return -1;
        return resultBankInfo.getInt(1);
    }

    public int insertBonus(double famBonus, double searchBonus, double libBonus, String group, String job) throws SQLException {
        if (group.equals("Permanent") && job.equals("Educator")) {
            searchBonus = 0.0;
        } else if (!group.equals("Contractor") && !job.equals("Educator")){
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
        if (newSalary <= oldSalary) return;

        PreparedStatement statement = selectStatement(connector,
                "UPDATE Employee SET salary=? WHERE EmployeeId=?");
        statement.setDouble(1, newSalary);
        statement.setInt(2, employeeId);
        statement.execute();
    }

    public void insertRaise(Date currentdate, double newSalary, double famBonus, double searchBonus, double libBonus) throws SQLException {
        newSalary = convert_to_2_Decimals(newSalary);
        famBonus = convert_to_2_Decimals(famBonus);
        searchBonus = convert_to_2_Decimals(searchBonus);
        libBonus = convert_to_2_Decimals(libBonus);
        if (newSalary == 0 && famBonus == 0 && searchBonus == 0 && libBonus == 0) return;
        if (newSalary < 0) return;
        if (famBonus < 0) return;
        if (searchBonus < 0) return;
        if (libBonus < 0) return;

        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Raise (RaiseId,date,raiseFamBonus,raiseSearchBonus,raiseLibraryBonus,raiseSalary) VALUES (?,?,?,?,?,?)");
        statement1.setString(1, null);
        statement1.setDate(2, currentdate);
        statement1.setDouble(3, famBonus);
        statement1.setDouble(4, searchBonus);
        statement1.setDouble(5, libBonus);
        statement1.setDouble(6, newSalary);
        statement1.executeUpdate();
    }

    /*Pay the employees and get the data from employee*/
    public ArrayList<String> payEmployees(String currentDate, double basicSalary, double contractSalary) throws SQLException {
        ArrayList<String> payments = new ArrayList<>();
        changeSalary(currentDate, basicSalary, contractSalary);

        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee");
        ResultSet resultEmployee = statement.executeQuery();

        while (resultEmployee.next()) {
            PreparedStatement statement2 = selectStatement(connector,
                    "SELECT * FROM Bonus WHERE BonusId=?");
            statement2.setInt(1, resultEmployee.getInt("BonusId"));
            ResultSet resultBonus = statement2.executeQuery();
            resultBonus.next();

            if (resultEmployee.getInt("active") == 1) payments.add("EmployeeId: " + resultEmployee.getInt("EmployeeId") + " ,Salary: "
                    + resultEmployee.getDouble("salary") + " ,FamilyBonus: " + resultBonus.getDouble("familyBonus") + " ,SearchBonus: "
                    + resultBonus.getDouble("searchBonus") + " ,LibraryBonus: " + resultBonus.getDouble("libraryBonus"));
        }
        return payments;
    }

    /*Check EmployeeId in Database to change info of Employee*/
    public boolean check_EmployeeId_In_Database(int EmployeeId) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement.executeQuery();

        return resultEmployee.next();
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

        return resultEmployee.getDouble("salary");
    }


    /*Queries, find min-max-average salary per category*/
    public ArrayList<Double> getMinSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();

        sortedSalaries.add(getMinSalary("PermanentManager"));
        sortedSalaries.add(getMinSalary("PermanentEducator"));
        sortedSalaries.add(getMinSalary("ContractorManager"));
        sortedSalaries.add(getMinSalary("ContractorEducator"));

        return sortedSalaries;
    }

    public double getMinSalary(String EmployeeCategory) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT MIN(salary) FROM Employee WHERE EmployeeId IN (SELECT EmployeeId FROM " + EmployeeCategory + ")");
        ResultSet resultSalary = statement1.executeQuery();
        if (!resultSalary.next()) return 0.0;
        return resultSalary.getDouble(1);
    }

    public ArrayList<Double> getMaxSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();

        sortedSalaries.add(getMaxSalary("PermanentManager"));
        sortedSalaries.add(getMaxSalary("PermanentEducator"));
        sortedSalaries.add(getMaxSalary("ContractorManager"));
        sortedSalaries.add(getMaxSalary("ContractorEducator"));

        return sortedSalaries;
    }

    public double getMaxSalary(String EmployeeCategory) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT MAX(salary) FROM Employee WHERE EmployeeId IN (SELECT EmployeeId FROM " + EmployeeCategory + ")");
        ResultSet resultSalary = statement1.executeQuery();
        if (!resultSalary.next()) return 0.0;
        return resultSalary.getDouble(1);
    }

    public ArrayList<Double> getAverageSalaryStatisticsperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();

        sortedSalaries.add(getAverageSalary("PermanentManager"));
        sortedSalaries.add(getAverageSalary("PermanentEducator"));
        sortedSalaries.add(getAverageSalary("ContractorManager"));
        sortedSalaries.add(getAverageSalary("ContractorEducator"));

        return sortedSalaries;
    }

    public double getAverageSalary(String EmployeeCategory) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT AVG(salary) FROM Employee WHERE EmployeeId IN (SELECT EmployeeId FROM " + EmployeeCategory + ")");
        ResultSet resultSalary = statement1.executeQuery();
        if (!resultSalary.next()) return 0.0;
        return resultSalary.getDouble(1);
    }

    public ArrayList<Double> getTotalSalaryperCategory() throws SQLException {
        ArrayList<Double> sortedSalaries = new ArrayList<>();

        sortedSalaries.add(getTotalSalary("PermanentManager"));
        sortedSalaries.add(getTotalSalary("PermanentEducator"));
        sortedSalaries.add(getTotalSalary("ContractorManager"));
        sortedSalaries.add(getTotalSalary("ContractorEducator"));

        return sortedSalaries;
    }

    public double getTotalSalary(String EmployeeCategory) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT SUM(salary) FROM Employee WHERE EmployeeId IN (SELECT EmployeeId FROM " + EmployeeCategory + ")");
        ResultSet resultSalary = statement1.executeQuery();
        if (!resultSalary.next()) return 0.0;
        return resultSalary.getDouble(1);
    }


    /*Add category of Employees salary*/
    public ArrayList<String> getSalaryPerCategory(String EmployeeCategory) throws SQLException {
        ArrayList<String> salaryEmployees = new ArrayList<>();
        PreparedStatement statement1 = selectStatement(connector, "SELECT firstName,lastName,salary FROM Employee WHERE EmployeeId IN (SELECT EmployeeId FROM " + EmployeeCategory + ")");
        ResultSet resultSalary = statement1.executeQuery();

        while (resultSalary.next()){
            salaryEmployees.add(resultSalary.getString("firstName") + " " + resultSalary.getString("lastName") + " " + resultSalary.getDouble("salary"));
        }
        return salaryEmployees;
    }

    public String getEmployeeSalaryData(int EmployeeId) throws SQLException {
        String EmployeeInfo = "";
        PreparedStatement statement = selectStatement(connector, "SELECT * FROM Employee WHERE EmployeeId=?");
        statement.setInt(1,EmployeeId);
        ResultSet resultEmployee = statement.executeQuery();
        resultEmployee.next();

        EmployeeInfo += "FirstName: " + resultEmployee.getString("firstName") + "<br><br>LastName: " + resultEmployee.getString("lastName")
                + "<br><br>BeginHiringDate: " + resultEmployee.getDate("BeginHiringDate").toString() + "<br><br>Address: "
                + resultEmployee.getString("address") + "<br><br>PhoneNumber " + resultEmployee.getInt("phoneNumber")
                + "<br><br>Salary: " + resultEmployee.getDouble("salary");

        PreparedStatement statement1 = selectStatement(connector, "SELECT * FROM BankInfo WHERE BankId=?");
        statement1.setInt(1,resultEmployee.getInt("BankId"));
        ResultSet resultBankInfo = statement1.executeQuery();
        resultBankInfo.next();

        EmployeeInfo += "<br><br>IBAN: " + resultBankInfo.getInt("IBAN") + "<br><br>BankName: " + resultBankInfo.getString("bankName");

        PreparedStatement statement2 = selectStatement(connector, "SELECT * FROM Bonus WHERE BonusId=?");
        statement2.setInt(1,resultEmployee.getInt("BonusId"));
        ResultSet resultBonus = statement2.executeQuery();
        resultBonus.next();

        EmployeeInfo += "<br><br>FamilyBonus: " + resultBonus.getDouble("familyBonus");
        if (resultBonus.getDouble("searchBonus") != 0) {
            EmployeeInfo += "<br><br>SearchBonus: " + resultBonus.getDouble("searchBonus");
        }
        if (resultBonus.getDouble("libraryBonus") != 0) {
            EmployeeInfo += "<br><br>LibraryBonus: " + resultBonus.getDouble("libraryBonus");
        }

        PreparedStatement statement3 = selectStatement(connector, "SELECT * FROM FamilyState WHERE StateId=?");
        statement3.setInt(1,resultEmployee.getInt("StateId"));
        ResultSet resultState = statement3.executeQuery();
        resultState.next();

        EmployeeInfo += "<br><br>State: " + resultState.getString("state") + "<br><br>NumberKids: " + resultState.getInt("numberKids")
                    + "<br><br>Ages: " + resultState.getString("ages");

        return EmployeeInfo;
    }

    public double getAverageSalaryBonusIncrease(Date initialDate, Date finalDate, String raiseType) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement("SELECT AVG(" + raiseType + ") FROM Raise WHERE date BETWEEN ? AND ?");
        statement1.setDate(1, initialDate);
        statement1.setDate(2, finalDate);

        ResultSet resultRaise = statement1.executeQuery();
        if (!resultRaise.next()) return 0.0;
        return resultRaise.getDouble(1);
    }

    public int countActiveEmployees() throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT SUM(active) FROM Employee WHERE active=1");
        ResultSet resultRaise = statement1.executeQuery();
        if (!resultRaise.next()) return 0;
        return resultRaise.getInt(1);
    }

    public String EmployeeWithMaxKids() throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT firstName,lastName FROM Employee WHERE StateId IN (SELECT StateId FROM FamilyState WHERE numberKids = (SELECT MAX(numberKids) FROM FamilyState))");
        ResultSet resultRaise = statement1.executeQuery();
        if (!resultRaise.next()) return "";
        return resultRaise.getString(1) + " " + resultRaise.getString(2);
    }

    public String EmployeeWithMaxExperience() throws SQLException {
        PreparedStatement statement1 = selectStatement(connector, "SELECT firstName,lastName FROM Employee WHERE beginHiringDate = (SELECT MIN(beginHiringDate) FROM Employee) AND active=1");
        ResultSet resultRaise = statement1.executeQuery();
        if (!resultRaise.next()) return "";
        return resultRaise.getString(1) + " " + resultRaise.getString(2);
    }
}