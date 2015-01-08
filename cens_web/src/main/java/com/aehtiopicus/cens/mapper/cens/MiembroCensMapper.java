package com.aehtiopicus.cens.mapper.cens;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;

@Component
public class MiembroCensMapper {

	@Autowired
	private Mapper mapper;
	
	public MiembroCensDto convertMiembroCensToDto(MiembroCens miembroCens){
		return mapper.map(miembroCens, MiembroCensDto.class);
		
	}
	
	public MiembroCens convertMiembroCensDtoToEntity(MiembroCensDto miembroCensDto){
		return mapper.map(miembroCensDto, MiembroCens.class);
		
	}
}
