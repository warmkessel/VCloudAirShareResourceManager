package com.vcloudairshare.server.datastore.locator;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.vcloudairshare.server.datastore.entity.Account;
import com.vcloudairshare.server.datastore.entity.DatastoreObject;
import com.vcloudairshare.server.datastore.service.HibernateFactory;
import com.google.web.bindery.requestfactory.shared.Locator;

/**
 * Generic @Locator for objects that extend DatastoreObject
 */
public class ObjectifyLocator extends Locator<DatastoreObject, Long>
{
	@Override
	public DatastoreObject create(Class<? extends DatastoreObject> clazz)
	{
		try
		{
			return clazz.newInstance();
		} catch (InstantiationException e)
		{
		  throw new RuntimeException(e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public DatastoreObject find(Class<? extends DatastoreObject> clazz, Long id)
	{
		Session session = HibernateFactory.getSessionFactory().openSession();
		try {
			return (DatastoreObject) session.get(clazz, id);

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
		
		
		
//    return ((DatastoreObject) ObjectifyService.ofy().load().value(Key.create(clazz, id)).now());
//		return null;
	}

	@Override
	public Class<DatastoreObject> getDomainType()
	{
		// Never called
		return null;
	}

	@Override
	public Long getId(DatastoreObject domainObject)
	{
		return domainObject.getId();
	}

	@Override
	public Class<Long> getIdType()
	{
		return Long.class;
	}

	@Override
	public Object getVersion(DatastoreObject domainObject)
	{
		return domainObject.getVersion();
	}
}
