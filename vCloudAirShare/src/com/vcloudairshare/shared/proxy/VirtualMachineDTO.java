package com.vcloudairshare.shared.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.locator.ObjectifyLocator;

@ProxyFor(value = VirtualMachine.class, locator = ObjectifyLocator.class)
public interface VirtualMachineDTO extends EntityProxy {

	public Long getId();
		
	public void setId(Long id);

	public String getIpaddress();

	public void setIpaddress(String ipaddress);

	public Long getCurrentUser();

	public void setCurrentUser(Long currentUser);

	public String getMachinename();

	public void setMachinename(String machinename);
	
	public String getMachineDesc();
	
	public void setMachineDesc(String machineDesc);

	public Integer getMachinetype();

	public void setMachinetype(Integer machinetype);

	public Integer getCondition();

	public void setCondition(Integer condition);

	public Integer getStatus();

	public void setStatus(Integer status);

	public void setStatus(String status);
}
