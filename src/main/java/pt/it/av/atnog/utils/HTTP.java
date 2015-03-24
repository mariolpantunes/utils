package pt.it.av.atnog.utils;

import com.eclipsesource.json.JsonObject;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//TODO: review the exception for error codes different from OK
public class HTTP {

    public static String get(String url) throws Exception {
        return get(url, 5000);
    }

    public static String get(String url, int timeout) throws IOException {
        String rv = null;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setReadTimeout(timeout);
        con.setReadTimeout(timeout);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "text/plain");
        con.connect();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);
            in.close();
            rv = response.toString();
        } else {
            throw new HTTPException(con.getResponseCode());
        }
        return rv;
    }

    public static JsonObject getJSON(String url) throws Exception {
        return getJSON(url, 5000);
    }

    public static JsonObject getJSON(String url, int timeout) throws IOException {
        JsonObject json = null;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setReadTimeout(timeout);
        con.setReadTimeout(timeout);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.connect();
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            json = JsonObject.readFrom(new InputStreamReader(con.getInputStream()));
        } else {
            throw new HTTPException(con.getResponseCode());
        }

        return json;
    }
}
