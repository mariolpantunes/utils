package pt.it.av.atnog.utils.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 26/03/2015.
 */
public class JSONArray implements JSONValue {
    private List<JSONValue> list = new ArrayList<>();

    public void add(JSONValue value) {
        list.add(value);
    }


    @Override
    public void toString(StringBuilder sb) {

    }
}
