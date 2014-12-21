package com.aehtiopicus.cens.enumeration;

public enum TipoBeneficioEnum {
	
	PESO("$"),
	PORCENTAJE("%");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private TipoBeneficioEnum(String nombre) {
		this.nombre = nombre;
	}



	public static TipoBeneficioEnum getTipoBeneficioEnumFromNombre(String nombre) {
		if (nombre != null) {
			for (TipoBeneficioEnum me : TipoBeneficioEnum.values()) {
				if (nombre.equals(me.getNombre())) {
					return me;
				}
			}
		}
		return null;
	}
}
