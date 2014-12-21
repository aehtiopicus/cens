package com.aehtiopicus.cens.enumeration;

public enum MesEnum {
	
	ENERO(1,"Enero"),
	FEBRERO(2,"Febrero"),
	MARZO(3,"Marzo"),
	ABRIL(4,"Abril"),
	MAYO(5,"Mayo"),
	JUNIO(6,"Junio"),
	JULIO(7,"Julio"),
	AGOSTO(8,"Agosto"),
	SETIEMBRE(9,"Setiembre"),
	OCTUBRE(10,"Octubre"),
	NOVIEMBRE(11,"Noviembre"),
	DICIEMBRE(12,"Diciembre");

	protected final String nombre;	
	protected final Integer numero;
	
	public String getNombre() {
		return nombre;
	}

	public Integer getNumero() {
		return numero;
	}
	
	private MesEnum(Integer numero, String nombre) {
		this.nombre = nombre;
		this.numero = numero;
	}

	public static MesEnum getMesEnumFromNumero(Integer mesNumero) {
		if (mesNumero != null) {
			for (MesEnum me : MesEnum.values()) {
				if (mesNumero.equals(me.getNumero())) {
					return me;
				}
			}
		}
		return null;
	}

	public static MesEnum getMesEnumFromNombre(String mesNombre) {
		if (mesNombre != null) {
			for (MesEnum me : MesEnum.values()) {
				if (mesNombre.equals(me.getNombre())) {
					return me;
				}
			}
		}
		return null;
	}
}
