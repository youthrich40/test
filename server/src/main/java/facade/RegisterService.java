package facade;

import data.User;
import data.DataUtil;
import dataAccess.Database;
import dataAccess.DatabaseUtil;
import request.LoginRequest;
import request.RegisterRequest;
import response.LoginResponse;
import response.RegisterResponse;

public class RegisterService extends DataUtil {

    private Database database = new Database();
    private LoginService loginService = new LoginService();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String personID;

    private boolean isUserExist(String username){
        if(database.getUserDAO().getUserByUsername(username) != null){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isValidRequest(RegisterRequest request){

        if(request.getUserName() == null || request.getUserName().equals("")){
            return false;
        }
        if(request.getPassword() == null || request.getPassword().equals("")){
            return false;
        }
        if(request.getFirstName() == null || request.getFirstName().equals("")){
            return false;
        }
        if(request.getLastName() == null || request.getLastName().equals("")){
            return false;
        }
        if(request.getEmail() == null || request.getEmail().equals("")){
            return false;
        }

        String gender = request.getGender().toLowerCase();

        if(gender.equals("m/f")){
            gender = "m";
        }

        if(gender.equals("m") || gender.equals("f")){
            return true;
        }
        else{
            return false;
        }
    }

    private RegisterResponse prepareResponse(User user) {
        RegisterResponse response = new RegisterResponse();

        response.setUserName(user.getUserName());
        response.setPersonID(user.getPersonID());

        LoginRequest loginRequest = new LoginRequest(user.getUserName(), user.getPassword());
        LoginResponse loginResponse = loginService.login(loginRequest);
        response.setAuthtoken(loginResponse.getAuthToken());

        response.setMessage("Register success");

        return response;
    }

    private void prepareRequest(RegisterRequest request) {
        userName = request.getUserName();
        password = request.getPassword();
        firstName = request.getFirstName();
        lastName = request.getLastName();
        gender = request.getGender();
        email = request.getEmail();
        personID = IDgenerator();
    }

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        databaseUtil.dbOpen(database);
        databaseUtil.createTable(database);

        //if user not found in the database, register user into database
        if (isValidRequest(request) == true) {
            if (isUserExist(request.getUserName()) == false) {

                prepareRequest(request);
                User user = new User(userName, password, personID, firstName, lastName, gender, email);
                database.getUserDAO().insert(user);
                databaseUtil.dbClose(database);
                response = prepareResponse(user);

            } else {
                //handle user already exist
                response.setMessage("user already exist");
                System.out.println("user already exist");
                databaseUtil.dbClose(database);
            }
        } else {
            // handle invalid request
            response.setMessage("invalid register request or missing property");
            System.out.println("invalid register request or missing property");
            databaseUtil.dbClose(database);
        }

        return response;
    }

}
