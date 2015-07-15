package com.vcloudairshare.shared.proxy;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.server.datastore.locator.ObjectifyLocator;

@ProxyFor(value = Account.class, locator = ObjectifyLocator.class)
public interface UserDTO extends EntityProxy {

	public Long getId();
	
	public void setId(Long id);
	
	public String getIpaddress();

	public void setIpaddress(String ipaddress);

	public Date getLastloggedin();

	public void setLastloggedin(Date lastloggedin);

	public Long getUserId();

	public void setUserId(Long userId);

	public String getName();

	public void setName(String name);

	public String getScreen_name();

	public void setScreen_name(String screen_name);

	public String getLocation();

	public void setLocation(String location);

	public String getDescription();

	public void setDescription(String description);

	public String getUrl();

	public void setUrl(String url);

	public int getCondition();

	public void setCondition(int condition);

	public String getLanguage();

	public void setLanguage(String language);

	public int getStatus();

	public void setStatus(int status);

	public String getUseragent();

	public void setUseragent(String useragent);

}
