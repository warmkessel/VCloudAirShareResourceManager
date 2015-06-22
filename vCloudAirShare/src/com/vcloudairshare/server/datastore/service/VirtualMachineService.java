package com.vcloudairshare.server.datastore.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.shared.enumeration.MachineType;
import com.vcloudairshare.shared.enumeration.Status;

public class VirtualMachineService {

	public VirtualMachine findByAirId(String airId) {
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(VirtualMachine.class);
			theCriteria.add(Restrictions.eq("airId", airId));

			return (VirtualMachine) theCriteria.uniqueResult();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
		// Query<VirtualMachine> q =
		// OfyService.ofy().load().type(VirtualMachine.class);
		// q = q.limit(1).filter("airId", airId);
		//
		// List<VirtualMachine> VirtualMachine = q.list();
		// VirtualMachine user = null;
		// if (null != VirtualMachine && VirtualMachine.size() > 0) {
		// user = VirtualMachine.get(0);
		// }
		// return user;
	}

	public static VirtualMachine findById(Long key) {
		// return
		// OfyService.ofy().load().type(VirtualMachine.class).id(key).now();
		// return OfyService.ofy().load().type(Users.class).id(key).now();

		Session session = HibernateFactory.getSessionFactory().openSession();
		try {
			return (VirtualMachine) session.get(VirtualMachine.class, key);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public static List<VirtualMachine> findByAvialable(int start, int limit,
			MachineType machineType, Status status) {
		// Query<VirtualMachine> q =
		// OfyService.ofy().load().type(VirtualMachine.class);
		// q = q.offset(from);
		// if (null != machineType) {
		// q = q.filter("machinetype", machineType.getId());
		// }
		// if (null != status) {
		// q = q.filter("status", status.getId());
		// }
		// if (count > 0) {
		// q = q.limit(count);
		// }
		// return q.list();
		List<VirtualMachine> theList = new ArrayList<VirtualMachine>();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(VirtualMachine.class);
			theCriteria.add(Restrictions.eq("status", status.getId()));

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

	public static VirtualMachine findFirstByAvialable(int from, int count,
			MachineType machineType, Status status) {
		// Query<VirtualMachine> q =
		// OfyService.ofy().load().type(VirtualMachine.class);
		// q = q.limit(1);
		// q = q.offset(from);
		// if (null != machineType) {
		// q = q.filter("machinetype", machineType.getId());
		// }
		// if (null != status) {
		// q = q.filter("status", status.getId());
		// }
		// if (count > 0) {
		// q = q.limit(count);
		// }
		// List<VirtualMachine> users = q.list();
		VirtualMachine user = null;
		// if (null != users && users.size() > 0) {
		// user = users.get(0);
		// }
		return user;
	}

	public VirtualMachine persist(VirtualMachine theVM) {
		theVM.onPersist();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(theVM);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return theVM;
	}

	public List<VirtualMachine> findRange(int start, int limit) {
		List<VirtualMachine> theList = new ArrayList();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(VirtualMachine.class);
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