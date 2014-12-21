package com.aehtiopicus.cens.enumeration;

public enum InformeConsolidadoEstadoEnum {
	
	BORRADOR("Borrador"),
//	ENVIADO("Enviado"),
	CONSOLIDADO("Consolidado");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private InformeConsolidadoEstadoEnum(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public  String toString(){
		return nombre;
	}
	
	public static InformeConsolidadoEstadoEnum getInformeConsolidadoEstadoEnumFromString(String text) {
	    if (text != null) {
	      for (InformeConsolidadoEstadoEnum b : InformeConsolidadoEstadoEnum.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	 }
}
