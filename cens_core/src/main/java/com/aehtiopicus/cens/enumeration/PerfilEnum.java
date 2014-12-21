package com.aehtiopicus.cens.enumeration;

public enum PerfilEnum {
	
	ADMINISTRADOR("ROLE_ADMINISTRADOR"),
	RRHH("ROLE_RRHH"),
	GTE_OPERACION("ROLE_GTEOPERACION"),
	JEFE_OPERACION("ROLE_JEFEOPERACION"),
	ADMINISTRACION("ROLE_ADMINISTRACION");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private PerfilEnum(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public  String toString(){
		return nombre;
	}
}
