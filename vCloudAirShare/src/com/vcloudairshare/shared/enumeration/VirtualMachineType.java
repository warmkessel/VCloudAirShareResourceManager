package com.vcloudairshare.shared.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum VirtualMachineType {
	CENT63(0,"CentOS 6.3", "vappTemplate-f24a7458-b7b3-48a3-87ab-2965345c93e1", "1CPU, 2GB Memory, 20 GB Storage");

	  public static VirtualMachineType fromId(int s) {
	      for (VirtualMachineType language : values()) {
	        if (s == language.getId())
	          return language;
	      }
	    return getDefault();
	  }
	  public static VirtualMachineType fromId(String s) {
		  return fromId(Integer.parseInt(s));
	  }
	  public static VirtualMachineType getDefault() {
	    return CENT63;
	  }
	  public static List<Integer> idValues() {
	    List<Integer> ids = new ArrayList<Integer>();
	    
	    for(VirtualMachineType explicit : values()){
	      ids.add(explicit.getId());
	    }
	    return ids;
	  }

	  private int id;

	  private String value;
	 
	  private String path;

	  private String desc;

	  
	  VirtualMachineType(int id, String value, String path, String desc) {
	    this.id = id;
	    this.value = value;
	    this.path = path;
	    this.desc = desc;
	  }
	  VirtualMachineType(String id, String value, String path, String desc) {
		  this(Integer.parseInt(id), value, path, desc);
	  }
	  public int getId() {
	    return this.id;
	  }
	  public String getPath() {
		    return this.path;
		  }
	  @Override
	  public String toString() {
	    return this.value;
	  }
	}
