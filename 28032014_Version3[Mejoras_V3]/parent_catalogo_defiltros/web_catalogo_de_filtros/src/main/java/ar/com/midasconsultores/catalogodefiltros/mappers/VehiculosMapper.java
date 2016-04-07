package ar.com.midasconsultores.catalogodefiltros.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;
import ar.com.midasconsultores.catalogodefiltros.dto.VehiculoDTO;

/**
 * 
 * @author cgaia
 */
@Component
public class VehiculosMapper {
	
	private static final String EMPTY_TEXT = " - ";
	
	public VehiculoDTO map(Vehiculo vehiculo) {

		VehiculoDTO vehiculoDTO = new VehiculoDTO();

		vehiculoDTO.setId(vehiculo.getId());

		vehiculoDTO.setMarca(getStringValue(vehiculo.getMarca().getNombre()));

		vehiculoDTO.setModelo(getStringValue(vehiculo.getModelo().getNombre()));

		vehiculoDTO.setTipoMotor(getStringValue(vehiculo.getTipoVehiculo().getNombre()));

		vehiculoDTO.setFoto(getStringValue(vehiculo.getImage()));

		return vehiculoDTO;

	}
    
	private String getStringValue(String value){
//		return value.toString();
    	return value == null ? EMPTY_TEXT : value.trim(); 
    }
    
	public List<VehiculoDTO> mapToDTOList(List<Vehiculo> vehiculos) {
		List<VehiculoDTO> vehiculoDTOs = new ArrayList<VehiculoDTO>();
		for (Vehiculo v : vehiculos) {
			vehiculoDTOs.add(this.map(v));
		}
		return vehiculoDTOs;
	}

//	public Vehiculo map(VehiculoDTO vehiculoDTO) {
//
//		Vehiculo vehiculo = new Vehiculo();
//
//		vehiculoDTO.setId(vehiculo.getId());
//
//		vehiculo.setMarca(vehiculoDTO.getMarca());
//
//		vehiculo.setModelo(vehiculoDTO.getModelo());
//
//		vehiculo.setTipoVehiculo(vehiculoDTO.getTipoMotor());
//
//		vehiculo.setImage(vehiculoDTO.getFoto());
//
//		return vehiculo;
//
//	}
//
//	public List<Vehiculo> mapToObjList(List<VehiculoDTO> vehiculoDTOs) {
//		List<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
//		for (VehiculoDTO udto : vehiculoDTOs) {
//			vehiculos.add(this.map(udto));
//		}
//		return vehiculos;
//	}

}
