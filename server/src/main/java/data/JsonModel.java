package data;

import java.util.ArrayList;
import java.util.List;

public class JsonModel <T extends Object> {
    private List<T> data = new ArrayList<>();

    public List<T> getJson() {
        return data;
    }

    public void setJson(List<T> data) {
        this.data = data;
    }
}
