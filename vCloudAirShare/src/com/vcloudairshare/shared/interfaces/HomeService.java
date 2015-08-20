package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;

@RemoteServiceRelativePath("home")
public interface HomeService extends RemoteService {
//	UserDTO authentication(String username, String pass)
//			throws IllegalArgumentException;
	
	String pass(Long vmId, Long userId)
			throws IllegalArgumentException;
	
	Boolean power(Long vmId, VirtualMachineStatus status, Long userId)
	throws IllegalArgumentException;
	
	Boolean checkout(Long vmId, Boolean state, Long userId)
			throws IllegalArgumentException;
}
