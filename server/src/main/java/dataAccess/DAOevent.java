package dataAccess;

import java.sql.Connection;

public class DAOevent {
    private Connection conn;

    public DAOevent(Connection conn) {
        this.conn = conn;
    }

    public void delete(String userName) {
        String query = "delete from Event where descendant = ?";
    }
}
