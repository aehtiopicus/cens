package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

public class PrecioVentaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1566562216956612067L;
	
	
	private String porcentaje;

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

}
