package com.vcloudairshare.server.datastore.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Unindex;
import com.vcloudairshare.shared.enumeration.Language;
import com.vcloudairshare.shared.enumeration.Status;


@Entity
@Unindex
//@Cache
public class Users extends DatastoreObject {
	private String ipaddress = "";
	@Index
	private Date lastloggedin = new Date();
	@Index
	private String username = "";
	@Index
	private String password = "";
	private int condition = Status.getDefault().getId();
	private String language = Language.getDefault().getId();
	@Index
	private int status = Status.getDefault().getId();
	private String useragent = "";

	public Users() {

	}

	public int getCondition() {
		return condition;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public String getIpAddress() {
		return ipaddress;
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

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipaddress = ipAddress;
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
}
