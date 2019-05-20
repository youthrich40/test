package dataAccess;

import java.sql.SQLException;

public class DatabaseUtil {

    public void dbOpen(Database database){
        try {
            database.openConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dbClose(Database database){
        try {
            database.closeConnection(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(Database database){
        try {
            database.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
