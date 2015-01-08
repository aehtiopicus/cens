package com.aehtiopicus.cens.enumeration;

import org.codehaus.jackson.annotate.JsonValue;


public enum PerfilTrabajadorCensType {
	ADMINISTRADOR("ROLE_ADMINISTADOR"),
	ASESOR("ROLE_ASESOR"),
	PROFESOR("ROLE_PROFESOR"),
	ALUMNO("ROLE_ALUMNO"),
	PRECEPTOR("ROLE_PRECEPTOR");
	
	protected final String nombre;

	
	public String getNombre() {
		return nombre;
	}	
	
	@Override
	public  String toString(){
		return nombre;
	}

	private PerfilTrabajadorCensType(String nombre) {
		this.nombre = nombre;
	}
}
