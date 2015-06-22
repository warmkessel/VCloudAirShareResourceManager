package com.vcloudairshare.server.datastore.service;

public class DataServices {

	public static EventService getEventService() {
		return new EventService();
	}
	public static AccountService getAccountService() {
		return new AccountService();
	}
	public static VirtualMachineService getVirtualMachineService() {
		return new VirtualMachineService();
	}
}
