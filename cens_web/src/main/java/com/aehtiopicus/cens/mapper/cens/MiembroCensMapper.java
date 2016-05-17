package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;


import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.dto.cens.MiembroCensDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class MiembroCensMapper {

	
	
	public MiembroCensDto convertMiembroCensToDto(MiembroCens miembroCens){
		MiembroCensDto mcDto = Utils.getMapper().map(miembroCens, MiembroCensDto.class);
		if(miembroCens.getUsuario().getFileInfo()!=null){

			mcDto.getUsuario().setPassword(null);
			mcDto.getUsuario().setAvatarImg(miembroCens.getUsuario().getFileInfo().getRealFileName());
		}
		return mcDto;
		
	}
	
	public MiembroCens convertMiembroCensDtoToEntity(MiembroCensDto miembroCensDto){
		return Utils.getMapper().map(miembroCensDto, MiembroCens.class);
		
	}

	public List<MiembroCensDto> convertMiembrCensListToDtoList(
			List<MiembroCens> miembroCensList) {
		List<MiembroCensDto> miembroCensDtoList = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(miembroCensList))
		for(MiembroCens mc : miembroCensList){
			mc.getUsuario().setPassword(null);
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
