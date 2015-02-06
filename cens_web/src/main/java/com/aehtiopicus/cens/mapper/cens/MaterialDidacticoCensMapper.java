package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.dto.cens.DivisionPeriodoTypeDto;
import com.aehtiopicus.cens.dto.cens.MaterialDidacticoDto;
import com.aehtiopicus.cens.enumeration.cens.DivisionPeriodoType;
import com.aehtiopicus.cens.util.Utils;

@Component
public class MaterialDidacticoCensMapper {

	public MaterialDidacticoDto convertMaterialDidacticoToDto(MaterialDidactico materialDidactico){
		MaterialDidacticoDto dto = Utils.getMapper().map(materialDidactico, MaterialDidacticoDto.class);
		dto.setProgramaId(materialDidactico.getPrograma().getId());
		dto.setProfesorId(materialDidactico.getProfesor().getId());
		return dto;
		
	}
	
	public MaterialDidactico convertMaterialDidacticoDtoToEntity(MaterialDidacticoDto mdDto){
		MaterialDidactico md =Utils.getMapper().map(mdDto, MaterialDidactico.class);
		Profesor p = new Profesor();
		Programa pg = new Programa();
		p.setId(mdDto.getProfesorId());
		pg.setId(mdDto.getProgramaId());
		md.setPrograma(pg);
		md.setProfesor(p);
		return md;
		
	}

	public List<MaterialDidacticoDto> convertMaterialDidacticoListToDtoList(
			List<MaterialDidactico> materialList) {
		List<MaterialDidacticoDto> materialDidacticoDtoList = new ArrayList<MaterialDidacticoDto>();
		if(CollectionUtils.isNotEmpty(materialList))
		for(MaterialDidactico md : materialList){
			materialDidacticoDtoList.add(convertMaterialDidacticoToDto(md));
		}
		return materialDidacticoDtoList;
	}
	
	public List<MaterialDidactico> convertMaterialDidacticoDtoListToEntityList(
			List<MaterialDidacticoDto> materialDidacticoDtoList) {
		List<MaterialDidactico> materialDidacticoList = new ArrayList<MaterialDidactico>();
		if(CollectionUtils.isNotEmpty(materialDidacticoDtoList))
		for(MaterialDidacticoDto mdDto : materialDidacticoDtoList){
			materialDidacticoList.add(convertMaterialDidacticoDtoToEntity(mdDto));
		}
		return materialDidacticoList;
	}

	public List<DivisionPeriodoTypeDto> convertToDto(List<DivisionPeriodoType> listDivisionPeriodo) {
		List<DivisionPeriodoTypeDto> returnList = new ArrayList<DivisionPeriodoTypeDto>();
		DivisionPeriodoTypeDto dto = null;
		if(CollectionUtils.isNotEmpty(listDivisionPeriodo)){
			for(DivisionPeriodoType dpt : listDivisionPeriodo){
				dto = new DivisionPeriodoTypeDto();
				dto.setDivisionPeriodoType(dpt);
				returnList.add(dto);
			}
		}
		return returnList;
	}
}
