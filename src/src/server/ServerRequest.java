package server;

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
}
