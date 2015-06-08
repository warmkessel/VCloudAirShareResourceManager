package com.vcloudairshare.shared.interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;



public interface HomeServiceAsync {
	void power(String airId, Boolean state, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
	void checkout(String airId, Boolean state, String userName, AsyncCallback<Boolean> callback)
			throws IllegalArgumentException;
}