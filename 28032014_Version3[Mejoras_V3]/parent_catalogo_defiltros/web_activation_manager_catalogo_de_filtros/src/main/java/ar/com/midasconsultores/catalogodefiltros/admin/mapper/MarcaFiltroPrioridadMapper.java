package ar.com.midasconsultores.catalogodefiltros.admin.mapper;

import ar.com.midasconsultores.catalogodefiltros.domain.MarcaFiltroPrioridad;
import ar.com.midasconsultores.catalogodefiltros.admin.dto.MarcaFiltroPrioridadDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 
 * @author cgaia
 */
@Component
public class MarcaFiltroPrioridadMapper {
	
	private static final String EMPTY_TEXT = " - ";
	
	public MarcaFiltroPrioridadDTO map(MarcaFiltroPrioridad marcaFiltroPrioridad) {

		MarcaFiltroPrioridadDTO marcaFiltroPrioridadDTO = new MarcaFiltroPrioridadDTO();

		marcaFiltroPrioridadDTO.setId(marcaFiltroPrioridad.getId());

		marcaFiltroPrioridadDTO.setCodMarca(marcaFiltroPrioridad.getCodMarca());
                
                marcaFiltroPrioridadDTO.setNombreMarca(marcaFiltroPrioridad.getNombreMarca());
                
                marcaFiltroPrioridadDTO.setPrioridad(marcaFiltroPrioridad.getPrioridad());

		return marcaFiltroPrioridadDTO;

	}
        
        public MarcaFiltroPrioridad map(MarcaFiltroPrioridadDTO marcaFiltroPrioridadDTO) {

		MarcaFiltroPrioridad marcaFiltroPrioridad = new MarcaFiltroPrioridad();

		marcaFiltroPrioridad.setId(marcaFiltroPrioridadDTO.getId());

		marcaFiltroPrioridad.setCodMarca(marcaFiltroPrioridadDTO.getCodMarca());
                
                marcaFiltroPrioridad.setNombreMarca(marcaFiltroPrioridadDTO.getNombreMarca());
                
                marcaFiltroPrioridad.setPrioridad(marcaFiltroPrioridadDTO.getPrioridad());

		return marcaFiltroPrioridad;

	}
    
	private String getStringValue(String value){
//		return value.toString();
    	return value == null ? EMPTY_TEXT : value.trim(); 
    }
    
	public List<MarcaFiltroPrioridadDTO> mapToDTOList(List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad) {
		List<MarcaFiltroPrioridadDTO> marcaFiltroPrioridadDTOs = new ArrayList<MarcaFiltroPrioridadDTO>();
		for (MarcaFiltroPrioridad mfp : listaMarcaFiltroPrioridad) {
			marcaFiltroPrioridadDTOs.add(this.map(mfp));
		}
		return marcaFiltroPrioridadDTOs;
	}
        
        public List<MarcaFiltroPrioridad> mapToObjList(List<MarcaFiltroPrioridadDTO> listaMarcaFiltroPrioridadDTO){
		List<MarcaFiltroPrioridad> listaMarcaFiltroPrioridad = new ArrayList<MarcaFiltroPrioridad>();
		for (MarcaFiltroPrioridadDTO mfpDTO : listaMarcaFiltroPrioridadDTO) {
			listaMarcaFiltroPrioridad.add(this.map(mfpDTO));
		}
		return listaMarcaFiltroPrioridad;
	}

}
