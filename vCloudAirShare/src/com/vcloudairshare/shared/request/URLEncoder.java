package com.vcloudairshare.shared.request;

public class URLEncoder {

  public static final String decode(String theURL){
    //!!!!
    //This is broken and will need some more work
    return theURL.replaceAll("&", "&amp;");
  }
}
