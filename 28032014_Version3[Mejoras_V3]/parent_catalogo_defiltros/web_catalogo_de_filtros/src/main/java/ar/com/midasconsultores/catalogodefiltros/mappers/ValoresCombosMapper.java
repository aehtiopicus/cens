package ar.com.midasconsultores.catalogodefiltros.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.midasconsultores.catalogodefiltros.domain.ClienteLista;
import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.dto.ValoresComboDTO;

/**
 * 
 * @author cgaia
 */
@Component
public class ValoresCombosMapper {

	public ValoresComboDTO map(String valor) {

		ValoresComboDTO valoresComboDTO = new ValoresComboDTO();

		valoresComboDTO.setValor(valor);

		valoresComboDTO.setLabel(valor);

		return valoresComboDTO;

	}	

	public List<ValoresComboDTO> mapToDTOList(List<String> valores) {
		List<ValoresComboDTO> valoresDTOs = new ArrayList<ValoresComboDTO>();

		ValoresComboDTO valoresComboDto = new ValoresComboDTO();
		
		if(valores != null && valores.size() > 0){
			valoresComboDto.setLabel("Todos");
			valoresComboDto.setValor("");
			valoresDTOs.add(valoresComboDto);
		}
		for (String v : valores) {			
			valoresDTOs.add(this.map(v));
		}
		
		return valoresDTOs;
	}

	public String map(ValoresComboDTO valoresComboDTO) {
		
		String result = valoresComboDTO.getValor();

		return result;
	}

	public List<String> mapToObjList(List<ValoresComboDTO> valoresDTOs) {
		List<String> valores = new ArrayList<String>();
		for (ValoresComboDTO udto : valoresDTOs) {
			valores.add(this.map(udto));
		}
		return valores;
	}
	
	public List<ValoresComboDTO> mapFiltroToDTOList(List<Filtro> valores) {
		List<ValoresComboDTO> valoresDTOs = new ArrayList<ValoresComboDTO>();		
		
		if(valores != null ){
			ValoresComboDTO vcDTO = null;
			for (Filtro v : valores) {	
				vcDTO = new ValoresComboDTO();
				vcDTO.setLabel(v.getCodigoCortoLimpio());
				vcDTO.setValor(v.getId().toString());
				valoresDTOs.add(vcDTO);
			}
		}
		
		return valoresDTOs;
	}
	
	public List<ValoresComboDTO> mapClienteToDTOList(List<ClienteLista> valores) {
		List<ValoresComboDTO> valoresDTOs = new ArrayList<ValoresComboDTO>();		
		
		if(valores != null ){
			ValoresComboDTO vcDTO = null;
			for (ClienteLista v : valores) {	
				vcDTO = new ValoresComboDTO();
				vcDTO.setLabel(v.getCodigoCliente());
				vcDTO.setValor(v.getId().toString());
				valoresDTOs.add(vcDTO);
			}
		}
		
		return valoresDTOs;
	}

}
