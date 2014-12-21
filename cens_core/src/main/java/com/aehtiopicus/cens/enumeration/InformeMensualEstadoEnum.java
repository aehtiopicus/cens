package com.aehtiopicus.cens.enumeration;

public enum InformeMensualEstadoEnum {
	
	BORRADOR("Borrador"),
	ENVIADO("Enviado"),
	CONSOLIDADO("Consolidado");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private InformeMensualEstadoEnum(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public  String toString(){
		return nombre;
	}
	
	public static InformeMensualEstadoEnum getInformeMensualEstadoEnumFromString(String text) {
	    if (text != null) {
	      for (InformeMensualEstadoEnum b : InformeMensualEstadoEnum.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	 }


}
