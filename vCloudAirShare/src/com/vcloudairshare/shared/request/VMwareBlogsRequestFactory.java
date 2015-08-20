package com.vcloudairshare.shared.request;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface VMwareBlogsRequestFactory extends RequestFactory {
	 UserRequest userRequest();
	 VirtualMachineRequest virtualMacineRequest();
}