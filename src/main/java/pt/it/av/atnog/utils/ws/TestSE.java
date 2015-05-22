package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.PrintUtils;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.ws.search.SearchEngine;
import pt.it.av.atnog.utils.ws.thesaurus.BigHugeThesaurus;
import pt.it.av.atnog.utils.ws.thesaurus.Thesaurus;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class TestSE {
    public static void main(String[] args) throws Exception {
        JSONObject config = JSONObject.read(new BufferedReader(new InputStreamReader(new FileInputStream("ws.json"))));

        String w = "good";

        Thesaurus t = new BigHugeThesaurus(config.get("bighugethesaurus").asString());
        //Thesaurus t = new Altervista(config.get("altervista").asString().value());
        List<String> syn = t.synonyms(w);
        System.out.println("Results: " + syn.size());
        PrintUtils.printList(syn);
        List<String> ant = t.antonyms(w);
        System.out.println("Results: " + ant.size());
        PrintUtils.printList(ant);

        //SearchEngine s = new Bing(config.get("bing").asString().value());
        //SearchEngine s = new Faroo(config.get("faroo").asString().value());
        SearchEngine s = new DuckDuckGo();
        List<String> snippets = s.snippets("humidity");
        System.out.println("Results: " + snippets.size());
        PrintUtils.printList(snippets);
    }
}
