package facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import data.DataUtil;
import data.Person;
import data.User;
import dataAccess.Database;
import dataAccess.DatabaseUtil;
import request.FillRequest;
import response.FillResponse;

public class FillSerivce extends DataUtil {
    private Database database = new Database();
    private DataUtil dataUtil = new DataUtil();
    private DatabaseUtil databaseUtil = new DatabaseUtil();
    private FillResponse fillResponse;
    private User user;

    private int countPersons;
    private int countEvents;

    private List<Person> paternal = new ArrayList<>();
    private List<Person> maternal = new ArrayList<>();

    public FillResponse fill(FillRequest fillRequest) {

        databaseUtil.dbOpen(database);
        countPersons = 0;
        countEvents = 0;

        if(isValidRequest(fillRequest) == true){
            fillResponse = new FillResponse();
            dataUtil.setUpFakeJSON();
        }

        //set up fake tree
        user = database.getUserDAO().getUserByUsername(fillRequest.getUserName());
        populateFakeTree(fillRequest);

        databaseUtil.dbClose(database);

        fillResponse.setMessage("Successfully added "
                + Integer.toString(countPersons)
                + " people and " + Integer.toString(countEvents)
                + " events to the database.");
        return fillResponse;
    }

    private void populateFakeTree(FillRequest fillRequest) {
        Person male = new Person();
        male.setPersonID(UUID.randomUUID().toString());

        Person female = new Person();
        female.setPersonID(UUID.randomUUID().toString());

        male = dataUtil.generateFakeAncestor("m", fillRequest, female.getPersonID());
        female = dataUtil.generateFakeAncestor("f", fillRequest, male.getPersonID());

        countGenerations(fillRequest);

        paternal.add(male);
        maternal.add(female);
    }

    private void countGenerations(FillRequest fillRequest){
        int generations = fillRequest.getGenerations();

        while(generations>0){

            int population = (int) (Math.pow(2, generations))/2;

            for(int i=population; i>0; --i){
                if(i != population){
                    recursiveAncestor(fillRequest.getUserName(), null, generations);
                }
                else{
                    recursiveAncestor(fillRequest.getUserName(), user.getLastName(), generations);
                }
            }

            --generations;
        }
    }

    private void recursiveAncestor(String descendant, String lastName, int generations) {
        if(lastName == null){
        }
    }

    private boolean isValidRequest(FillRequest fillRequest) {
        if(fillRequest.getGenerations() < 1) {
            fillResponse.setMessage("invalid generations");
            databaseUtil.dbClose(database);
            return false;
        }
        if(database.getUserDAO().getUserByUsername(fillRequest.getUserName()) == null){
            fillResponse.setMessage("cannot find user");
            databaseUtil.dbClose(database);
            return false;
        }
        else {
            database.getEventDAO().delete(fillRequest.getUserName());
            database.getPersonDAO().delete(fillRequest.getUserName());
            user = database.getUserDAO().getUserByUsername(fillRequest.getUserName());
            return true;
        }
    }
}
