package com.vcloudairshare.shared.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.vcloudairshare.server.datastore.service.AccountService;
import com.vcloudairshare.shared.proxy.UserDTO;

@Service(AccountService.class)
public interface UserRequest extends RequestContext {
	Request<UserDTO> findById(Long key);
	Request<UserDTO> findByTwitterCredential2(String tid);
}