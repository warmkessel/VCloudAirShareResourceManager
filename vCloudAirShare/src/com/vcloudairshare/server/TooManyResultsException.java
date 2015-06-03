package com.vcloudairshare.server;


/**
 * Wrapper exception that gets thrown when Objectify get() returns too many results
 */
public class TooManyResultsException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4932706280309128777L;

	public TooManyResultsException()
	{
		super();
	}

	public TooManyResultsException(String msg)
	{
		super(msg);
	}

	public TooManyResultsException(String msg, Throwable t)
	{
		super(msg, t);
	}

	public TooManyResultsException(Throwable t)
	{
		super(t);
	}

}