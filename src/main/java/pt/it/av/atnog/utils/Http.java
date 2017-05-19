package pt.it.av.atnog.utils;

import pt.it.av.atnog.utils.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

//TODO: review the exception for error codes different from OK
//TODO: test try-resource to minize the amount of code
//TODO: review put method

/**
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Http {
  private static final int TIMEOUT = 10000;

  private static InputStream inputStream(HttpURLConnection con) throws IOException {
    InputStream rv;
    String encoding = con.getContentEncoding();
    if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
      rv = new GZIPInputStream(con.getInputStream());
    } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
      rv = new InflaterInputStream(con.getInputStream(), new Inflater(true));
    } else {
      rv = con.getInputStream();
    }
    return rv;
  }

  private static InputStream errorStream(HttpURLConnection con) throws IOException {
    InputStream rv;
    String encoding = con.getContentEncoding();
    if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
      rv = new GZIPInputStream(con.getErrorStream());
    } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
      rv = new InflaterInputStream(con.getErrorStream(), new Inflater(true));
    } else {
      rv = con.getErrorStream();
    }
    return rv;
  }

  public static String get(String url) throws Exception {
    return get(url, TIMEOUT);
  }

  /**
   * @param url
   * @param timeout
   * @return
   * @throws IOException
   */
  public static String get(String url, int timeout) throws IOException {
    String rv = null;
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) new URL(url).openConnection();
      con.setReadTimeout(timeout);
      con.setReadTimeout(timeout);
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "text/plain");
      con.setRequestProperty("Accept-Encoding", "gzip, deflate");
      con.setRequestProperty("User-Agent", "");
      con.connect();
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream(con)))) {
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
        }
        rv = response.toString();
      }
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
    return rv;
  }

  public static String get(String url, String user, String pass) throws IOException {
    return get(url, user, pass, TIMEOUT);
  }

  /**
   * @param url
   * @param user
   * @param pass
   * @param timeout
   * @return
   * @throws IOException
   */
  public static String get(String url, String user, String pass, int timeout) throws IOException {
    String rv = null;
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) new URL(url).openConnection();
      con.setReadTimeout(timeout);
      con.setReadTimeout(timeout);
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "text/plain");
      con.setRequestProperty("Authorization", "Basic "
              + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes()));
      con.setRequestProperty("Accept-Encoding", "gzip, deflate");
      con.setRequestProperty("User-Agent", "");
      con.connect();
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream(con)))) {
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
        }
        rv = response.toString();
      }
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
    return rv;
  }

  public static JSONObject getJson(String url) throws Exception {
    return getJson(url, TIMEOUT);
  }

  public static JSONObject getJson(String url, int timeout) throws IOException {
    JSONObject rv = null;
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) new URL(url).openConnection();
      con.setReadTimeout(timeout);
      con.setReadTimeout(timeout);
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Accept-Encoding", "gzip, deflate");
      con.setRequestProperty("User-Agent", "");
      con.connect();
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(con))));
      }
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
    return rv;
  }

  public static JSONObject getJson(String url, String user, String pass) throws Exception {
    return getJson(url, user, pass, TIMEOUT);
  }

  public static JSONObject getJson(String url, String user, String pass, int timeout)
          throws IOException {
    JSONObject rv = null;
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) new URL(url).openConnection();
      con.setReadTimeout(timeout);
      con.setReadTimeout(timeout);
      con.setRequestMethod("GET");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("Authorization", "Basic "
              + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes()));
      con.setRequestProperty("Accept-Encoding", "gzip, deflate");
      con.setRequestProperty("User-Agent", "");
      con.connect();
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(con))));
      }
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
    return rv;
  }

  //TODO: this method needs some love, like all the persons in the world...
  public static void post(String url, JSONObject json) throws IOException {
    post(url, json, TIMEOUT);
  }

  /**
   * @param url
   * @param json
   * @param timeout
   * @throws IOException
   */
  public static void post(String url, JSONObject json, int timeout) throws IOException {
    byte[] data = json.toString().getBytes("UTF-8");
    HttpURLConnection con = null;
    try {
      con = (HttpURLConnection) new URL(url).openConnection();
      con.setReadTimeout(timeout);
      con.setReadTimeout(timeout);
      con.setRequestMethod("POST");
      con.setRequestProperty("Content-Type", "application/json");
      con.setRequestProperty("charset", "utf-8");
      con.setRequestProperty("Accept-Encoding", "gzip, deflate");
      con.setRequestProperty("Content-Length", Integer.toString(data.length));
      con.setRequestProperty("User-Agent", "");
      con.setDoOutput(true);
      try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
        wr.write(data);
        wr.flush();
      }
      if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
        JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(con))));
      } else {
        throw new IOException("Error code: " + con.getResponseCode());
      }
    } finally {
      if (con != null) {
        con.disconnect();
      }
    }
  }
}
