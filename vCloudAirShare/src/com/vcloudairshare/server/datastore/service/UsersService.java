package com.vcloudairshare.server.datastore.service;

import static com.vcloudairshare.server.datastore.service.OfyService.ofy;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.cmd.Query;
import com.vcloudairshare.server.datastore.entity.Users;
import com.vcloudairshare.shared.enumeration.Status;

public class UsersService{
  private void clearCache() {
    MemcacheServiceFactory.getMemcacheService().clearAll();
  }

  public int countAll() {
    return OfyService.ofy().load().type(Users.class).count();
  }

  public List<Users> findActiveRange(int from, int to) throws ServiceException {
    Query<Users> q = OfyService.ofy().load().type(Users.class);
    q = q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.filter("status", Status.APPROVED.getId());
    q =q.order("status");
    q =q.order("lastloggedin");
    return q.list();
  }

  public List<Users> findRange(int from, int to) throws ServiceException {

	  Query<Users> q = OfyService.ofy().load().type(Users.class);
    q =q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.order("status");
    q =q.order("lastloggedin");
    return q.list();
  }

  public List<Users> findAll() {
    return OfyService.ofy().load().type(Users.class).list();
  }

  public List<Users> findAllWithIdxAndCount(int from, int count, String columnOrderings,
      boolean published) {
    Query<Users> q = OfyService.ofy().load().type(Users.class);
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

  public Users findByAddress(String ipaddress, String useragent) {
    Query<Users> q = OfyService.ofy().load().type(Users.class);
    q = q.limit(1).filter("ipaddress", ipaddress).filter("useragent",useragent);

    List<Users> users = q.list();
    Users user = null;
    if (null != users && users.size() > 0) {
      user = users.get(0);
    }
    return user;
  }
  public static Users findByCredential(String username, String password) {
	   return new UsersService().findByLogInCredentials(username, password);
	  }
  public static Users findById(Long key) {
    return OfyService.ofy().load().type(Users.class).id(key).now();
  }

  public Users findByLogInCredentials(String username, String password) {
    Query<Users> q = OfyService.ofy().load().type(Users.class);
    q = q.limit(1).filter("username", username).filter("password", password).filter("status", Status.APPROVED.getId());

    List<Users> users = q.list();
    Users user = null;
    if (null != users && users.size() > 0) {
      user = users.get(0);
    }
    return user;
  }

  public Users persist(Users users) {   
    ofy().save().entity(users).now();
    clearCache();
    return users;
  }
}