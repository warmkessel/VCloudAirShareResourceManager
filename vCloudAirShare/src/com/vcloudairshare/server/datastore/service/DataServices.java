package com.vcloudairshare.server.datastore.service;

public class DataServices {

	public static EventService getEventService() {
		return new EventService();
	}
	public static UsersService getUsersService() {
		return new UsersService();
	}
	public static VirtualMachineService getVirtualMachineService() {
		return new VirtualMachineService();
	}
}
