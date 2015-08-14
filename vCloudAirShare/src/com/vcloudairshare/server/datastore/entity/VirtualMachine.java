package com.vcloudairshare.server.datastore.entity;

import com.vcloudairshare.shared.enumeration.VirtualMachineType;
import com.vcloudairshare.shared.enumeration.VirtualHostType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.enumeration.DataCenter;

public class VirtualMachine extends DatastoreObject {

	private String publicIpAddress = "";
	private String privateIpAddress = "";
	private Long currentUser = 0l;
	private String machinename = "";
	private String pass = "";
	private String machineDesc = "";
	private String airId = "";
	private String currentUserName = "";
	private Integer datacenter = DataCenter.getDefault().getId();
	private Integer machinetype = VirtualMachineType.getDefault().getId();
	private Integer hosttype = VirtualHostType.getDefault().getId();
	private Integer condition = Status.getDefault().getId();
	private Integer status = Status.getDefault().getId();

	public VirtualMachine() {

	}

	
	public Integer getDatacenter() {
		return datacenter;
	}


	public void setDatacenter(Integer datacenter) {
		this.datacenter = datacenter;
	}


	public String getPublicIpAddress() {
		return publicIpAddress;
	}


	public void setPublicIpAddress(String publicIpAddress) {
		this.publicIpAddress = publicIpAddress;
	}


	public String getPrivateIpAddress() {
		return privateIpAddress;
	}


	public void setPrivateIpAddress(String privateIpAddress) {
		this.privateIpAddress = privateIpAddress;
	}


	public Long getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Long currentUser) {
		this.currentUser = currentUser;
	}

	public Integer getHosttype() {
		return hosttype;
	}


	public void setHosttype(Integer hosttype) {
		this.hosttype = hosttype;
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
