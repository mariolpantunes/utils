package pt.it.av.atnog.utils.ws.search;

import pt.it.av.atnog.utils.json.JSONArray;
import pt.it.av.atnog.utils.json.JSONObject;
import pt.it.av.atnog.utils.json.JSONString;
import pt.it.av.atnog.utils.json.JSONValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mantunes on 4/15/15.
 */
// TODO: Maybe search operration should return a valid url instead of a string
public class CachedSearchEngine implements SearchEngine {
    private final SearchEngine se;
    private final Path path;
    private final long timeout;

    public CachedSearchEngine(SearchEngine se) throws IOException {
        this(se, Paths.get(".cache"), 0);
    }

    public CachedSearchEngine(SearchEngine se, Path path, long timeout) throws IOException {
        this.timeout = timeout;
        this.se = se;
        this.path = path;
        if (!Files.isDirectory(path))
            throw new NotDirectoryException("");
    }

    @Override
    public List<String> search(final String q) {
        List<String> rv = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(path.resolve("search_" + q + ".dat"), StandardCharsets.UTF_8)) {
            JSONObject j = JSONObject.read(r);
            if (j.contains("search")) {
                JSONObject search = j.get("search").asObject();
                ZonedDateTime date = ZonedDateTime.parse(j.get("date").asString()),
                        now = ZonedDateTime.now();

                JSONArray array = search.get("results").asArray();
                for (JSONValue v : array)
                    rv.add(v.asString());
            } else {
                rv = se.search(q);
                JSONArray array = new JSONArray();
                for (String s : rv)
                    array.add(new JSONString(s));
                JSONObject search = new JSONObject();
                search.add("results", array);
                j.add("search", search);
            }
        } catch (NoSuchFileException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public List<String> snippets(String q) {
        List<String> rv = new ArrayList<>();
        Path file = path.resolve("search_" + q + ".dat");
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            JSONObject j = JSONObject.read(r);
            if (j.contains("snippets")) {
                JSONArray array = j.get("snippets").asArray();
                for (JSONValue v : array)
                    rv.add(v.asString());
            } else {
                rv = se.snippets(q);
                JSONArray array = new JSONArray();
                for (String s : rv)
                    array.add(new JSONString(s));
                j.add("snippets", array);
                try (BufferedWriter bw = Files.newBufferedWriter(file)) {
                    j.write(bw);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (NoSuchFileException e) {
            rv = se.snippets(q);
            JSONArray array = new JSONArray();
            for (String s : rv)
                array.add(new JSONString(s));
            JSONObject j = new JSONObject();
            j.add("snippets", array);
            try (BufferedWriter bw = Files.newBufferedWriter(file)) {
                j.write(bw);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rv;
    }
}