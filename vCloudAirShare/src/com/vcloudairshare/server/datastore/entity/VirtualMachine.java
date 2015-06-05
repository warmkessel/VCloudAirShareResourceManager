package com.vcloudairshare.server.datastore.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;

@Entity
@Unindex
public class VirtualMachine extends DatastoreObject {

	private String ipaddress = "";
	@Index
	private Long currentUser = 0l;

	private String machinename = "";

	private String pass = "";

	private String machineDesc = "";
	@Index
	private String airId = "";

	
	@Index
	private Integer machinetype = MachineType.getDefault().getId();
	@Index
	private Integer condition = Status.getDefault().getId();
	@Index
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

}
