package com.aehtiopicus.cens.enumeration;

public enum EstadoCivil {
	
	NO_INFORMADO(""),
	CASADO("Casado"),
	CONCUBINATO("Concubinato"),
	DIVORCIADO("Divorciado"),
	SEPARADO("Separado"),
	SOLTERO("Soltero"),
	UNION_LIBRE("Unión Libre"),
	VIUDO("Viudo");

	private String nombre;
	
	
	EstadoCivil(String nombre){
		this.nombre = nombre;
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public static EstadoCivil getEstadoCivilFromString(String text) {
	    if (text != null) {
	      for (EstadoCivil b : EstadoCivil.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
}
