package com.aehtiopicus.cens.controller.cens.validator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class MiembroCensValidator {

	@Autowired
	private UsuarioCensValidator usuarioCensValidator;
	
	public void validate(List<MiembroCens> miembroCensList) throws CensException{
		if(CollectionUtils.isEmpty(miembroCensList)){
			throw new CensException("No hay datos de miembros");
		}
		Map<String,String> errorMap = new HashMap<String,String>();
		
		for(MiembroCens miembro : miembroCensList){
			if(StringUtils.isEmpty(miembro.getApellido())){
				errorMap.put("apellido", "Apellido es requerido");
			}
			if(StringUtils.isEmpty(miembro.getNombre())){
				errorMap.put("nombre", "Nombre es requerido");
			}
			if(StringUtils.isEmpty(miembro.getDni())){
				errorMap.put("dni", "DNI es requerido");
			}
			if(miembro.getFechaNac()==null){
				errorMap.put("fechaNac", "Fecha Nacimiento es requerido");
			}else {
				Calendar c = GregorianCalendar.getInstance();
				c.set(GregorianCalendar.YEAR,c.get(GregorianCalendar.YEAR)-13);
				if(!miembro.getFechaNac().before(c.getTime())){
					errorMap.put("fechaNac", "Fecha Nacimiento debe ser menor que "+new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
				}
				
			}
			try{
				usuarioCensValidator.validate(miembro.getUsuario(),true);
			}catch(CensException e){
				errorMap.putAll(e.getError());
			}
			
		
		if(!errorMap.isEmpty()){
			throw new CensException("Error al crear el miembro del cens",errorMap);
		}
		}
	}
		
}
