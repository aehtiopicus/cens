package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;

public class PedidoCantidadDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 562652558544842962L;
	
	private int cantidad;

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
