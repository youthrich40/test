package data;

import java.util.ArrayList;
import java.util.List;

public class JsonLocationModel {
    private List<Location> data = new ArrayList<>();

    public List<Location> getJson() {
        return data;
    }

    public void setJson(List<Location> data) {
        this.data = data;
    }
}
