package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.User;

public class DAOuser {

    private Connection conn;

    public DAOuser(Connection conn) {
        this.conn = conn;
    }

    private User retrieveUser(ResultSet resultSet){
        User user = null;

        try {
            String username = resultSet.getString(1);
            String password = resultSet.getString(2);
            String personID = resultSet.getString(3);
            String firstname = resultSet.getString(4);
            String lastname = resultSet.getString(5);
            String gender = resultSet.getString(6);
            String email = resultSet.getString(7);

            user = new User(username, password, personID, firstname, lastname, gender, email);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByUsername(String username) {
        User user = null;
        String query = "select * from User where userName = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                user = retrieveUser(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void insert(User user) {
        String query = "insert into User values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getPersonID());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getGender());
            pstmt.setString(7, user.getEmail());

            pstmt.executeUpdate();
            System.out.println("user " + user.getUserName() + " inserted");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void retrieve(String username, String password) {
    }
}
