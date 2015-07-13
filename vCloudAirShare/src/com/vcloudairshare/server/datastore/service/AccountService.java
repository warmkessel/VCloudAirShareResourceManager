package com.vcloudairshare.server.datastore.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vcloudairshare.server.datastore.entity.Account;

public class AccountService {

	public static Account findByTwitterCredential2(String tid) {
		return new AccountService().findByTwitterCredential(tid);
	}

	public static Account findById(Long key) {
		// return OfyService.ofy().load().type(Users.class).id(key).now();

		Session session = HibernateFactory.getSessionFactory().openSession();
		try {
			return (Account) session.get(Account.class, key);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public Account findByTwitterCredential(String tid) {
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(Account.class);
//			theCriteria.add(Restrictions.eq("username", username));
//			theCriteria.add(Restrictions.eq("password", password));

			return (Account) theCriteria.uniqueResult();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;

		// Query<Users> q = OfyService.ofy().load().type(Users.class);
		// q = q.limit(1).filter("username", username).filter("password",
		// password).filter("status", Status.APPROVED.getId());
		//
		// List<Users> users = q.list();
		// Users user = null;
		// if (null != users && users.size() > 0) {
		// user = users.get(0);
		// }
		// return user;
	}

	public Account persist(Account users) {
		users.onPersist();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(users);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<Account> findRange(int start, int limit) {
		List<Account> theList = new ArrayList<Account>();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(Account.class);
			theCriteria.setFetchSize(limit);

			if (0 == start) {
				return theCriteria.list();
			} else {
				theCriteria.list().subList(start, theCriteria.list().size());
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
}