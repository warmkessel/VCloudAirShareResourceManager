package com.vcloudairshare.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class FeedJSO extends JavaScriptObject {
  protected FeedJSO() {
  }

  public final native JsArray<ArticleJSO> getArticle() /*-{
		return this.feed.article;
  }-*/;
}
