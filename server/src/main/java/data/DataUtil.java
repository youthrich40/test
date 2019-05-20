package data;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import request.FillRequest;

public class DataUtil {

    private JsonModel fnames;
    private JsonModel mnames;
    private JsonModel snames;
    private JsonLocationModel jsonLocations;

    public static String IDgenerator(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void setUpFakeJSON(){
        Gson gson = new Gson();

        try {
            fnames = gson.fromJson(new JsonReader(new FileReader("/Users/saejongjang/Downloads/familyserver/server/src/json/fnames.json")), JsonModel.class);
            mnames = gson.fromJson(new JsonReader(new FileReader("/Users/saejongjang/Downloads/familyserver/server/src/json/mnames.json")), JsonModel.class);
            snames = gson.fromJson(new JsonReader(new FileReader("/Users/saejongjang/Downloads/familyserver/server/src/json/snames.json")), JsonModel.class);
            jsonLocations = gson.fromJson(new JsonReader(new FileReader("/Users/saejongjang/Downloads/familyserver/server/src/json/locations.json")), JsonLocationModel.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Person generateFakeAncestor(String gender, FillRequest fillRequest, String spouseID){
        String descendant = fillRequest.getUserName();
        Double index = mnames.getJson().size() * Math.random();

        String ranFirstName = getRandomFirstName(gender);;
        String ranLastName = (String) snames.getJson().get(index.intValue());
        String personID = UUID.randomUUID().toString();

        //TODO: set randomized father and mother

        Person ancestor = new Person(personID, descendant, ranFirstName, ranLastName, gender, null, null, null);

        return ancestor;
    }

    private String getRandomFirstName(String gender){
        String ranFirstName = new String();
        Double index;

        if(gender.equals("m")){
            index = mnames.getJson().size() * Math.random();
            ranFirstName = (String) mnames.getJson().get(index.intValue());
        }
        else if(gender.equals("f")){
            index = fnames.getJson().size() * Math.random();
            ranFirstName = (String) fnames.getJson().get(index.intValue());
        }
        else{
            return "error occur";
        }
        return ranFirstName;
    }

    public JsonModel getFnames() {
        return fnames;
    }

    public JsonModel getMnames() {
        return mnames;
    }

    public JsonModel getSnames() {
        return snames;
    }

    public JsonLocationModel getJsonLocations() {
        return jsonLocations;
    }
}
