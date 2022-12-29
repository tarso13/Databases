package server;

import classes.*;
import com.mysql.cj.x.protobuf.MysqlxCrud;

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

    public PermanentManager getPM(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentManager where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPM = statement2.executeQuery();
        resultPM.next();

        PermanentManager pManager = new PermanentManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"),resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPM.getInt("PMId"));
        return pManager;
    }

    public void hirePM(int PMId, int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "INSERT INTO PermanentManager (PMId, EmployeeId,username,password) VALUES (?,?,?,?)");
        statement1.setInt(1,PMId);
        statement1.setInt(2,EmployeeId);
        statement1.setString(3,"PMId");
        statement1.setString(4,"PMId");
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
        resultPE.next();

        PermanentEducator pEducator = new PermanentEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"),resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("PEId"));
        return pEducator;
    }

    public ContractorManager getCM(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentEducator where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        resultPE.next();

        ContractorManager cManager = new ContractorManager(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"),resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("CMId"));
        return cManager;
    }
    public ContractorEducator getCE(int EmployeeId) throws SQLException {
        PreparedStatement statement1 = selectStatement(connector,
                "SELECT * FROM Employee where EmployeeId=?");
        statement1.setInt(1, EmployeeId);
        ResultSet resultEmployee = statement1.executeQuery();
        resultEmployee.next();

        PreparedStatement statement2 = selectStatement(connector,
                "SELECT * FROM PermanentEducator where EmployeeId=?");
        statement2.setInt(1, EmployeeId);
        ResultSet resultPE = statement2.executeQuery();
        resultPE.next();

        ContractorEducator cEducator = new ContractorEducator(resultEmployee.getString("firstName"),
                resultEmployee.getString("lastName"),resultEmployee.getString("address"),
                resultEmployee.getInt("phoneNumber"), resultEmployee.getDate("beginHiringDate"), resultPE.getInt("CEId"));
        return cEducator;
    }

}
