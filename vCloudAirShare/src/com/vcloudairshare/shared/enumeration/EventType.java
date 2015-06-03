package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum EventType {
	RESERVED(0,"Reserved"), PROVISIONED(1,"Provisioned"), INUSE(2,"Inuse"), ENDRESERVATION(3,"EndReservation"), DEPROVISIONED(4,"DeProvisioned");

	  public static EventType fromId(int s) {
	      for (EventType language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static EventType fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static EventType getDefault() {
	    return RESERVED;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(EventType explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 

	  EventType(int id, String value) {
	    this.id = id;
	    this.value = value;
	  }
	  EventType(String id, String value) {
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
