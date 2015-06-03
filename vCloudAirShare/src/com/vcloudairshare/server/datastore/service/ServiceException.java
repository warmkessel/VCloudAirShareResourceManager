package com.vcloudairshare.server.datastore.service;

import java.util.logging.Logger;

public class ServiceException extends Exception {
	/**
	 * 
	 */
    private static final Logger log = Logger.getLogger(ServiceException.class.getName());

	private static final long serialVersionUID = -3624962748059564832L;
	public ServiceException(Exception e){
		super(e.getMessage());
		log.severe(e.getMessage());
	}
	public ServiceException(String s){
		super(s);
		log.severe(s);		
	}
}
