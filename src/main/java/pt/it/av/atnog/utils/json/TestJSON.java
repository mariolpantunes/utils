package pt.it.av.atnog.utils.json;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mantunes on 3/28/15.
 */
public class TestJSON {
    public static void main(String[] args) throws IOException {
        JSONObject json = JSONObject.read(new FileReader("sample1.json"));
        System.out.println(json.toString());
        /*JSONObject j = new JSONObject();
        j.add("number",2);
        j.add("String", "\"banana\"");
        System.out.println(j.toString());*/
    }
}
