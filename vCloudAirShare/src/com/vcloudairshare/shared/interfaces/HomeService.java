package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;

@RemoteServiceRelativePath("home")
public interface HomeService extends RemoteService {
//	UserDTO authentication(String username, String pass)
//			throws IllegalArgumentException;
	
	Boolean power(String airId, VirtualMachineStatus status)
	throws IllegalArgumentException;
	
	Boolean checkout(String airId, Boolean state, String userName)
			throws IllegalArgumentException;
}
