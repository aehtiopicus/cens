package com.aehtiopicus.cens.enumeration.cens;



public enum PerfilTrabajadorCensType {
	ADMINISTRADOR("ROLE_ADMINISTRADOR",1),
	ASESOR("ROLE_ASESOR",1),
	PROFESOR("ROLE_PROFESOR",2),
	ALUMNO("ROLE_ALUMNO",2),
	PRECEPTOR("ROLE_PRECEPTOR",2),
	USUARIO("ROLE_USUARIO",2); //rol inexistente
	
	protected final String nombre;
	
	protected final int prioridad;

	
	public String getNombre() {
		return nombre;
	}	
	
	@Override
	public  String toString(){
		return nombre;
	}

	private PerfilTrabajadorCensType(String nombre,int prioridad) {
		this.nombre = nombre;
		this.prioridad = prioridad;
	}
	
	
	public static PerfilTrabajadorCensType getPrefilByName(String name){
		for(PerfilTrabajadorCensType rt : values()){
			if(rt.name().equals(name)){
				return rt;
			}
		}
		return null;
	}
	
	public static PerfilTrabajadorCensType getPrefilByNombre(String nombre){
		for(PerfilTrabajadorCensType rt : values()){
			if(rt.getNombre().equals(nombre)){
				return rt;
			}
		}
		return null;
	}

	public int getPrioridad() {
		return prioridad;
	}
	
	
}
