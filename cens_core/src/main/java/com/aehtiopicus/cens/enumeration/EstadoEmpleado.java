package com.aehtiopicus.cens.enumeration;

public enum EstadoEmpleado {
	
	ACTUAL("Actual"),
	BAJA("Baja");

	private String nombre;
	
	
	EstadoEmpleado(String nombre){
		this.nombre = nombre;
	}
	
	
	public String getNombre() {
		return nombre;
	}


	public static EstadoEmpleado getEstadoEmpleadoFromString(String text) {
	    if (text != null) {
	      for (EstadoEmpleado b : EstadoEmpleado.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	  }
}
