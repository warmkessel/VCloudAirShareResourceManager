package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum MachineType {
	VCLOUDAIR(0,"vCloudAir");

	  public static MachineType fromId(int s) {
	      for (MachineType language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static MachineType fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static MachineType getDefault() {
	    return VCLOUDAIR;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(MachineType explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 

	  MachineType(int id, String value) {
	    this.id = id;
	    this.value = value;
	  }
	  MachineType(String id, String value) {
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
