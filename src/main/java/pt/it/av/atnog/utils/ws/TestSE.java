package pt.it.av.atnog.utils.ws;

import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.ws.search.SearchEngine;
import pt.it.av.atnog.utils.ws.search.YaCy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TestSE {
    public static void main(String[] args) throws Exception {
        JSONObject config = JSONObject.read(new BufferedReader(new InputStreamReader(new FileInputStream("ws.json"))));
        String w = "good", q = "banana";
        //Thesaurus t = new BigHugeThesaurus(config.get("bighugethesaurus").asString());
        //Thesaurus t = new Altervista(config.get("altervista").asString().value());
        //List<String> syn = t.synonyms(w);
        //System.out.println("Results: " + syn.size());
        //PrintUtils.printList(syn);
        //List<String> ant = t.antonyms(w);
        //System.out.println("Results: " + ant.size());
        //PrintUtils.printList(ant);
        //SearchEngine s = new Bing(config.get("bing").asString().value());
        //SearchEngine s = new Faroo(config.get("faroo").asString().value());
        //SearchEngine s = new DuckDuckGo();
        System.out.println("Search Engine Test");
        //SearchEngine s = new Faroo(config.get("faroo").asString());
        //SearchEngine s = new Searx();
        SearchEngine s = new YaCy();
        Set<String> s1 = new LinkedHashSet<>(s.snippets(q));
        List<String> l = new ArrayList<>();
        Set<String> s2 = new LinkedHashSet<>();
        Iterator<String> it = s.snippetsIt(q);

        while (it.hasNext()) {
            String str = it.next();
            System.out.println(str);
            l.add(str);
        }

        s2.addAll(l);

        if (s1.equals(s2))
            System.out.println("Equals");
        else
            System.out.println("Not equal");

        System.out.println("Results 1: " + s1.size());
        System.out.println("Results 2: " + s2.size());
        //System.out.println(PrintUtils.list(snippets));
    }
}
