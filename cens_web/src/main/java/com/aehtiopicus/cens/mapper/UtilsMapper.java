package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.domain.Nacionalidad;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.enumeration.EstadoCivil;

public class UtilsMapper {

	public static List<ComboDto> getCombosDtoEstadoCivil(){
		List<ComboDto> comboData = new ArrayList<ComboDto>();
		  for (EstadoCivil b : EstadoCivil.values()) {
			ComboDto data = new ComboDto();
			data.setValue(b.getNombre());
			comboData.add(data);
		  }
					return comboData;
	}

	public static List<ComboDto> getComboNacionalidad(List<Nacionalidad> nacionalidades) {
		List<ComboDto> data = new ArrayList<ComboDto>();
		if(!CollectionUtils.isEmpty(nacionalidades)){
			for(Nacionalidad nacion : nacionalidades){
				ComboDto nacionalidad = new ComboDto();
				nacionalidad.setId(nacion.getId());
				nacionalidad.setValue(nacion.getNombre());
				data.add(nacionalidad);
			}
		return data;
		}
	return null;
	}

	public static  List<ComboDto>  getComboObraSocial(List<ObraSocial> obrasSociales) {
		List<ComboDto> data = new ArrayList<ComboDto>();
		ComboDto obraSocialDto0 = new ComboDto();
		data.add(obraSocialDto0);
		if(!CollectionUtils.isEmpty(obrasSociales)){
			for(ObraSocial obraSocial : obrasSociales){
				ComboDto obraSocialDto = new ComboDto();
				obraSocialDto.setId(obraSocial.getId());
				obraSocialDto.setValue(obraSocial.getNombre());
				data.add(obraSocialDto);
			}
			
		}
		return data;
	}

	public static Object getComboPrepaga(List<Prepaga> prepagas) {
		List<ComboDto> data = new ArrayList<ComboDto>();
		ComboDto obraSocialDto0 = new ComboDto();
		data.add(obraSocialDto0);
		if(!CollectionUtils.isEmpty(prepagas)){
			for(Prepaga prepaga : prepagas){
				ComboDto comboPrepaga = new ComboDto();
				comboPrepaga.setId(prepaga.getId());
				comboPrepaga.setValue(prepaga.getNombre());
				data.add(comboPrepaga);
			}
			
		}
		return data;
	}

	public static List<ComboDto> getComboBancos(List<Banco> bancos) {
		List<ComboDto> data = new ArrayList<ComboDto>();
		ComboDto obraSocialDto0 = new ComboDto();
		data.add(obraSocialDto0);
		if(!CollectionUtils.isEmpty(bancos)){
			for(Banco prepaga : bancos){
				ComboDto comboPrepaga = new ComboDto();
				comboPrepaga.setId(prepaga.getId());
				comboPrepaga.setValue(prepaga.getNombre());
				data.add(comboPrepaga);
			}
			
		}
		return data;
	}

	public static List<ComboDto> getComboMotivoBaja(List<MotivoBaja> entities) {
		List<ComboDto> data = new ArrayList<ComboDto>();
		ComboDto dto = new ComboDto();
		data.add(dto);
		if(!CollectionUtils.isEmpty(entities)){
			for(MotivoBaja entity : entities){
				dto = new ComboDto();
				dto.setId(entity.getId());
				if(entity.getArticuloLct() != null) {
					dto.setValue(entity.getMotivo() + " (art: " + entity.getArticuloLct() + ")");
				}else {
					dto.setValue(entity.getMotivo());					
				}
				data.add(dto);
			}
		}
		return data;
	}
}
