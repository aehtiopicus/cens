/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

/**
 *
 * @author cgaia
 */
public class FiltroCantidadDTO extends FiltroDTO implements Serializable {

    	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cantidad;
	

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
	
}
