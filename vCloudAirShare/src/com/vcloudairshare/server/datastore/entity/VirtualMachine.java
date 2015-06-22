package com.vcloudairshare.server.datastore.entity;

import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;


public class VirtualMachine extends DatastoreObject {

	private String ipaddress = "";
	private Long currentUser = 0l;
	private String machinename = "";
	private String pass = "";
	private String machineDesc = "";
	private String airId = "";
	private String currentUserName = "";
	private Integer machinetype = MachineType.getDefault().getId();
	private Integer condition = Status.getDefault().getId();
	private Integer status = Status.getDefault().getId();

	public VirtualMachine() {

	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Long getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Long currentUser) {
		this.currentUser = currentUser;
	}

	public String getMachinename() {
		return machinename;
	}

	public void setMachinename(String machinename) {
		this.machinename = machinename;
	}

	public String getMachineDesc() {
		return machineDesc;
	}

	public void setMachineDesc(String machineDesc) {
		this.machineDesc = machineDesc;
	}

	public Integer getMachinetype() {
		return machinetype;
	}

	public void setMachinetype(Integer machinetype) {
		this.machinetype = machinetype;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		setStatus(Integer.parseInt(status));
	}

	public String getAirId() {
		return airId;
	}

	public void setAirId(String airId) {
		this.airId = airId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getCurrentUserName() {
		return currentUserName;
	}

	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}

}
