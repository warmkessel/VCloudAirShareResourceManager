package com.vcloudairshare.server.datastore.service;

import static com.vcloudairshare.server.datastore.service.OfyService.ofy;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.cmd.Query;
import com.vcloudairshare.server.datastore.entity.Event;
import com.vcloudairshare.shared.enumeration.Status;

public class EventService{
  private void clearCache() {
    MemcacheServiceFactory.getMemcacheService().clearAll();
  }

  public int countAll() {
    return OfyService.ofy().load().type(Event.class).count();
  }

  public List<Event> findActiveRange(int from, int to) throws ServiceException {
    Query<Event> q = OfyService.ofy().load().type(Event.class);
    q = q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.filter("status", Status.APPROVED.getId());
    q =q.order("status");
    q =q.order("lastloggedin");
    return q.list();
  }

  public List<Event> findRange(int from, int to) throws ServiceException {
    Query<Event> q = OfyService.ofy().load().type(Event.class);
    q =q.offset(from);
    if (to > 0) {
      q =q.limit(to);
    }
    q =q.order("status");
    return q.list();
  }

  public List<Event> findAll() {
    return OfyService.ofy().load().type(Event.class).list();
  }

  public List<Event> findAllWithIdxAndCount(int from, int count, String columnOrderings,
      boolean published) {
    Query<Event> q = OfyService.ofy().load().type(Event.class);
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

  public Event findByAddress(String ipaddress, String useragent) {
    Query<Event> q = OfyService.ofy().load().type(Event.class);
    q = q.limit(1).filter("ipaddress", ipaddress).filter("useragent",useragent);

    List<Event> Reservation = q.list();
    Event user = null;
    if (null != Reservation && Reservation.size() > 0) {
      user = Reservation.get(0);
    }
    return user;
  }
  public Event findById(Long key) {
    return OfyService.ofy().load().type(Event.class).id(key).now();
  }

  public Event findByLogInCredentials(String username, String password) {
    Query<Event> q = OfyService.ofy().load().type(Event.class);
    q = q.limit(1).filter("username", username).filter("password", password);

    List<Event> Reservation = q.list();
    Event user = null;
    if (null != Reservation && Reservation.size() > 0) {
      user = Reservation.get(0);
    }
    return user;
  }

  public Event persist(Event reservation) {     
	  ofy().save().entity(reservation).now();
    clearCache();
    return reservation;
  }
}