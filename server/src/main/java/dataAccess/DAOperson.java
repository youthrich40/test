package dataAccess;

import java.sql.Connection;

public class DAOperson {
    private Connection conn;

    public DAOperson(Connection conn) {
        this.conn = conn;
    }

    public void delete(String userName) {
        String query = "delete from Person where descendant = ?";
    }
}
