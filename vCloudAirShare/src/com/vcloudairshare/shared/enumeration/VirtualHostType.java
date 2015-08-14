package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum VirtualHostType {
	VCLOUDAIR(0,"vCloudAir");

	  public static VirtualHostType fromId(int s) {
	      for (VirtualHostType language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static VirtualHostType fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static VirtualHostType getDefault() {
	    return VCLOUDAIR;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(VirtualHostType explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 

	  
	  VirtualHostType(int id, String value) {
	    this.id = id;
	    this.value = value;
	  }
	  VirtualHostType(String id, String value) {
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
