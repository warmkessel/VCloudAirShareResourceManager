package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("home")
public interface HomeService extends RemoteService {
//	UserDTO authentication(String username, String pass)
//			throws IllegalArgumentException;
	
	Boolean power(String airId, Boolean state)
	throws IllegalArgumentException;
	
	Boolean checkout(String airId, Boolean state)
			throws IllegalArgumentException;
}
