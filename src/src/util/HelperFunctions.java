package util;

import server.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HelperFunctions {
    static ServerRequest request;
    public HelperFunctions() {}

    public boolean check_null_username_password(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()){
            System.err.println("Cannot give a username and password that is null!\n");
            return true;
        }
        return false;
    }

    public int findEmployeeId() throws SQLException {
        int i=0;
        while (request.check_EmployeeId_In_Database(i)){
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
}
