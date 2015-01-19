package ar.com.midasconsultores.catalogodefiltros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.midasconsultores.catalogodefiltros.domain.PrecioVenta;
import ar.com.midasconsultores.catalogodefiltros.repository.PrecioVentaRepository;

@Service
public class PrecioVentaServiceImpl implements PrecioVentaService {

	@Autowired
	private PrecioVentaRepository precioVentaRepository;

	@Override
	public PrecioVenta obtenerPrecioVenta() {
		PrecioVenta pv = null;
		List<PrecioVenta> pvs = precioVentaRepository.findAll();
		if (pvs != null && !pvs.isEmpty()) {
			pv = pvs.get(0);
		}
		return pv;
	}

	@Override
	public void actualizarPrecioVenta(PrecioVenta pv) {
		PrecioVenta precioVenta = obtenerPrecioVenta();
		if (precioVenta == null) {
			precioVenta = pv;
		} else {
			precioVenta.setPorcentaje(pv.getPorcentaje());
		}
		precioVentaRepository.save(precioVenta);

	}
}
