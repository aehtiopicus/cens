package com.aehtiopicus.cens.enumeration;

public enum InformeIntermedioEstadoEnum {
	
	CONSOLIDADO("Consolidado"),
	PENDIENTE("Pendiente"),
	RECIBIDO("Recibido");

	protected final String nombre;	
	
	public String getNombre() {
		return nombre;
	}

	private InformeIntermedioEstadoEnum(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public  String toString(){
		return nombre;
	}
	
	public static InformeIntermedioEstadoEnum getInformeMensualEstadoEnumFromString(String text) {
	    if (text != null) {
	      for (InformeIntermedioEstadoEnum b : InformeIntermedioEstadoEnum.values()) {
	        if (text.equalsIgnoreCase(b.nombre)) {
	          return b;
	        }
	      }
	    }
	    return null;
	 }


}
