package pt.it.av.atnog.utils.ws;


import com.eclipsesource.json.JsonObject;
import pt.it.av.atnog.utils.Utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestSE {
    public static void main(String[] args) throws Exception {
        JsonObject config = JsonObject.readFrom(new BufferedReader(new InputStreamReader(new FileInputStream("ws.json"))));
        //SearchEngine s = new Bing(config.get("bing").asString());
        SearchEngine s = new DuckDuckGo();
        Utils.printList(s.search("humidity"));
    }
}
