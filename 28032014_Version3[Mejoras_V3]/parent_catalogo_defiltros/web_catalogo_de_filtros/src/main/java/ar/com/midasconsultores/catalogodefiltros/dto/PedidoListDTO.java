package ar.com.midasconsultores.catalogodefiltros.dto;

import java.io.Serializable;
import java.util.List;

public class PedidoListDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7543640378811833855L;
	
	private List<PedidoDTO> pedidoList;

	public List<PedidoDTO> getPedidoList() {
		return pedidoList;
	}

	public void setPedidoList(List<PedidoDTO> pedidoList) {
		this.pedidoList = pedidoList;
	}
	
	
}
