package com.vcloudairshare.server.datastore.service;

import static com.vcloudairshare.server.datastore.service.OfyService.ofy;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.cmd.Query;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;

public class VirtualMachineService{
  private void clearCache() {
    MemcacheServiceFactory.getMemcacheService().clearAll();
  }

  public int countAll() {
    return OfyService.ofy().load().type(VirtualMachine.class).count();
  }

  public List<VirtualMachine> findActiveRange(int from, int to) throws ServiceException {
    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
    q = q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.filter("status", Status.APPROVED.getId());
    q =q.order("status");
    q =q.order("lastloggedin");
    return q.list();
  }

  public List<VirtualMachine> findRange(int from, int to) throws ServiceException {
    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
    q =q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.order("status");
    return q.list();
  }

  public List<VirtualMachine> findAll() {
    return OfyService.ofy().load().type(VirtualMachine.class).list();
  }

  public List<VirtualMachine> findAllWithIdxAndCount(int from, int count, String columnOrderings,
      boolean published) {
    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
    q = q.offset(from);
    if (published) {
      q = q.filter("published", true);
      q = q.filter("publishDate <=", new Date());
      // q.order("publishDate");
    }
    if (count > 0) {
      q = q.limit(count);
    }
    if (null != columnOrderings && columnOrderings.length() > 0) {
      String[] list = columnOrderings.split(",");
      for (int x = 0; x < list.length; x++) {
        q = q.order(list[x]);
      }
    } else {
      q = q.order("-publishDate");
    }
    return q.list();
  }

  public VirtualMachine findByAddress(String ipaddress, String useragent) {
    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
    q = q.limit(1).filter("ipaddress", ipaddress).filter("useragent",useragent);

    List<VirtualMachine> VirtualMachine = q.list();
    VirtualMachine user = null;
    if (null != VirtualMachine && VirtualMachine.size() > 0) {
      user = VirtualMachine.get(0);
    }
    return user;
  }
  public static VirtualMachine findById(Long key) {
    return OfyService.ofy().load().type(VirtualMachine.class).id(key).now();
  }

  public static List<VirtualMachine> findByAvialable(int from, int count, MachineType machineType,
		  Status status) {
	    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
	    q = q.offset(from);
	    if (null != machineType) {
	      q = q.filter("machinetype", machineType.getId());
	    }
	    if (null != status) {
		      q = q.filter("status", status.getId());
		    }
	    if (count > 0) {
	      q = q.limit(count);
	    }
	    return q.list();
	  }
  
  public static VirtualMachine findFirstByAvialable(int from, int count, MachineType machineType,
		  Status status) {
	    Query<VirtualMachine> q = OfyService.ofy().load().type(VirtualMachine.class);
	    q = q.limit(1);
	    q = q.offset(from);
	    if (null != machineType) {
	      q = q.filter("machinetype", machineType.getId());
	    }
	    if (null != status) {
		      q = q.filter("status", status.getId());
		    }
	    if (count > 0) {
	      q = q.limit(count);
	    }
	    List<VirtualMachine> users = q.list();
	    VirtualMachine user = null;
	    if (null != users && users.size() > 0) {
	      user = users.get(0);
	    }
	    return user;
	  }
  
  public VirtualMachine persist(VirtualMachine VirtualMachine) {   
    ofy().save().entity(VirtualMachine).now();
    clearCache();
    return VirtualMachine;
  }
}