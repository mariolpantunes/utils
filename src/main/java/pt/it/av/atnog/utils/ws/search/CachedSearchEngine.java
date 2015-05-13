package pt.it.av.atnog.utils.ws.search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 4/15/15.
 */

//TODO: its a dirty fix to improve performance on other code
//TODO: remove the limitation of docs....
public class CachedSearchEngine implements SearchEngine {
    private final SearchEngine se;

    public CachedSearchEngine(SearchEngine se) {
        this.se = se;
    }

    @Override
    public List<String> search(String q) {
        List<String> rv = new ArrayList<>();
        //System.out.println("Loading file search_" + q + ".dat");
        try (BufferedReader br = new BufferedReader(new FileReader("search_" + q + ".dat"))) {
            String line = br.readLine();
            while (line != null) {
                rv.add(line);
                line = br.readLine();

            }
        } catch (FileNotFoundException e) {
            //System.out.println("File search_" + q + ".dat not found");
            //System.out.println("Download data from search engine");
            rv = se.search(q);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("search_" + q + ".dat"))) {
                for (String s : rv) {
                    bw.write(s);
                    bw.write(System.lineSeparator());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public List<String> snippets(String q) {
        List<String> rv = new ArrayList<>();
        //System.out.println("Loading file snippet_" + q + ".dat");
        try (BufferedReader br = new BufferedReader(new FileReader("snippet_" + q + ".dat"))) {
            String line = br.readLine();
            while (line != null) {
                rv.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            //System.out.println("File snippet_" + q + ".dat not found");
            //System.out.println("Download data from search engine");
            rv = se.snippets(q);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("snippet_" + q + ".dat"))) {
                for (String s : rv) {
                    bw.write(s);
                    bw.write(System.lineSeparator());
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }
}
