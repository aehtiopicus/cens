package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;

@Component
public class MiembroCensMapper {

	private Mapper mapper =  new DozerBeanMapper();
	
	public MiembroCensDto convertMiembroCensToDto(MiembroCens miembroCens){
		return mapper.map(miembroCens, MiembroCensDto.class);
		
	}
	
	public MiembroCens convertMiembroCensDtoToEntity(MiembroCensDto miembroCensDto){
		return mapper.map(miembroCensDto, MiembroCens.class);
		
	}

	public List<MiembroCensDto> convertMiembrCensListToDtoList(
			List<MiembroCens> miembroCensList) {
		List<MiembroCensDto> miembroCensDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(miembroCensList))
		for(MiembroCens mc : miembroCensList){
			miembroCensDtoList.add(convertMiembroCensToDto(mc));
		}
		return miembroCensDtoList;
	}
	
	public List<MiembroCens> convertMiembrCensDtoListToEntityList(
			List<MiembroCensDto> miembroCensDtoList) {
		List<MiembroCens> miembroCensList = new ArrayList<MiembroCens>();
		if(CollectionUtils.isNotEmpty(miembroCensDtoList))
		for(MiembroCensDto mcDto : miembroCensDtoList){
			miembroCensList.add(convertMiembroCensDtoToEntity(mcDto));
		}
		return miembroCensList;
	}
}
