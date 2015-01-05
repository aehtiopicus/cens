package com.aehtiopicus.cens.utils;

public class CensException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2822301097116539767L;

	public CensException() {
		super();

	}

	public CensException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CensException(String message, Throwable cause) {
		super(message, cause);
	}

	public CensException(String message) {
		super(message);
	}

	public CensException(Throwable cause) {
		super(cause);

	}
	
	

}
