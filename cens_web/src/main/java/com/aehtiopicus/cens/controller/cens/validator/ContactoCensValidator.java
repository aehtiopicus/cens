package com.aehtiopicus.cens.controller.cens.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.dto.cens.ContactoDto;
import com.aehtiopicus.cens.enumeration.ContactoType;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class ContactoCensValidator {

	public void validate(List<ContactoDto> dtoList)throws Exception{
		if(CollectionUtils.isEmpty(dtoList)){
			throw new CensException("No existe informaci&oacute; de contacot");
		}
		Map<String, String> errorMap = new HashMap<String, String>();
		for(ContactoDto dto : dtoList){
			if(dto.getTipoContacto()==null){
				throw new CensException("No se indic&oactue; el tipo de contacto");
			}
			if(dto.getMiembroCens()==null || dto.getMiembroCens().getId()==null){
				throw new CensException("No se indic&oacute; referencia de contacto");
			}
			if(ContactoType.MAIL.equals(dto.getTipoContacto())){
				if(StringUtils.isEmpty(dto.getDatoContacto()) || ! Utils.emailValido(dto.getDatoContacto())){
					errorMap.put("email", "Email no v&aacute;lido");
				}
			}							
			
		}
		if(!errorMap.isEmpty()){
			throw new CensException("Error de contactos",errorMap);
		}
	}
}
