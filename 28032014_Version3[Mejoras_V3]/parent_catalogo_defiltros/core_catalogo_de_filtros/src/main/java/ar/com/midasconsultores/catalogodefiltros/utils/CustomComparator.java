package ar.com.midasconsultores.catalogodefiltros.utils;

import java.util.Comparator;

import ar.com.midasconsultores.catalogodefiltros.domain.PedidoDetalle;

public class CustomComparator implements Comparator<PedidoDetalle> {

	@Override
	public int compare(PedidoDetalle o1, PedidoDetalle o2) {
		Long idDetalle1 = o1.getId();
		Long idDetalle2 = o2.getId();		
		return idDetalle2.intValue() - idDetalle1.intValue();
	}

}
