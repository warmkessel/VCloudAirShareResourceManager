package com.vcloudairshare.server.datastore.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.vcloudairshare.server.datastore.entity.Event;
import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.shared.enumeration.Status;

public class EventService{

	public List<Event> findRange(int start, int limit){
		  List<Event> theList = new ArrayList();
		  Session session = HibernateFactory.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Criteria theCriteria = session.createCriteria(Event.class);
				theCriteria.setFetchSize(limit);

				if(0 == start){
					return theCriteria.list();
				}
				else{
					theCriteria.list().subList(start,theCriteria.list().size());
				}
				
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
			return theList;
	  }
  public Event findById(Long key) {
//    return OfyService.ofy().load().type(Event.class).id(key).now();
	  return null;
  }

  public Event findByLogInCredentials(String username, String password) {
//    Query<Event> q = OfyService.ofy().load().type(Event.class);
//    q = q.limit(1).filter("username", username).filter("password", password);
//
//    List<Event> Reservation = q.list();
    Event user = null;
//    if (null != Reservation && Reservation.size() > 0) {
//      user = Reservation.get(0);
//    }
    return user;
  }

  public Event persist(Event reservation) {     
//	  ofy().save().entity(reservation).now();
//    clearCache();
    return reservation;
  }
}