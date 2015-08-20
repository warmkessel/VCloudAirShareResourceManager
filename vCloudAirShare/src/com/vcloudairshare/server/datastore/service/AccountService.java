package com.vcloudairshare.server.datastore.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.shared.enumeration.Status;

public class AccountService {

//	private static final Logger log = Logger.getLogger(AccountService.class
//			.getName());

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

	public static Account findByUserIdEnsured(Long key) {
		// return OfyService.ofy().load().type(Users.class).id(key).now();
		Session session = HibernateFactory.getSessionFactory().openSession();
//		Transaction tx = null;
		try {
//			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(Account.class);
			theCriteria.add(Restrictions.eq("userId", key));
			Account theReturn = (Account) theCriteria.uniqueResult();
			if (theReturn == null) {
				theReturn = new Account();
				theReturn.setUserId(key);
				theReturn.setStatus(Status.APPROVED.getId());
				//session.saveOrUpdate(theReturn);
				//tx.commit();
				theReturn = persist(theReturn);
//				log.warning("created account" + key);
			}

			return theReturn;
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
	public static Account findByUserId(Long key) {
		// return OfyService.ofy().load().type(Users.class).id(key).now();
		Session session = HibernateFactory.getSessionFactory().openSession();
//		Transaction tx = null;
		try {
//			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(Account.class);
			theCriteria.add(Restrictions.eq("userId", key));
			return  (Account) theCriteria.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public static Account persist(Account users) {
//		log.warning("!!!!!   updated account " + users.getId());

		//users.onPersist();
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