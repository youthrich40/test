package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import sun.net.ftp.FtpDirEntry;

public class Database {

    private DAOuser userDAO;
    private DAOauth authDAO;
    private DAOevent eventDAO;
    private DAOperson personDAO;
    private Connection conn;

    public DAOuser getUserDAO() {
        return userDAO;
    }

    public DAOauth getAuthDAO() {
        return authDAO;
    }

    public DAOevent getEventDAO() {
        return eventDAO;
    }

    public DAOperson getPersonDAO() {
        return personDAO;
    }

    //Dr.Rodham's note https://students.cs.byu.edu/~cs240ta/spring2019/rodham_files/08-databases/Database.java
    static {
        try {
            final String driver = "org.sqlite.JDBC";
            Class.forName(driver);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws SQLException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:/Users/saejongjang/Downloads/familyserver/server/src/fms.db";

            // Open a database connection
            conn = DriverManager.getConnection(CONNECTION_URL);

            userDAO = new DAOuser(conn);
            authDAO = new DAOauth(conn);
            eventDAO = new DAOevent(conn);
            personDAO = new DAOperson(conn);

            // Start a transaction
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new SQLException("openConnection failed", e);
        }
    }

    public void closeConnection(boolean commit) throws SQLException {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }

            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            throw new SQLException("closeConnection failed", e);
        }
    }

    public void createTables() throws SQLException {
        try {
            Statement stmt = null;
            try {
                stmt = conn.createStatement();

                stmt.executeUpdate("create table if not exists AuthToken (" +
                        "authToken TEXT NOT NULL PRIMARY KEY," +
                        "userName TEXT NOT NULL" +
                        ")");
                stmt.executeUpdate("create table if not exists User (" +
                        "userName text not null UNIQUE PRIMARY KEY," +
                        "password text not null," +
                        "personID text not null," +
                        "firstName text not null," +
                        "lastName text not null," +
                        "gender text not null," +
                        "email text not null"+
                        ")");
                stmt.executeUpdate("create table if not exists Person (" +
                        "personID text not null UNIQUE PRIMARY KEY," +
                        "descendant text not null," +
                        "firstName text not null," +
                        "lastName text not null," +
                        "gender text not null," +
                        "father text not null," +
                        "mother text not null," +
                        "spouse text not null"+
                        ")");
                stmt.executeUpdate("create table if not exists Event (" +
                        "eventType text not null," +
                        "eventID text not null," +
                        "longitude text not null," +
                        "latitude text not null," +
                        "city text not null," +
                        "country text not null," +
                        "year text not null," +
                        "descendant text not null," +
                        "personID text not null UNIQUE PRIMARY KEY"+
                        ")");
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) {
            throw new SQLException("createTables failed", e);
        }
    }

    public void clearTables() throws SQLException{

    }
}
