package pt.it.av.tnav.utils;

import pt.it.av.tnav.utils.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * HTTP/REST helper class.
 *
 * @author Mário Antunes
 * @version 1.0
 */
public class Http {
  private static final int DEFAULT_CONNECT_TIMEOUT_MS = 10000, DEFAULT_READ_TIMEOUT_MS = 10000, DEFAULT_MAX_RETRIES = 3,
      DEFAULT_RETRY_DELAY_MS = 1000;

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
  private static InputStream inputStream(final InputStream is, final String encoding) throws IOException {
    InputStream rv;
    if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
      rv = new GZIPInputStream(is);
    } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
      rv = new InflaterInputStream(is, new Inflater(true));
    } else {
      rv = is;
    }
    return rv;
  }

  public static JSONObject getJson(final String baseUrl, final Map<String, String> props,
      final Map<String, String> params) throws UnsupportedEncodingException {
    return getJson(baseUrl, props, params, DEFAULT_CONNECT_TIMEOUT_MS, DEFAULT_READ_TIMEOUT_MS, DEFAULT_MAX_RETRIES);
  }

  public static JSONObject getJson(final String baseUrl, final Map<String, String> props,
      final Map<String, String> params, final int cTimeout, final int rTimeout, final int retries)
      throws UnsupportedEncodingException {
    JSONObject rv = null;
    HttpURLConnection conn = null;
    boolean done = false;
    String url = null;

    if (params != null) {
      url = String.format("%s%s", baseUrl, paramsString(params));
    } else {
      url = baseUrl;
    }

    for (int i = 0; i < retries && !done; i++) {
      try {
        conn = (HttpURLConnection) new URL(url).openConnection();

        if (props != null) {
          props.remove("Accept-Encoding");
          props.remove("Content-Type");
          for (Map.Entry<String, String> e : props.entrySet()) {
            conn.setRequestProperty(e.getKey(), e.getValue());
          }
        }

        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(cTimeout);
        conn.setReadTimeout(rTimeout);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");

        conn.connect();

        System.err.println(conn.getResponseCode());

        switch (conn.getResponseCode()) {
          case HttpURLConnection.HTTP_OK:
            InputStream is = conn.getInputStream();
            String encoding = conn.getContentEncoding();
            rv = JSONObject.read(new BufferedReader(new InputStreamReader(inputStream(is, encoding))));
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
        e.printStackTrace();
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
