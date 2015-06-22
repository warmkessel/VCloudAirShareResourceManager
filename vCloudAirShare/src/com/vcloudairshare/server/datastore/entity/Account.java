package com.vcloudairshare.server.datastore.entity;

import java.util.Date;

import com.vcloudairshare.shared.enumeration.Language;
import com.vcloudairshare.shared.enumeration.Status;

public class Account  extends DatastoreObject {
	private String ipaddress = "";
	private Date lastloggedin = new Date();
	private String username = "";
	private String password = "";
	private int condition = Status.getDefault().getId();
	private String language = Language.getDefault().getId();
	private int status = Status.getDefault().getId();
	private String useragent = "";

	public Account() {

	}

	public int getCondition() {
		return condition;
	}

	public String getLanguage() {
		return language;
	}

	public Date getLastloggedin() {
		return lastloggedin;
	}

	public Date getLastLoggedIn() {
		return lastloggedin;
	}

	public String getPassword() {
		return password;
	}

	public int getStatus() {
		return status;
	}

	public String getUseragent() {
		return useragent;
	}

	public String getUsername() {
		return username;
	}

	public String getUserName() {
		return username;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLastloggedin(Date lastloggedin) {
		this.lastloggedin = lastloggedin;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastloggedin = lastLoggedIn;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setStatus(String status) {
		setStatus(Integer.parseInt(status));
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	
}
