package pt.it.av.tnav.utils;

import pt.it.av.tnav.utils.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * HTTP/REST helper class.
 *
 * @author <a href="mailto:mariolpantunes@gmail.com">Mário Antunes</a>
 * @version 1.0
 */
public class Http {
  private static final int DEFAULT_TIMEOUT_MS = 10000, DEFAULT_MAX_RETRIES = 5, DEFAULT_RETRY_DELAY_MS = 1000;
  // private static final int MAX_REDIRECT = 10;

  /**
   * Utility class, lets make the constructor private.
   */
  private Http() {
  }

  public static String paramsString(Map<String, String> params) throws UnsupportedEncodingException {
    StringBuilder result = new StringBuilder();

    for (Map.Entry<String, String> entry : params.entrySet()) {
      result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
      result.append("=");
      result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
      result.append("&");
    }

    String resultString = result.toString();
    resultString = resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    return String.format("?%s", resultString);
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

  public static JSONObject getJson(final String baseUrl, final Map<String, String> prop,
      final Map<String, String> params) {
    return getJson(baseUrl, prop, params, DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES);
  }

  public static JSONObject getJson(final String baseUrl, final Map<String, String> prop,
      final Map<String, String> params, final int timeout, final int retries) {
    JSONObject rv = null;
    HttpURLConnection conn = null;
    boolean done = false;
    for (int i = 0; i < retries && !done; i++) {
      try {
        String url = String.format("%s%s", baseUrl, paramsString(params));
        conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setInstanceFollowRedirects(true);
        conn.setReadTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setConnectTimeout(timeout);
        conn.setRequestMethod("GET");
        prop.remove("Content-Type");
        conn.setRequestProperty("Content-Type", "application/json");
        prop.remove("Accept-Encoding");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        
        if (prop.containsKey("User-Agent")) {
          conn.setRequestProperty("User-Agent", prop.get("User-Agent"));
        } else {
          conn.setRequestProperty("User-Agent", "");
        }
        for (Map.Entry<String, String> e : prop.entrySet()) {
          if (!e.getKey().equals("User-Agent")) {
            conn.setRequestProperty(e.getKey(), e.getValue());
          }
        }

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
          case 429:
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
}
