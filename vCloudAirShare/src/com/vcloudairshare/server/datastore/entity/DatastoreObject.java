package com.vcloudairshare.server.datastore.entity;

import java.util.Date;

import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class DatastoreObject
{
	private Long id;
	private Integer version = 0;
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
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	/**
	 * Auto-increment version # whenever persisted
	 */
	public void onPersist()
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
