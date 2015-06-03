package com.vcloudairshare.server.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;



public class Authentication {
//  private static final Logger log = Logger.getLogger(Authentication.class.getName());

  private static Authentication query = null;

  public static Authentication getQueryBlogs() {
    if (null == query) {
      query = new Authentication();
    }
    return query;
  }

  private Authentication() {
  }

 

  public static InputStream requestURL(String theURL) throws IOException {
    return requestURL(new URL(theURL));
  }

  private static InputStream requestURL(URL url) throws IOException {

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    // conn.setReadTimeout(5000);
    conn.setConnectTimeout(60000); // 60 Seconds
    conn.setReadTimeout(60000);
    conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
    // conn.addRequestProperty("User-Agent", "Mozilla");
    conn.addRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
    conn.addRequestProperty("Referer", "google.com");
    conn.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=utf-8");

    // System.out.println("Request URL ... " + url);

    boolean redirect = false;

    // normally, 3xx is redirect
    int status = conn.getResponseCode();
    if (status != HttpURLConnection.HTTP_OK) {
      if (status == HttpURLConnection.HTTP_MOVED_TEMP
          || status == HttpURLConnection.HTTP_MOVED_PERM
          || status == HttpURLConnection.HTTP_SEE_OTHER)
        redirect = true;
    }

    // System.out.println("Response Code ... " + status);

    if (redirect) {

      // get redirect url from "location" header field
      String newUrl = conn.getHeaderField("Location");

      // get the cookie if need, for login
      String cookies = conn.getHeaderField("Set-Cookie");

      // open the new connnection again
      conn = (HttpURLConnection) new URL(newUrl).openConnection();
      conn.setRequestProperty("Cookie", cookies);
      conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
      conn.addRequestProperty("User-Agent", "Mozilla");
      conn.addRequestProperty("Referer", "google.com");
      conn.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=utf-8");

      // System.out.println("Redirect to URL : " + newUrl);

    }
    return conn.getInputStream();

  }
}
