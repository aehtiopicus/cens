package com.aehtiopicus.cens.mapper.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Contacto;
import com.aehtiopicus.cens.dto.cens.ContactoDto;
import com.aehtiopicus.cens.util.Utils;

@Component
public class ContactoCensMapper {

	public Contacto convertContactoDtoToEntity(ContactoDto dto){
		return Utils.getMapper().map(dto, Contacto.class);
	}
	
	public ContactoDto convertEntityToDto(Contacto contacto){
		return Utils.getMapper().map(contacto, ContactoDto.class);
	}
	
	public List<Contacto> converContactoDtoToEntity(List<ContactoDto> dtoList){
		List<Contacto> cList = null;
		if(CollectionUtils.isNotEmpty(dtoList)){
			cList = new ArrayList<Contacto>();
			for(ContactoDto cDto : dtoList){
				cList.add(convertContactoDtoToEntity(cDto)); 				
			}
		}
		return cList;		
	}
	
	public List<ContactoDto> convertEntityToDto(List<Contacto> cList){
		List<ContactoDto> dtoList = null;
		if(CollectionUtils.isNotEmpty(cList)){
			dtoList = new ArrayList<ContactoDto>();
			for(Contacto c : cList){
				dtoList.add(convertEntityToDto(c)); 				
			}
		}
		return dtoList;		
	}
}
