package com.vcloudairshare.client;

import java.util.List;

import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.proxy.UserDTO;
import com.vcloudairshare.shared.proxy.VirtualMachineDTO;


public class EntityDepo {
  private UserDTO user = null;
  private List<VirtualMachineDTO> vm = null;
  private VirtualMachineDTO test = null;
  
  public List<VirtualMachineDTO> getVm() {
	return vm;
}
public void setVm(List<VirtualMachineDTO> vm) {
	this.vm = vm;
}
public EntityDepo() {
	
  }
  public boolean isUserLoggedIn(){
	  return(getUser() != null && getUser().getStatus() == Status.APPROVED.getId());
  }
  public UserDTO getUser(){
	  return user;
  }
  public void setUser(UserDTO id){
	  user = id;
  }
public VirtualMachineDTO getTest() {
	return test;
}
public void setTest(VirtualMachineDTO test) {
	this.test = test;
}
  
}
