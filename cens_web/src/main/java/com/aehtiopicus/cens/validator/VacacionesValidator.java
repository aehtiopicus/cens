package com.aehtiopicus.cens.validator;

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
import com.aehtiopicus.cens.dto.VacacionesDto;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.util.Utils;

@Service
public class VacacionesValidator implements Validator {

	private static final Logger logger = Logger.getLogger(VacacionesValidator.class);
	protected static ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");

	@Override
	public boolean supports(Class<?> clazz) {
		return VacacionesDto.class.equals(clazz);
	}
	@Autowired
	private EmpleadoService empleadoService;
	
	
	
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}



	@Override
	public void validate(Object target, Errors errors) {
		VacacionesDto vacacionesDto = (VacacionesDto) target;

	Empleado empleado = empleadoService.getEmpleadoByEmpleadoId(vacacionesDto.getEmpleadoId());	
	Boolean fechaInicio = false;
	// La fecha de inicio debe tener el formato correcto
	if (vacacionesDto != null && !StringUtils.isEmpty(vacacionesDto.getFechaInicio())) {
		try {
			Utils.sdf.parse(vacacionesDto.getFechaInicio());
		}catch (ParseException e) {
			fechaInicio = true;
			errors.rejectValue( "fechaInicio", "vacacionesDto.fechaInicio", mensajes.getString("ERR005"));
		}
	}
	// La fecha de fin debe tener el formato correcto
	Boolean fechaFin = false;
	if (vacacionesDto != null && !StringUtils.isEmpty(vacacionesDto.getFechaFin())) {
			try {
				Utils.sdf.parse(vacacionesDto.getFechaFin());
			}catch (ParseException e) {
				fechaFin = false;
				errors.rejectValue( "fechaFin", "vacacionesDto.fechaFin", mensajes.getString("ERR005"));
			}
	}
	
	
	// La fecha de inicio de novatium no puede ser mayor a la fecha de fin
	if (fechaInicio == false && fechaFin == false && vacacionesDto != null && !StringUtils.isEmpty(vacacionesDto.getFechaFin()) && !StringUtils.isEmpty(vacacionesDto.getFechaInicio())) {
		try {
			Date fechaFinVacaciones = Utils.sdf.parse(vacacionesDto.getFechaFin());
			Date fechaInicioVacaciones = Utils.sdf.parse(vacacionesDto.getFechaInicio());
			if(fechaInicioVacaciones.after(fechaFinVacaciones)){
				errors.rejectValue( "fechaInicio", "vacacionesDto.fechaInicio", mensajes.getString("ERR023"));
			}
		} catch (ParseException e) {
			errors.rejectValue( "fechaFin", "vacacionesDto.fechaFin",  mensajes.getString("ERR005"));
		}
	}
	
	if(!StringUtils.isEmpty(vacacionesDto.getFechaInicio())){
		try {
			Date fechaInicioVacaciones = Utils.sdf.parse(vacacionesDto.getFechaInicio());
			if(fechaInicioVacaciones.before(empleado.getFechaIngresoNovatium())){
				errors.rejectValue( "fechaInicio", "vacacionesDto.fechaInicio", mensajes.getString("ERR027"));
			}
		} catch (ParseException e) {
			logger.error("no se pudo parsear la fecha de ingreso");
		}
	}
  }
}
