package com.vcloudairshare.server.locator;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.vcloudairshare.server.datastore.entity.DatastoreObject;
import com.vcloudairshare.server.datastore.service.HibernateFactory;

/**
 * Generic @Locator for objects that extend DatastoreObject
 */
public class ObjectifyLocator extends Locator<DatastoreObject, Long> {
	@Override
	public DatastoreObject create(Class<? extends DatastoreObject> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id) {
		// return ((DatastoreObject)
		// ObjectifyService.ofy().load().value(id).now());
		// This will need to be fixed
		Session session = HibernateFactory.getSessionFactory().openSession();
		try {
			return (DatastoreObject) session.get(clazz, id);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public Class<DatastoreObject> getDomainType() {
		// Never called
		return null;
	}

	@Override
	public Long getId(DatastoreObject domainObject) {
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Object getVersion(DatastoreObject domainObject) {
		return domainObject.getVersion();
	}
}
