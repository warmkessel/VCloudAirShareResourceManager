package com.vcloudairshare.server.datastore.service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.vcloudairshare.server.datastore.entity.Event;
import com.vcloudairshare.server.datastore.entity.Users;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;

public class OfyService {
  static {
	    ObjectifyService.register(VirtualMachine.class);
	    ObjectifyService.register(Event.class);
	    ObjectifyService.register(Users.class);
  }

  public static Objectify ofy() {
    return ObjectifyService.ofy();// prior to v.4.0 use .begin() ,
                                  // since v.4.0 use ObjectifyService.ofy();
  }

  public static ObjectifyFactory factory() {
    return ObjectifyService.factory();
  }
}
