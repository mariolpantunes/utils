package pt.it.av.tnav.utils;

import pt.it.av.tnav.utils.json.JSONObject;

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
 * HTTP/REST helper class.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Http {
  private static final int DEFAULT_TIMEOUT_MS = 5000,
      DEFAULT_MAX_RETRIES = 3, DEFAULT_RETRY_DELAY_MS = 1000;
  //private static final int MAX_REDIRECT = 10;

  /**
   * Utility class, lets make the constructor private.
   */
  private Http() {
  }

  /**
   * @param con
   * @return
   * @throws IOException
   */
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

  /**
   * @param con
   * @return
   * @throws IOException
   */
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

  /**
   * @param url
   * @return
   * @throws Exception
   */
  public static String get(String url) throws Exception {
    return get(url, DEFAULT_TIMEOUT_MS);
  }

  /**
   * @param url
   * @param timeout
   * @return
   * @throws IOException
   */
  public static String get(String url, int timeout) throws IOException {
    String rv = null;
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setInstanceFollowRedirects(true);
      conn.setReadTimeout(timeout);
      conn.setReadTimeout(timeout);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "text/plain");
      conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
      conn.setRequestProperty("User-Agent", "");
      conn.connect();
      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream(conn)))) {
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
        }
        rv = response.toString();
      }
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return rv;
  }

  public static String get(String url, String user, String pass) throws IOException {
    return get(url, user, pass, DEFAULT_TIMEOUT_MS);
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
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setInstanceFollowRedirects(true);
      conn.setReadTimeout(timeout);
      conn.setReadTimeout(timeout);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "text/plain");
      conn.setRequestProperty("Authorization", "Basic "
          + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes()));
      conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
      conn.setRequestProperty("User-Agent", "");
      conn.connect();
      if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream(conn)))) {
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
        }
        rv = response.toString();
      }
    } finally {
      if (conn != null) {
        conn.disconnect();
      }
    }
    return rv;
  }

  /**
   * @param url
   * @return
   * @throws IOException
   */
  public static JSONObject getJson(String url) throws IOException {
    return getJson(url, DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES);
  }

  /**
   * @param url
   * @param timeout
   * @return
   * @throws IOException
   */
  public static JSONObject getJson(final String url, final int timeout, final int retries) {
    JSONObject rv = null;
    HttpURLConnection conn = null;
    boolean done = false;
    for (int i = 0; i < retries && !done; i++) {
      try {
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("User-Agent", "");
        conn.connect();
        switch (conn.getResponseCode()) {
          case HttpURLConnection.HTTP_OK:
            rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(conn))));
            done = true;
            break;
          case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
            break;
          case HttpURLConnection.HTTP_UNAVAILABLE:
            break;
          case (HttpURLConnection.HTTP_CLIENT_TIMEOUT):
            break;
          default:
            done = true;
            break;
        }
        if (rv == null) {
          Thread.sleep(DEFAULT_RETRY_DELAY_MS);
        }
      } catch (IOException | InterruptedException e) {
        rv = null;
      } finally {
        if (conn != null) {
          conn.disconnect();
        }
      }
    }
    return rv;
  }

  /**
   * @param url
   * @param user
   * @param pass
   * @return
   * @throws Exception
   */
  public static JSONObject getJson(String url, String user, String pass) throws IOException {
    return getJson(url, user, pass, DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES);
  }

  /**
   * @param url
   * @param user
   * @param pass
   * @param timeout
   * @param retries
   * @return
   */
  public static JSONObject getJson(final String url, final String user,
                                   final String pass, final int timeout, final int retries) {
    JSONObject rv = null;
    HttpURLConnection conn = null;
    boolean done = false;
    for (int i = 0; i < retries && !done; i++) {
      try {
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.setReadTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic "
            + Base64.getEncoder().encodeToString((user + ":" + pass).getBytes()));
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("User-Agent", "");
        conn.connect();
        switch (conn.getResponseCode()) {
          case HttpURLConnection.HTTP_OK:
            rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(conn))));
            done = true;
            break;
          case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
            break;
          case HttpURLConnection.HTTP_UNAVAILABLE:
            break;
          case (HttpURLConnection.HTTP_CLIENT_TIMEOUT):
            break;
          default:
            done = true;
            break;
        }
        if (rv == null) {
          Thread.sleep(DEFAULT_RETRY_DELAY_MS);
        }
      } catch (IOException | InterruptedException e) {
        rv = null;
      } finally {
        if (conn != null) {
          conn.disconnect();
        }
      }
    }
    return rv;
  }


  /**
   *
   * @param url
   * @param xMashapeKey
   * @return
   * @throws IOException
   */
  public static JSONObject getJson(String url, String xMashapeKey) throws IOException {
    return getJson(url, xMashapeKey, DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES);
  }

  /**
   * @param url
   * @param xMashapeKey
   * @param timeout
   * @param retries
   * @return
   * @throws IOException
   */
  public static JSONObject getJson(final String url, final String xMashapeKey,
                                   final int timeout, final int retries) {
    JSONObject rv = null;
    HttpURLConnection conn = null;
    boolean done = false;
    for (int i = 0; i < retries && !done; i++) {
      try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setInstanceFollowRedirects(true);
      conn.setReadTimeout(timeout);
      conn.setReadTimeout(timeout);
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setRequestProperty("X-Mashape-Key", xMashapeKey);
      conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
      conn.setRequestProperty("User-Agent", "");
      conn.connect();
        switch (conn.getResponseCode()) {
          case HttpURLConnection.HTTP_OK:
            rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(conn))));
            done = true;
            break;
          case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
            break;
          case HttpURLConnection.HTTP_UNAVAILABLE:
            break;
          case (HttpURLConnection.HTTP_CLIENT_TIMEOUT):
            break;
          default:
            done = true;
            break;
        }
        if (rv == null) {
          Thread.sleep(DEFAULT_RETRY_DELAY_MS);
        }
      } catch (IOException | InterruptedException e) {
        rv = null;
      } finally {
        if (conn != null) {
          conn.disconnect();
        }
      }
    }
    return rv;
  }

  //TODO: this method needs some love, like all the persons in the world...
  public static void post(String url, JSONObject json) throws IOException {
    post(url, json, DEFAULT_TIMEOUT_MS);
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
