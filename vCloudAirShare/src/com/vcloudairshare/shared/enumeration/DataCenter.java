package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum DataCenter {
	CAL(0,"California", "https://us-california-1-3.vchs.vmware.com");

	  public static DataCenter fromId(int s) {
	      for (DataCenter language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static DataCenter fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static DataCenter getDefault() {
	    return CAL;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(DataCenter explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 
	  private String url;

	  DataCenter(int id, String value, String url) {
	    this.id = id;
	    this.value = value;
	    this.url = url;
	  }
	  DataCenter(String id, String value, String url) {
		  this(Integer.parseInt(id), value, url);
	  }
	  public int getId() {
	    return this.id;
	  }
	  @Override
	  public String toString() {
	    return this.value;
	  }
	  public String getURL() {
	    return this.url;
	  }
	}
