package com.vcloudairshare.server.impl;

import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.server.datastore.service.AccountService;
import com.vcloudairshare.server.datastore.service.DataServices;
import com.vcloudairshare.server.datastore.service.VirtualMachineService;
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
	public Boolean checkout(Long vmId, Boolean state, Long userId) {
		Account theAccount = AccountService.findByUserId(userId);
		if(null != theAccount){
		VirtualMachine vm = VirtualMachineService.findById(vmId);
		if (vm != null) {
			if (state) {
				return DataServices.getVirtualMachineService().commission(vm, theAccount);

			} else {
				return DataServices.getVirtualMachineService().recommission(vm.getId());
			}
		}
	}
		return false;
	}

	public Boolean power(Long vmId, VirtualMachineStatus status, Long userId) {
		VCloudAirComm comm = VCloudAirComm.getVCloudAirComm(DataCenter.getDefault());
		log.info("status " + status.toString());
		try {
			return comm.power(VirtualMachineService.findById(vmId), status);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return false;
	}
	public String pass(Long vmId, Long userId){
		log.info("vmId " + vmId);
		log.info("userId " + userId);

		Account theAccount = AccountService.findByUserId(userId);
		
		log.info("Account " + (null == theAccount));

		if(null != theAccount){
			log.info("VirtualMachineService ");

			VirtualMachine theMachine = VirtualMachineService.findByIdAndAccountId(vmId, theAccount.getId());
			log.info("theMachine " + (null == theMachine));

			if(null != theMachine){
				log.info("theMachine.getPass() " + theMachine.getPass());

				return theMachine.getPass();
			}
		}
		return "";
	}
	
}
