package com.vcloudairshare.server.impl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.DataServices;
import com.vcloudairshare.shared.enumeration.Status;
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
	public Boolean power(String id, Boolean state){
		VirtualMachine vm = DataServices.getVirtualMachineService().findByAirId(id);
		if(vm != null){
			if(state){
				vm.setCondition(Status.INUSE.getId());
			}
			else{
				vm.setCondition(Status.AVAILABLE.getId());
			}
			DataServices.getVirtualMachineService().persist(vm);

			VCloudAirComm comm = new VCloudAirComm();
			comm.login();
			
		}	
		return true;
	}

}
