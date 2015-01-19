package ar.com.midasconsultores.catalogodefiltros.mappers;

import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.PrecioVenta;
import ar.com.midasconsultores.catalogodefiltros.dto.PrecioVentaDTO;

@Component
public class PrecioVentaMapper {

	public PrecioVenta crearDetallePrecioVenta(
			String porcentaje) {
		PrecioVenta pv = new PrecioVenta();		
		pv.setAjuste(true);
		try {
			pv.setPorcentaje(Double.parseDouble(porcentaje));
		} catch (Exception e) {
			pv.setPorcentaje(0);
		}
		return pv;
	}

	public PrecioVentaDTO crearDetallePrecioVentaDTO(PrecioVenta pv) {
		PrecioVentaDTO pvDTO = new PrecioVentaDTO();
		if(pv!=null){
			pvDTO.setPorcentaje(String.valueOf(pv.getPorcentaje()));
		}else{
			pvDTO.setPorcentaje("0");
		}
		return pvDTO;

	}
}
