package com.vcloudairshare.server.impl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.shared.interfaces.HomeService;

public class HomeServiceImpl extends RemoteServiceServlet implements HomeService {
  /**
   * 
   */
  private static final long serialVersionUID = -5441875622545162286L;

//@Override
//public UserDTO authentication(String username, String password)
//		throws IllegalArgumentException {
//	
//	DataServices.getUsersService().findByCredential(username, password);
//	return new UserDTO();;
//}
	public Boolean power(Long id, Boolean state){
		VCloudAirComm comm = new VCloudAirComm();
		comm.login();
		
		return true;
	}

}
