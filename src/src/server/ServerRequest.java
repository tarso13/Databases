package server;

import classes.*;

import java.util.Date;
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

    /*login Employee with username password*/
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

        /*select information from getTABLENAME from database and set the specific employee this info*/
        FamilyState state = getFamilyState(resultEmployee.getInt("StateId"));
        pEducator.setFamilyState(state);

        Bonus bonus = getBonus(resultEmployee.getInt("BonusId"), pEducator.calculateFamilyBonus());
        pEducator.setBonus(bonus);

        BankInfo bankInfo = getBankInfo(resultEmployee.getInt("BankId"));
        pEducator.setBankInfo(bankInfo);

//        EmployeesSalary salary = new EmployeesSalary(1500,600,6,0);
//        pEducator.setEmployeesSalary(salary);
//
//        if (state != null){
//            EmployeesSalary salary2 = pEducator.getEmployeesSalary();
//            salary = insertEmployeesSalary(state.getStateID(), salary2.getBasicSalary(),salary2.getContractSalary(),salary2.getMonthsContract());
//            state.setFamilyStateSalary(salary);
//            pEducator.setSalary();
//        }
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

    public Bonus getBonus(int BonusId, double familyBonus) throws SQLException{
        PreparedStatement statement1 = selectStatement(connector,
                "UPDATE Bonus SET familyBonus=? WHERE BonusId=?");
        statement1.setInt(1, BonusId);
        statement1.setDouble(2, familyBonus);
        statement1.execute();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM Bonus WHERE BonusId=?");
        statement2.setInt(1, BonusId);
        ResultSet resultBonus = statement2.executeQuery();
        if (!resultBonus.next()) return null;

        Bonus bonus = new Bonus(BonusId, familyBonus, resultBonus.getDouble("searchBonus"),
                resultBonus.getDouble("libraryBonus"));
        return bonus;
    }

    public FamilyState getFamilyState(int stateId) throws SQLException{
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM FamilyState WHERE StateId=?");
        statement1.setInt(1, stateId);
        ResultSet resultFamState = statement1.executeQuery();
        if (!resultFamState.next()) return null;

        FamilyState state = new FamilyState(resultFamState.getString("state"), resultFamState.getInt("numberKids"),
                resultFamState.getString("ages"), stateId);
        return state;
    }

    public BankInfo getBankInfo(int bankId) throws SQLException{
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM BankInfo WHERE BankId=?");
        statement1.setInt(1, bankId);
        ResultSet resultBank = statement1.executeQuery();
        if (!resultBank.next()) return null;

        BankInfo info = new BankInfo(bankId,resultBank.getInt("IBAN"),
                resultBank.getString("bankName"));
        return info;
    }


    /*insert columns into tables*/
    public int hirePermanentEmployee(PermanentEducator employee,String username, String password,BankInfo bankInfo,FamilyState state) throws SQLException {
        int EmployeeId = findEmployeeId();
        int bonusId = insertBonus(employee.calculateFamilyBonus(),employee.getBonus().getSearchBonus(),employee.getBonus().getSearchBonus()).getBonusId();

        String[] infoEmployeeString={employee.getFirstName(), employee.getLastName(), employee.getAddress()};
        //int[] infoEmployeeInteger={employee.getPhoneNumber(),state.getStateID(),bankInfo.getBankID(),bonusId,employee.getEmployeesSalary().getSalaryID()};
        //insertEmployee(EmployeeId, infoEmployeeString, employee.getBeginHiringDate(), infoEmployeeInteger);

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO PermanentEducator (username,password,PEId,EmployeeId) VALUES (?,?,?,?)");
        statement1.setString(1,username);
        statement1.setString(2,password);
        statement1.setInt(3,employee.getPEId());
        statement1.setInt(4,EmployeeId);
        statement1.execute();

        return EmployeeId;
    }

    public void insertEmployee(int EmployeeId, String[] infoStr, Date date, int[] infoInt) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO Employee (EmployeeId,firstName,lastName, beginHiringDate, address, phoneNumber, StateId, BankId, BonusId,SalaryId) VALUES (?,?,?,?,?,?,?,?,?,?)");
        statement1.setInt(1,EmployeeId);
        statement1.setString(2,infoStr[0]);
        statement1.setString(3,infoStr[1]);
        statement1.setDate(4, (java.sql.Date) date);
        statement1.setString(5,infoStr[2]);
        statement1.setInt(6,infoInt[0]);
        statement1.setInt(7,infoInt[1]);
        statement1.setInt(8,infoInt[2]);
        statement1.setInt(9,infoInt[3]);
        statement1.setInt(10,infoInt[4]);
        statement1.execute();
    }

    public FamilyState insertFamilyState(String state, int numberKids, String ages, EmployeesSalary salary) throws SQLException {
        int stateId = findStateId();
        int salaryId = findSalaryId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO FamilyState (state,numberKids,ages,StateID,SalaryId) VALUES (?,?,?,?,?)");
        statement1.setString(1,state);
        statement1.setInt(2,numberKids);
        statement1.setString(3,ages);
        statement1.setInt(4,stateId);
        statement1.setInt(5,salaryId);
        statement1.execute();

        EmployeesSalary salary1 = insertEmployeesSalary(stateId,salary.getBasicSalary(),salary.getContractSalary(),salary.getMonthsContract());

        FamilyState familyState = new FamilyState(state, numberKids, ages, stateId);
        familyState.setFamilyStateSalary(salary1);
        return familyState;
    }

    public BankInfo insertBankInfo(int IBAN, String bankName) throws SQLException{
        int bankId = findBankId();

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO BankInfo (bankID,IBAN,bankName) VALUES (?,?,?)");
        statement1.setInt(1,bankId);
        statement1.setInt(2,IBAN);
        statement1.setString(3,bankName);
        statement1.execute();

        BankInfo bankInfo= new BankInfo(bankId,IBAN, bankName);
        return bankInfo;
    }

    public Bonus insertBonus(double famBonus,  double searchBonus, double libBonus) throws SQLException {
        int i=0;
        while (check_BankId_In_Database(i)){
            i++;
        }

        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO Bonus (BonusId,familyBonus,searchBonus,libraryBonus) VALUES (?,?,?,?)");
        statement1.setInt(1, i);
        statement1.setDouble(2, famBonus);
        statement1.setDouble(3, searchBonus);
        statement1.setDouble(4, libBonus);
        statement1.execute();

        Bonus bankInfo = new Bonus(i, famBonus, searchBonus, libBonus);
        return bankInfo;
    }

    public EmployeesSalary insertEmployeesSalary(int stateId, double basicSalary, double conSalary, int months) throws SQLException{
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT SalaryId FROM FamilyState WHERE StateId=?");
        statement1.setInt(1, stateId);
        ResultSet resultFamState = statement1.executeQuery();
        if (!resultFamState.next()) return null;

        PreparedStatement statement3 = selectStatement(connector,
                "INSERT INTO EmployeesSalary (basicSalary,contractSalary,monthsContract,SalaryId) VALUES (?,?,?,?)");
        statement3.setDouble(1,basicSalary);
        statement3.setDouble(2,conSalary);
        statement3.setInt(3,months);
        statement3.setInt(4,resultFamState.getInt("SalaryId"));
        statement3.execute();

        return new EmployeesSalary(basicSalary,conSalary,months,resultFamState.getInt("SalaryId"));
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

    public boolean check_SalaryId_In_Database(int salaryId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM EmployeesSalary WHERE SalaryId=?");
        statement1.setInt(1, salaryId);
        ResultSet resultSalary = statement1.executeQuery();

        if (resultSalary.next()) return true;
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

    public boolean check_null_username_password(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            System.err.println("Cannot give a username and password that is null!\n");
            return true;
        }
        return false;
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

    public void changeFamilyState(String state, int kids, String ages, int stateId, int EmployeeId) throws SQLException{
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
    }

    int findEmployeeId() throws SQLException {
        int i=0;
        while (check_EmployeeId_In_Database(i)){
            i++;
        }
        return i;
    }

    int findBankId() throws SQLException {
        int i=0;
        while (check_BankId_In_Database(i)){
            i++;
        }
        return i;
    }

    int findStateId() throws SQLException {
        int i=0;
        while (check_StateId_In_Database(i)){
            i++;
        }
        return i;
    }

    int findSalaryId() throws SQLException {
        int i=0;
        while (check_SalaryId_In_Database(i)){
            i++;
        }
        return i;
    }


}
