package com.aehtiopicus.cens.enumeration;

public enum InformeConsolidadoTipoEnum {
	
	COMUN(""),
	SAC("SAC");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private InformeConsolidadoTipoEnum(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public  String toString(){
		return nombre;
	}
	
	public static InformeConsolidadoTipoEnum getInformeConsolidadoTipoEnumFromString(String text) {
	    if (text != null) {
	      for (InformeConsolidadoTipoEnum b : InformeConsolidadoTipoEnum.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	 }
}
