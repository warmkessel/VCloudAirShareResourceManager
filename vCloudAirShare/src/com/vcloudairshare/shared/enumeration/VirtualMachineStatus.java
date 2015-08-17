package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum VirtualMachineStatus {
	UNKNOWN(-1,"Unknown"), NOTREADY(0,"Not Ready"), POWERON(4,"Power On"), POWEROFF(8,"Power Off");

	  public static VirtualMachineStatus fromId(int s) {
	      for (VirtualMachineStatus language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static VirtualMachineStatus fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static VirtualMachineStatus getDefault() {
	    return UNKNOWN;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(VirtualMachineStatus explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;


	  
	  VirtualMachineStatus(int id, String value) {
	    this.id = id;
	    this.value = value;
	  }
	  VirtualMachineStatus(String id, String value) {
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
