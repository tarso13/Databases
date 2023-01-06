package server;

import classes.*;
import util.HelperFunctions;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public int login(String userName, String password)  throws SQLException{
        HelperFunctions check = new HelperFunctions();
        if (check.check_null_username_password(userName,password)) return -1;
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

    public void changeFamilyState(String state, int kids, String ages, int stateId, int EmployeeId, double salary) throws SQLException{
        if (!check_EmployeeId_In_Database(EmployeeId)){
            System.err.println("The Person does not work in UOC!\n");
            return;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Employee SET stateId=? WHERE EmployeeId=?");
        statement1.setInt(1, stateId);
        statement1.setInt(2, EmployeeId);
        statement1.execute();

        PreparedStatement statement2 = selectStatement(connector,
                "UPDATE FamilyState SET state=?, numberKids=?, ages=? WHERE stateId=?");
        statement2.setString(1, state);
        statement2.setInt(2, kids);
        statement2.setString(3, ages);
        statement2.setInt(4, stateId);
        statement2.execute();

        //call Marilena's function changeFamilyBonus to calculate FamilyBonus and UPDATE Bonus table
    }


    /*insert columns into tables*/
    public void insertEmployee(int EmployeeId, String[] infoStr, Date date, int[] infoInt, double salary) throws SQLException {
        //infoStr contains firstName, lastName, address
        //infoInt contains phoneNumber, BankId, StateId, BonusId
        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO Employee (EmployeeId,firstName,lastName,beginHiringDate,address,phoneNumber,salary,BankId,StateId,BonusId) VALUES (?,?,?,?,?,?,?,?,?,?)");
        statement1.setInt(1,EmployeeId);
        statement1.setString(2,infoStr[0]);
        statement1.setString(3,infoStr[1]);
        statement1.setDate(4, date);
        statement1.setString(5,infoStr[2]);
        statement1.setInt(6,infoInt[0]);
        statement1.setDouble(7,salary);
        statement1.setInt(8,infoInt[1]);
        statement1.setInt(9,infoInt[2]);
        statement1.setInt(10,infoInt[3]);
        statement1.execute();
    }

    public int hirePermanentEducator(String username, String password, int PEId) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int EmployeeId = helper.findEmployeeId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO PermanentEducator (username,password,PEId,EmployeeId) VALUES (?,?,?,?)");
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setInt(3,PEId);
        statement1.setInt(4,EmployeeId);
        statement1.execute();

        return EmployeeId;
    }

    public int hirePermanentManager(String username, String password, int PMId) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int EmployeeId = helper.findEmployeeId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO PermanentManager (username,password,PMId,EmployeeId) VALUES (?,?,?,?)");
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setInt(3,PMId);
        statement1.setInt(4,EmployeeId);
        statement1.execute();

        return EmployeeId;
    }

    public int hireContractorEducator(String username, String password, int CEId) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int EmployeeId = helper.findEmployeeId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO ContractorEducator (username,password,CEId,EmployeeId) VALUES (?,?,?,?)");
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setInt(3,CEId);
        statement1.setInt(4,EmployeeId);
        statement1.execute();

        return EmployeeId;
    }

    public int hireContractorManager(String username, String password, int CMId) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int EmployeeId = helper.findEmployeeId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO ContractorManager (username,password,CMId,EmployeeId) VALUES (?,?,?,?)");
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setInt(3,CMId);
        statement1.setInt(4,EmployeeId);
        statement1.execute();

        return EmployeeId;
    }

    public int insertFamilyState(String state, int numberKids, String ages) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int stateId = helper.findStateId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO FamilyState (StateId,state,numberKids,ages) VALUES (?,?,?,?)");
        statement1.setInt(1,stateId);
        statement1.setString(2,state);
        statement1.setInt(3,numberKids);
        statement1.setString(4,ages);
        statement1.execute();

        return stateId;
    }

    public int insertBankInfo(int IBAN, String bankName) throws SQLException{
        HelperFunctions helper = new HelperFunctions();
        int bankId = helper.findBankId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO BankInfo (BankID,IBAN,bankName) VALUES (?,?,?)");
        statement1.setInt(1,bankId);
        statement1.setInt(2,IBAN);
        statement1.setString(3,bankName);
        statement1.execute();

        return bankId;
    }

    public int insertBonus(double famBonus, double searchBonus, double libBonus) throws SQLException {
        HelperFunctions helper = new HelperFunctions();
        int bonusId = helper.findBonusId();

        //double famBonus = calculateFamilyBonus();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO Bonus (BonusId,familyBonus,searchBonus,libraryBonus) VALUES (?,?,?,?)");
        statement1.setInt(1, bonusId);
        statement1.setDouble(2, famBonus);
        statement1.setDouble(3, searchBonus);
        statement1.setDouble(4, libBonus);
        statement1.execute();

        return bonusId;
    }

    /*check methods: helper functions*/
    public boolean check_EmployeeId_In_Database(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee WHERE EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public boolean check_PMId_In_Database(int PMId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM PermanentManager WHERE PMId=?");
        statement1.setInt(1, PMId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public boolean check_PEId_In_Database(int PEId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM PermanentEducator WHERE PEId=?");
        statement1.setInt(1, PEId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public boolean check_CMId_In_Database(int CMId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM ContractorManager WHERE CMId=?");
        statement1.setInt(1, CMId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public boolean check_CEId_In_Database(int CEId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM ContractorEducator WHERE CEId=?");
        statement1.setInt(1, CEId);
        ResultSet resultEmployee = statement1.executeQuery();

        if (resultEmployee.next()) return true;
        return false;
    }

    public boolean check_BankId_In_Database(int bankId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM BankInfo WHERE BankId=?");
        statement1.setInt(1, bankId);
        ResultSet resultBank = statement1.executeQuery();

        if (resultBank.next()) return true;
        return false;
    }

    public boolean check_StateId_In_Database(int stateId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM FamilyState WHERE StateId=?");
        statement1.setInt(1, stateId);
        ResultSet resultState = statement1.executeQuery();

        if (resultState.next()) return true;
        return false;
    }

    public boolean check_BonusId_In_Database(int bonusId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Bonus WHERE BonusId=?");
        statement1.setInt(1, bonusId);
        ResultSet resultBonus = statement1.executeQuery();

        if (resultBonus.next()) return true;
        return false;
    }

}
