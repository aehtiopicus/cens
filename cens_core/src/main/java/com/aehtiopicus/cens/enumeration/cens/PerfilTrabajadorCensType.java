package com.aehtiopicus.cens.enumeration.cens;



public enum PerfilTrabajadorCensType {
	ADMINISTRADOR("ROLE_ADMINISTRADOR"),
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
	
	
	public static PerfilTrabajadorCensType getPrefilByName(String name){
		for(PerfilTrabajadorCensType rt : values()){
			if(rt.name().equals(name)){
				return rt;
			}
		}
		return null;
	}
}
