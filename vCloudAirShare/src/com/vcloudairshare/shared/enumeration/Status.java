package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum Status {
	UNKNOWN(0,"Unknown"), APPROVED(1,"Approved"), SUSPECT(2,"Suspect"), INVALID(3,"Invalid"), INACTIVE(4,"Inactive"), UNSUPPORTED(5,"Unsupported"),DUPLICATE(6,"Duplicate"),REMOVED(7,"Removed"),TEST(8,"Test");

	  public static Status fromId(int s) {
	      for (Status language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static Status fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static Status getDefault() {
	    return UNKNOWN;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(Status explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 

	  Status(int id, String value) {
	    this.id = id;
	    this.value = value;
	  }
	  Status(String id, String value) {
		  this(Integer.parseInt(id), value);
	  }
	  public int getId() {
	    return this.id;
	  }
	  @Override
	  public String toString() {
	    return this.value;
	  }
	}
