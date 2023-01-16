package server;

import classes.*;
import java.sql.*;

public class ServerRequest {
    Connector connector;

    public ServerRequest(){
        connector = new Connector();
    }

    public PreparedStatement selectStatement(Connector c, String q) throws SQLException {
        return c.getConnection().prepareStatement(q, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
    }

    public void updateDate(Date date) throws SQLException {
        PreparedStatement statement = selectStatement(connector,
                "UPDATE Employee SET beginHiringDate=? WHERE EmployeeId=?");
        statement.setDate(1, date);
        statement.setInt(2, 10);
        statement.execute();
    }

    public boolean check_null_username_password(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            System.err.println("Cannot give a username and password that is null!\n");
            return true;
        }
        return false;
    }

    public int login(String userName, String password)  throws SQLException{
        if (check_null_username_password(userName,password)) return -1;
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
        if (!resultEmployee.next()){
            System.out.println("The person is not a UOC employee!\n");
            return null;
        }

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentEducator WHERE EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        resultPE.next();

        PermanentEducator pEducator = new PermanentEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"),resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"));

        System.out.println(pEducator.toString());
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
        resultPM.next();

        PermanentManager pManager = new PermanentManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPM.getInt("PMId"));

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
        resultCE.next();

        ContractorEducator cEducator = new ContractorEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCE.getInt("CEId"));

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
        resultCM.next();

        ContractorManager cManager = new ContractorManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"), resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultCM.getInt("CMId"));

        return cManager;
    }


    /*update information in base*/
    public void changeAddress(String address, int EmployeeId) throws SQLException{
        if (!check_EmployeeId_In_Database(EmployeeId)){
            System.out.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET address=? WHERE EmployeeId=?");
        statement1.setString(1, address);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changePhoneNumber(int number, int EmployeeId) throws SQLException{
        if (!check_EmployeeId_In_Database(EmployeeId)){
            System.err.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET phoneNumber=? WHERE EmployeeId=?");
        statement1.setInt(1, number);
        statement1.setInt(2, EmployeeId);
        statement1.execute();
    }

    public void changeFamilyState(String state, int kids, String ages, int EmployeeId, double basicSalary, double contractSalary) throws SQLException{
        if (!check_EmployeeId_In_Database(EmployeeId)){
            System.err.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "SELECT stateId FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "UPDATE FamilyState SET state=?, numberKids=?, ages=? WHERE stateId=?");
        statement2.setString(1, state);
        statement2.setInt(2, kids);
        statement2.setString(3, ages);
        statement2.setInt(4, resultEmployee.getInt("StateId"));
        statement2.execute();

        //call Marilena's function changeFamilyBonus to calculate FamilyBonus and UPDATE Bonus table
    }


    /*insert columns into tables*/
    public int insertEmployee(String[] infoStr, Date date, int[] infoInt, double salary) throws SQLException {
        //infoStr contains firstName, lastName, address
        //infoInt contains phoneNumber, BankId, StateId, BonusId
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO Employee (EmployeeId,firstName,lastName,beginHiringDate,address,phoneNumber,salary,BankId,StateId,BonusId) VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,null);
        statement1.setString(2,infoStr[0]);
        statement1.setString(3,infoStr[1]);
        statement1.setDate(4, date);
        statement1.setString(5,infoStr[2]);
        statement1.setInt(6,infoInt[0]);
        statement1.setDouble(7,salary);
        statement1.setInt(8,infoInt[1]);
        statement1.setInt(9,infoInt[2]);
        statement1.setInt(10,infoInt[3]);
        statement1.executeUpdate();

        ResultSet resultEmployee = statement1.getGeneratedKeys();
        if (!resultEmployee.next()) return -1;
        return resultEmployee.getInt(1);
    }

    public void hireEmployee(int EmployeeId,String groupEmployer, String JobDepartment, String username, String password) throws SQLException {
        if (groupEmployer.equals("Permanent")){
            if (JobDepartment.equals("Educator"))
                hirePermanentEducator(EmployeeId, username, password);
            else
                hirePermanentManager(EmployeeId, username, password);
        } else{
            if (JobDepartment.equals("Educator"))
                hireContractorEducator(EmployeeId, username, password);
            else
                hireContractorManager(EmployeeId, username, password);
        }
    }

    public void hirePermanentEducator(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO PermanentEducator (username,password,PEId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setString(3,null);
        statement1.setInt(4,EmployeeId);
        statement1.executeUpdate();
    }

    public void hirePermanentManager(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO PermanentManager (username,password,PMId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setString(3,null);
        statement1.setInt(4,EmployeeId);
        statement1.executeUpdate();
    }

    public void hireContractorEducator(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO ContractorEducator (username,password,CEId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setString(3,null);
        statement1.setInt(4,EmployeeId);
        statement1.executeUpdate();
    }

    public void hireContractorManager(int EmployeeId, String username, String password) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO ContractorManager (username,password,CMId,EmployeeId) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setString(3,null);
        statement1.setInt(4,EmployeeId);
        statement1.executeUpdate();
    }

    public int insertFamilyState(String state, int numberKids, String ages) throws SQLException {
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO FamilyState (StateId,state,numberKids,ages) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,null);
        statement1.setString(2,state);
        statement1.setInt(3,numberKids);
        statement1.setString(4,ages);
        statement1.executeUpdate();

        ResultSet resultState = statement1.getGeneratedKeys();
        if (!resultState.next()) return -1;
        return resultState.getInt(1);
    }

    public int insertBankInfo(int IBAN, String bankName) throws SQLException{
        PreparedStatement statement1 = connector.getConnection().prepareStatement(
                "INSERT INTO BankInfo (BankID,IBAN,bankName) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement1.setString(1,null);
        statement1.setInt(2,IBAN);
        statement1.setString(3,bankName);
        statement1.executeUpdate();

        ResultSet resultBankInfo = statement1.getGeneratedKeys();
        if (!resultBankInfo.next()) return -1;
        return resultBankInfo.getInt(1);
    }

    public int insertBonus(double famBonus, double searchBonus, double libBonus) throws SQLException {
        //double famBonus = calculateFamilyBonus();

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
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }
}
