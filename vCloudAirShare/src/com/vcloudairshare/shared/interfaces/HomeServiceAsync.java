package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.vcloudairshare.shared.enumeration.VirtualMachineStatus;



public interface HomeServiceAsync {
	void pass(Long vmId, Long userId, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void power(Long id, VirtualMachineStatus status, Long userId, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void checkout(Long id, Boolean state, Long userId, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
}