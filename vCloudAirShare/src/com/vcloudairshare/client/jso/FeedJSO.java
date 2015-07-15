package com.vcloudairshare.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class FeedJSO extends JavaScriptObject {
  protected FeedJSO() {
  }

  public final native String getId() /*-{
	return this.id_str;
}-*/;
public final native String getName() /*-{
return this.name;
}-*/;
public final native String getScreenName() /*-{
return this.screen_name;
}-*/;
public final native String getLocation() /*-{
return this.location;
}-*/;
public final native String getDescription() /*-{
return this.description;
}-*/;
public final native String getUrl() /*-{
return this.url;
}-*/;
//  public final native JsArray<ArticleJSO> getArticle() /*-{
//		return this.feed.article;
//  }-*/;
}
