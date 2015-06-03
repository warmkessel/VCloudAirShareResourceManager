package com.vcloudairshare.shared.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.vcloudairshare.server.datastore.service.UsersService;
import com.vcloudairshare.shared.proxy.UserDTO;

@Service(UsersService.class)
public interface UserRequest extends RequestContext {
	Request<UserDTO> findById(Long key);
	Request<UserDTO> findByCredential(String username, String password);
}