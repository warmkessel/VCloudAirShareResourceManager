package com.vcloudairshare.server.impl;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.Constants;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.DataServices;
import com.vcloudairshare.shared.enumeration.DataCenter;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.interfaces.HomeService;

public class HomeServiceImpl extends RemoteServiceServlet implements
		HomeService {
	/**
   * 
   */
	private static final long serialVersionUID = -5441875622545162286L;

	// @Override
	// public UserDTO authentication(String username, String password)
	// throws IllegalArgumentException {
	//
	// DataServices.getUsersService().findByCredential(username, password);
	// return new UserDTO();;
	// }
	public Boolean checkout(String id, Boolean state, String userName) {

		VirtualMachine vm = DataServices.getVirtualMachineService()
				.findByAirId(id);
		if (vm != null) {
			if (state) {
				vm.setCondition(Status.INUSE.getId());
				vm.setCurrentUserName(userName);
			} else {
				vm.setCondition(Status.AVAILABLE.getId());
				vm.setCurrentUserName("");

			}
			DataServices.getVirtualMachineService().persist(vm);

			return power(id, state);
		}
		return false;
	}

	public Boolean power(String id, Boolean state) {
		VCloudAirComm comm = VCloudAirComm.getVCloudAirComm();
		try {
			if (state) {
				comm.getDataString(DataCenter.CAL, Constants.API_VAPP, id,
						Constants.POWER_ON);
			} else {
				comm.getDataString(DataCenter.CAL, Constants.API_VAPP, id,
						Constants.POWER_OFF);
			}
			return state;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
