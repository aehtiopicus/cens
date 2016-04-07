package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.dto.cens.PerfilDto;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;

@Component
public class RolCensMapper {

	public List<PerfilDto> convertRolTypeToRolDTO(
			List<PerfilTrabajadorCensType> perfilList) {
		List<PerfilDto> perfilDtos = new ArrayList<PerfilDto>();
		for (PerfilTrabajadorCensType rt : perfilList) {
			perfilDtos.add(convertRolTypeToRolDTO(rt));
		}
		return perfilDtos;
	}

	public PerfilDto convertRolTypeToRolDTO(PerfilTrabajadorCensType rt) {
		PerfilDto rd = new PerfilDto();
		rd.setPerfilType(rt);
		return rd;
	}
}
