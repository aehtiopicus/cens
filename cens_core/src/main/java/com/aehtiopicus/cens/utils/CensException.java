package com.aehtiopicus.cens.utils;

import java.util.HashMap;
import java.util.Map;

public class CensException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2822301097116539767L;

	private Map<String, String> error;
	
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
	
	public CensException(String message, Map<String,String> error){
		super(message);
		this.error = error;
	}

	public CensException(String message, String ...errores) {
		super(message);
		if((errores.length%2) ==0){
			error = new HashMap<String, String>();
			for(int i=0;i<errores.length;i=i+2){
				error.put(errores[i], errores[i+1]);
			}
		}
		
		
	}

	public Map<String, String> getError() {
		return error;
	}

		

}
