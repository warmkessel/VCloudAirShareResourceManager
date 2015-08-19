package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;



public interface HomeServiceAsync {
	void power(String airId, VirtualMachineStatus status, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void checkout(String airId, Boolean state, String userName, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
}