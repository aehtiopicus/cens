package com.aehtiopicus.cens.validator;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.dto.SueldoDto;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.util.Utils;

@Service
public class SueldoValidator implements Validator {

	@Autowired
	private EmpleadoService empleadoService;
	private ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	private static final Logger logger = Logger.getLogger(SueldoValidator.class);
	
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SueldoDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		logger.info("validando sueldo");
		SueldoDto sueldoDto = (SueldoDto) target;
		Sueldo sueldoActual = empleadoService.getSueldoActualByEmpleadoId(sueldoDto.getEmpleadoId());
		Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(sueldoDto.getEmpleadoId());
		if(!StringUtils.isEmpty(sueldoDto.getBasico())){
			try{
				Double.valueOf(sueldoDto.getBasico());	
			}catch(Exception e){
				errors.rejectValue( "basico", "sueldo.basico", mensajes.getString("ERR019"));
			}
			
		}
		if(!StringUtils.isEmpty(sueldoDto.getPresentismo())){
			try{
				Double.valueOf(sueldoDto.getPresentismo());	
			}catch(Exception e){
				errors.rejectValue( "presentismo", "sueldo.presentismo",  mensajes.getString("ERR019"));
			}
			
		}
		Boolean errorFechaInicio = false;
		if(!StringUtils.isEmpty(sueldoDto.getFechaInicio())){
			try{
				Date fechaInicio = Utils.sdf.parse(sueldoDto.getFechaInicio());	
				if(fechaInicio.before(empleado.getFechaIngresoNovatium())){
					errors.rejectValue( "fechaInicio", "sueldo.fecha",mensajes.getString("ERR027"));
				}
			}catch(Exception e){
				errorFechaInicio = true;
				errors.rejectValue( "fechaInicio", "sueldo.fecha", mensajes.getString("ERR005"));
			}
			
		}
		if(errorFechaInicio == false && sueldoActual != null && !StringUtils.isEmpty(sueldoDto.getFechaInicio())){
			try {
				Date fechaInicio = 	Utils.sdf.parse(sueldoDto.getFechaInicio());
				if(fechaInicio.before(sueldoActual.getFechaInicio())){
						errors.rejectValue( "fechaInicio", "sueldo.fecha",MessageFormat.format(mensajes.getString("ERR020"),Utils.sdf.format(sueldoActual.getFechaInicio())));
					}
				}
			   catch (ParseException e) {
				errorFechaInicio = true;
				errors.rejectValue( "fechaInicio", "sueldo.fecha", mensajes.getString("ERR005"));
			}	
			
		}
		

		
 }
}
