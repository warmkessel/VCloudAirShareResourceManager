package com.vcloudairshare.client;

public class ExternalJNSI {
  public static final String PANELWIDTH = "322";
  public static final String PANELHEIGHT = "200";
  public static final String[] PANELIST = "0,1,2".split(",");
  public static final String CALLBACKLOCATION = "http://www.vmwareblogs.com/callback.jsp";

  private static ExternalJNSI external = null;

  private ExternalJNSI() {

  }

  public static ExternalJNSI getJSNI() {
    if (null == external) {
      external = new ExternalJNSI();
    }
    return external;
  }

  public final native String getCallbackLoc() /*-{
		return $wnd.callbackLoc;
  }-*/;

  public String getCallback() {

    if (null == getCallbackLoc() || getCallbackLoc().length() == 0) {
      return CALLBACKLOCATION;
    } else {
      return getCallbackLoc();
    }
  }

  public final native String getTestModeLoc() /*-{
		return $wnd.testMode;
  }-*/;

  public boolean getTestMode() {

    if (null == getTestModeLoc() || getTestModeLoc().length() == 0) {
      return Boolean.FALSE;
    } else {
      return Boolean.parseBoolean(getTestModeLoc());
    }
  }

  public final native String getpanelWidthLoc() /*-{
		return $wnd.panelWidth;
  }-*/;

  public String getpanelWidth() {

    if (null == getpanelWidthLoc() || getpanelWidthLoc().length() == 0) {
      return PANELWIDTH;
    } else {
      return getpanelWidthLoc();
    }
  }

  public final native String getpanelHeightLoc() /*-{
		return $wnd.panelHeight;
  }-*/;

  public String getpanelHeight() {

    if (null == getpanelHeightLoc() || getpanelHeightLoc().length() == 0) {
      return PANELHEIGHT;
    } else {
      return getpanelHeightLoc();
    }
  }

  public final native String getpanelListLoc() /*-{
		return $wnd.panelList;
  }-*/;


  public String[] getpanelList() {

    if (null == getpanelListLoc() || getpanelListLoc().length() == 0) {
      return PANELIST;
    } else {
      return getpanelListLoc().split(",");
    }
  }
}