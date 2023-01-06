package util;

import server.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelperFunctions {
    static ServerRequest request = new ServerRequest();
    public HelperFunctions() {}

    public boolean check_null_username_password(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            System.err.println("Cannot give a username and password that is null!\n");
            return true;
        }
        return false;
    }

    public int hireEmployee(String groupEmployer, String JobDepartment, String username, String password) throws SQLException {
        int PEId = findPEId();
        int PMId = findPMId();
        int CEId = findCEId();
        int CMId = findCMId();

        if (groupEmployer.equals("Permanent")){
            if (JobDepartment.equals("Educator")) return request.hirePermanentEducator(username, password, PEId);
            return request.hirePermanentManager(username, password, PMId);
        } else{
            if (JobDepartment.equals("Educator")) return request.hireContractorEducator(username, password, CEId);
            return request.hireContractorManager(username, password, CMId);
        }
    }

    public int findEmployeeId() throws SQLException {
        int i=0;
        while (request.check_EmployeeId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findPMId() throws SQLException {
        int i=0;
        while (request.check_PMId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findPEId() throws SQLException {
        int i=0;
        while (request.check_PEId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findCMId() throws SQLException {
        int i=0;
        while (request.check_CMId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findCEId() throws SQLException {
        int i=0;
        while (request.check_CEId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findBankId() throws SQLException {
        int i=0;
        while (request.check_BankId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findStateId() throws SQLException {
        int i=0;
        while (request.check_StateId_In_Database(i)){
            i++;
        }
        return i;
    }

    public int findBonusId() throws SQLException {
        int i=0;
        while (request.check_BonusId_In_Database(i)){
            i++;
        }
        return i;
    }
}
