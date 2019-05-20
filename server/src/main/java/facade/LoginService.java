package facade;

import data.User;
import dataAccess.Database;
import dataAccess.DatabaseUtil;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService {
    private Database database = new Database();
    private String invalidCode;
    private DatabaseUtil databaseUtil = new DatabaseUtil();

    private boolean isValidRequest(LoginRequest request){

        if(request.getUserName() == null || request.getUserName().equals("")){
            return false;
        }
        if(request.getPassword() == null || request.getPassword().equals("")){
            return false;
        }
        return true;
    }

    private boolean isValidUser(LoginRequest request){
        User user = null;
        if(database.getUserDAO().getUserByUsername(request.getUserName()) != null){
            user = database.getUserDAO().getUserByUsername(request.getUserName());
            if(user.getUserName().equals(request.getUserName()) && user.getPassword().equals(request.getPassword())){
                return true;
            }
            else {
                invalidCode = "invalid property";
                return false;
            }
        }
        else {
            invalidCode = "user not exist";
            return false;
        }
    }

    private LoginResponse prepareResponse(User user) {
        LoginResponse response = new LoginResponse();

        response.setMessage("login sucess");
        response.setPersonID(user.getPersonID());
        response.setAuthToken(user.getAuthToken());

        return response;
    }

    public LoginResponse login(LoginRequest request){
        LoginResponse response = new LoginResponse();
        databaseUtil.dbOpen(database);

        //if user not found in the database, register user into database
        if(isValidRequest(request) == true){
            if(isValidUser(request) == true) {
                String username = request.getUserName();

                User user = database.getUserDAO().getUserByUsername(username);
                user.setAuthToken();
                database.getAuthDAO().updateAuth(user.getUserName(), user.getAuthToken());
                databaseUtil.dbClose(database);

                response = prepareResponse(user);
            }
            else{
                //handle user already exist or incorrect username and password
                response.setMessage(invalidCode);
                System.out.println(invalidCode);
            }
        }
        else{
            // handle invalid request
            response.setMessage("invalid login request or missing property");
            System.out.println("invalid login request or missing property");
        }


        return response;
    }

}
