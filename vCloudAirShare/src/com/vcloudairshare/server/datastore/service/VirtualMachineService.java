package com.vcloudairshare.server.datastore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.vcloudairshare.server.communications.VCloudAirComm;
import com.vcloudairshare.server.datastore.entity.VirtualMachine;
import com.vcloudairshare.shared.enumeration.VirtualHostType;
import com.vcloudairshare.shared.enumeration.Status;
import com.vcloudairshare.shared.enumeration.DataCenter;
import com.vcloudairshare.shared.enumeration.VirtualMachineType;
public class VirtualMachineService {
	private static final Logger log = Logger.getLogger(VirtualMachineService.class
			.getName());
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

	@SuppressWarnings("unchecked")
	public static List<VirtualMachine> findByAvialable(int start, int limit,
			VirtualHostType machineType, Status status) {
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
			VirtualHostType machineType, Status status) {
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

	@SuppressWarnings("unchecked")
	public List<VirtualMachine> findRange(int start, int limit) {
		List<VirtualMachine> theList = new ArrayList<VirtualMachine>();
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
	
	public Boolean createMachine() {
		return createMachine(DataCenter.CAL, VirtualMachineType.getDefault());
	}
	public Boolean updateNAT() {
		return updateNAT(DataCenter.CAL);
	}
	public Boolean power(Long id, Boolean state) {
		return power(DataCenter.CAL, findById(id), state);
	}
	
	
	public Boolean delete(DataCenter dc, VirtualMachine vm, Boolean state) {
		VCloudAirComm vcac = VCloudAirComm.getVCloudAirComm(dc);
		vcac.delete(vm);
		return true;
	}
	
	public Boolean power(DataCenter dc, VirtualMachine vm, Boolean state) {
		VCloudAirComm vcac = VCloudAirComm.getVCloudAirComm(dc);
		vcac.power(vm, state);
		return true;
	}
	public Boolean updateNAT(DataCenter dc) {
		VCloudAirComm vcac = VCloudAirComm.getVCloudAirComm(dc);
		vcac.updateNAT();
		return true;
	}
	public Boolean createMachine(DataCenter dc, VirtualMachineType machineType) {
		log.info("Starting CreateMachine");
		VCloudAirComm vcac = VCloudAirComm.getVCloudAirComm(dc);
		log.info("Should be logged in");

		//Create new Macine Record
		VirtualMachine vm = new VirtualMachine();
		
		vm.setDatacenter(dc.getId());
		vm.setHosttype(VirtualHostType.VCLOUDAIR.getId());
		vm.setMachinetype(machineType.getId());
		vm.setCondition(Status.INUSE.getId());
		vm.setStatus(Status.APPROVED.getId());
		
		vm = persist(vm);
		//Fine a new Public Address
		log.info("Find Address");
		
		if(!vcac.findAddress(vm)){
			log.severe("No Address Found");
			return false;
		}
		
		//Create VM on Server
		vcac.createRemoteMachine(machineType, vm, "Id :" + vm.getId(), "http://localhost:8080/admin/virtualmachine.jsp?id="+ vm.getId());
		vm.setCondition(Status.AVAILABLE.getId());
		//poweroff the server
		//Save Record
		persist(vm);
		
		// build Network NAT
		vcac.updateNAT();
		log.info("Finished");

		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<VirtualMachine> findByIP(){

		List<VirtualMachine> theList = new ArrayList<VirtualMachine>();
		Session session = HibernateFactory.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Criteria theCriteria = session.createCriteria(VirtualMachine.class);
			theCriteria.add(Restrictions.isNotNull("publicIpAddress"));
			theCriteria.add(Restrictions.isNotNull("privateIpAddress"));
			theCriteria.add(Restrictions.ne("status", Status.REMOVED.getId()));

			return theCriteria.list();

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