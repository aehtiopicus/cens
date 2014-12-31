package com.aehtiopicus.cens.enumeration;

public enum RolType {
	ALUMNO,
	PROFESOR,
	PRECEPTOR,
	ASESOR;

	public static RolType getRolByName(String name){
		for(RolType rt : values()){
			if(rt.name().equals(name)){
				return rt;
			}
		}
		return null;
	}
}
