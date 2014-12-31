package com.aehtiopicus.cens.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.dto.RolDto;
import com.aehtiopicus.cens.enumeration.RolType;

@Component
public class RolMapper {

	public List<RolDto> convertRolTypeToRolDTO(List<RolType> rolList){
		List<RolDto> rolDtos = new ArrayList<RolDto>();
		for(RolType rt : rolList){
			rolDtos.add(convertRolTypeToRolDTO(rt));
		}
		return rolDtos;
	}
	
	public RolDto convertRolTypeToRolDTO(RolType rt){
		RolDto rd = new RolDto();
		rd.setNombre(rt.name());
		return rd;
	}
}
