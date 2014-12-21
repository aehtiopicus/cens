package com.aehtiopicus.cens.enumeration;

public enum EstadoClienteEnum {
	Vigente("Vigente"), 
	DadoDeBaja("Dado de baja");
	
	protected String nombre;
	
	private EstadoClienteEnum(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString(){
		return nombre;
	}
	
	public static EstadoClienteEnum getEstadoClienteEnumFromString(String text) {
	    if (text != null) {
	      for (EstadoClienteEnum b : EstadoClienteEnum.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	 }
	
}
