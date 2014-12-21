package com.aehtiopicus.cens.enumeration;

public enum FrecuenciaBeneficioEnum {
	
	MENSUAL("Mensual"),
	ANUAL("Anual"),
	UNICA_VEZ("Única vez");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private FrecuenciaBeneficioEnum(String nombre) {
		this.nombre = nombre;
	}



	public static FrecuenciaBeneficioEnum getFrecuenciaBeneficioEnumFromNombre(String nombre) {
		if (nombre != null) {
			for (FrecuenciaBeneficioEnum me : FrecuenciaBeneficioEnum.values()) {
				if (nombre.equals(me.getNombre())) {
					return me;
				}
			}
		}
		return null;
	}
}
