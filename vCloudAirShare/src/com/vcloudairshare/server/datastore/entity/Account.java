package com.vcloudairshare.server.datastore.entity;

import java.util.Date;

import com.vcloudairshare.shared.enumeration.Language;
import com.vcloudairshare.shared.enumeration.Status;

public class Account extends DatastoreObject {
	private String ipaddress = "";
	private Date lastloggedin = new Date();
	private Long userId = 0l;
	private String name = "";
	private String screen_name = "";
	private String location = "";
	private String description = "";
	private String url = "";
	private int condition = Status.getDefault().getId();
	private String language = Language.getDefault().getId();
	private int status = Status.getDefault().getId();
	private String useragent = "";

	public Account() {
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Date getLastloggedin() {
		return lastloggedin;
	}

	public void setLastloggedin(Date lastloggedin) {
		this.lastloggedin = lastloggedin;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreen_name() {
		return "@" + screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(String status) {
		setStatus(Integer.parseInt(status));
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getUseragent() {
		return useragent;
	}

	public void setUseragent(String useragent) {
		this.useragent = useragent;
	}


}
