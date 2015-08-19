package com.vcloudairshare.server.impl;

import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.DataServices;
import com.vcloudairshare.shared.enumeration.DataCenter;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;
import com.vcloudairshare.shared.interfaces.HomeService;

public class HomeServiceImpl extends RemoteServiceServlet implements
		HomeService {
	private static final Logger log = Logger.getLogger(HomeServiceImpl.class
			.getName());
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
				return DataServices.getVirtualMachineService().commission(vm, userName);

			} else {
				return DataServices.getVirtualMachineService().recommission(vm.getId());
			}
		}
		return false;
	}

	public Boolean power(String id, VirtualMachineStatus status) {
		VCloudAirComm comm = VCloudAirComm.getVCloudAirComm(DataCenter.getDefault());
		log.info("status " + status.toString());
		try {
			return comm.power(DataServices.getVirtualMachineService().findByAirId(id), status);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return false;
	}

}
