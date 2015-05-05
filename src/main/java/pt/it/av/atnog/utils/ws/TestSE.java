package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TestSE {
    public static void main(String[] args) throws Exception {
        JSONObject config = JSONObject.parse(new BufferedReader(new InputStreamReader(new FileInputStream("ws.json"))));
        //SearchEngine s = new Bing(config.get("bing").asString());
        //SearchEngine s = new Faroo(config.get("faroo").asString());
        SearchEngine s = new DuckDuckGo();
        Utils.printList(s.search("humidity"));
    }
}
