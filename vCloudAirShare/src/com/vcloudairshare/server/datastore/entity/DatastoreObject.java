package com.vcloudairshare.server.datastore.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.OnSave;
import com.googlecode.objectify.annotation.Index;
public class DatastoreObject
{
	@Id
	@Index
	private Long id;
	private Integer version = 0;
	@Index
  private Date lastUpdated = new Date(0);
	
	public Date getLastUpdated() {
    if(null == lastUpdated){
      lastUpdated = new Date(0);
    }
	  return lastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = lastUpdated;
  }

  public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	/**
	 * Auto-increment version # whenever persisted
	 */
	@OnSave
	void onPersist()
	{
	  setVersion(getVersion() + 1);
	  setLastUpdated(new Date());
	 }

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
