package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.Utils;
import pt.it.av.atnog.utils.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TestSE {
    public static void main(String[] args) throws Exception {
        JSONObject config = JSONObject.parse(new BufferedReader(new InputStreamReader(new FileInputStream("ws.json"))));
        //SearchEngine s = new Bing(config.get("bing").asString().value());
        //SearchEngine s = new Faroo(config.get("faroo").asString().value());
        SearchEngine s = new DuckDuckGo();
        List<String> results = s.snippets("humidity");
        System.out.println("Results: " + results.size());
        Utils.printList(results);
    }
}
