package com.vcloudairshare.shared.proxy;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.server.datastore.locator.ObjectifyLocator;

@ProxyFor(value = Account.class, locator = ObjectifyLocator.class)
public interface UserDTO extends EntityProxy {

	public int getCondition();

	public String getIpaddress();

	public String getLanguage();

	public Date getLastloggedin();

	public Date getLastLoggedIn();

	public String getPassword();

	public int getStatus();

	public String getUseragent();

	public String getUsername();

	public String getUserName();

	public void setCondition(int condition);

	public void setIpaddress(String ipaddress);

	public void setLanguage(String language);

	public void setLastloggedin(Date lastloggedin);

	public void setLastLoggedIn(Date lastLoggedIn);

	public void setPassword(String password);

	public void setStatus(int status);

	public void setStatus(String status);

	public void setUseragent(String useragent);

	public void setUsername(String username);

	public void setUserName(String userName);

}
