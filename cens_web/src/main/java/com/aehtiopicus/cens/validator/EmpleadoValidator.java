package com.aehtiopicus.cens.validator;

import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.dto.EmpleadoDto;
import com.aehtiopicus.cens.service.BancoService;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.util.Utils;

@Service
public class EmpleadoValidator implements Validator {

	private static final String GALICIA = "Galicia";
	@Autowired
	private EmpleadoService empleadoService;
	@Autowired
	private BancoService bancoService;

	private static final Logger logger = Logger.getLogger(EmpleadoValidator.class);

	protected ResourceBundle mensajes = ResourceBundle.getBundle("errores_es");
	
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}


	public void setBancoService(BancoService bancoService) {
		this.bancoService = bancoService;
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return EmpleadoDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EmpleadoDto empleadoDto = (EmpleadoDto) target;
		Pattern pattern = Pattern.compile("|^[0-9]{12}");
		
//		Long cantUsersWithLegajo = empleadoService.getCountEmpleadoByLegajo(empleadoDto.getLegajo(), empleadoDto.getEmpleadoId());
//		if(cantUsersWithLegajo > 0){
//			errors.rejectValue( "legajo", "usuario.duplicado",mensajes.getString("ERR000"));
//		}
		// No debe existir en el sistema otro usuario con el mismo dni
		Long cant = empleadoService.getCountEmpleadoByDNI(empleadoDto.getDni(), empleadoDto.getEmpleadoId());
		if (cant > 0) {
				if(!empleadoService.isOldEmpleado(empleadoDto.getDni())) {					
					errors.rejectValue( "dni", "usuario.duplicado",mensajes.getString("ERR000"));
				}else {
					empleadoDto.setOldEmpleado(true);
				}
		}
		
		
		//El cuil debe tener el formato correcto y debe ser un numero
		if(!StringUtils.isEmpty(empleadoDto.getCuil())){
			try{
				
				Long.valueOf(empleadoDto.getCuil());
				if(!Utils.esCUILValido(empleadoDto.getCuil())){
					errors.rejectValue( "cuil", "empleado.cuil", mensajes.getString("ERR001"));
				}
			}catch(NumberFormatException e){
				errors.rejectValue( "cuil", "empleado.cuil",  mensajes.getString("ERR002"));
			}		
		}
	
	 // El dni debe ser numerico	
	  try{
		  if(!StringUtils.isEmpty(empleadoDto.getDni())){
			 Long.valueOf(empleadoDto.getDni());
			 
			  if(!StringUtils.isEmpty(empleadoDto.getDni()) && empleadoDto.getDni().length() != 8){
				  errors.rejectValue( "dni", "empleado.dni", mensajes.getString("ERR003"));
			  }
		}
		}catch(NumberFormatException e){
			errors.rejectValue( "dni", "empleado.dni",mensajes.getString("ERR004"));
		}
	
	// La fecha de nacimiento debe tener el formato correcto
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getFechaNacimiento())) {
		try {
			Utils.sdf.parse(empleadoDto.getFechaNacimiento());
		}catch (ParseException e) {
					errors.rejectValue( "fechaNacimiento", "empleado.fechaNacimiento", mensajes.getString("ERR005"));
		}
	}
	Boolean fechaIngresoError = false;
	// La fecha de ingreso a novatium debe tener el formato correcto
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getFechaIngresoNovatium())) {
		try {
			Utils.sdf.parse(empleadoDto.getFechaIngresoNovatium());
			
		}catch (ParseException e) {
			fechaIngresoError = false;
			errors.rejectValue( "fechaIngresoNovatium", "empleado.fechaIngresoNovatium",mensajes.getString("ERR005"));
		}
	}
	Boolean fechaEgresoError = false;
	// La fecha de egreso a novatium debe tener el formato correcto
		if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getFechaEgresoNovatium())) {
			try {
				Utils.sdf.parse(empleadoDto.getFechaEgresoNovatium());
				if(empleadoDto.isExistRelacion()){
					errors.rejectValue( "fechaEgresoNovatium", "empleado.fechaEgresoNovatium",mensajes.getString("ERR030"));
				}
			}catch (ParseException e) {
				fechaEgresoError = true;
				errors.rejectValue( "fechaEgresoNovatium", "empleado.fechaEgresoNovatium", mensajes.getString("ERR005"));
			}
		}
	// La fecha de preocupacional debe tener el formato correcto
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getFechaPreocupacional())) {
		try {
			Utils.sdf.parse(empleadoDto.getFechaPreocupacional());
		}catch (ParseException e) {
			errors.rejectValue( "fechaPreocupacional", "empleado.fechaPreocupacional",mensajes.getString("ERR005"));
		}
	}
	//Numeros de telefono deben ser numericos
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getTelefonoFijo())) {
			try {
				Long.parseLong((empleadoDto.getTelefonoFijo()));
			}catch (NumberFormatException e) {
				errors.rejectValue( "telefonoFijo", "empleado.telefonoFijo",mensajes.getString("ERR006"));
			}
	}
	//Numeros de telefono deben ser numericos
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getCelular())) {
		try {
			Long.parseLong((empleadoDto.getCelular()));
		}catch (NumberFormatException e) {
			errors.rejectValue( "celular", "empleado.celular", mensajes.getString("ERR006"));
		}
	}
	
	//Numeros de telefono deben ser numericos
	if (empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getTelefonoUrgencias())) {
		try {
				Long.parseLong((empleadoDto.getTelefonoUrgencias()));
			}catch (NumberFormatException e) {
				errors.rejectValue( "telefonoUrgencias", "empleado.telefonoUrgencias", mensajes.getString("ERR006"));
			}
		}
	//Nacionalidad es obligatorio
	if (empleadoDto.getNacionalidadId() == 0) {
		errors.rejectValue( "nacionalidad", "usuario.nacionalidad", mensajes.getString("ERR007"));
	}
	
	// La fecha de egreso de novatium no puede ser menor a la fecha de ingreso a novatium
	if (fechaIngresoError == false && fechaEgresoError == false && empleadoDto != null && !StringUtils.isEmpty(empleadoDto.getFechaEgresoNovatium()) && !StringUtils.isEmpty(empleadoDto.getFechaIngresoNovatium())) {
		try {
			Date fechaEgreso = Utils.sdf.parse(empleadoDto.getFechaEgresoNovatium());
			Date fechaIngreso = Utils.sdf.parse(empleadoDto.getFechaIngresoNovatium());
			if(fechaEgreso.before(fechaIngreso)){
				errors.rejectValue( "fechaEgresoNovatium", "empleado.fechaEgresoNovatium", mensajes.getString("ERR008"));
			}
		} catch (ParseException e) {
			errors.rejectValue( "fechaEgresoNovatium", "empleado.fechaEgresoNovatium", mensajes.getString("ERR005"));
		}
	}	
	// si el banco es galicia el convenio es requerido
	
	Banco banco = bancoService.findById(empleadoDto.getBancoId());
//	if(banco == null){
//		errors.rejectValue( "bancoId", "empleado.banco", mensajes.getString("ERR025"));
//	}
	if(banco != null && !StringUtils.isEmpty(banco.getNombre()) && banco.getNombre().toLowerCase().equals(GALICIA.toLowerCase())){
		if(StringUtils.isEmpty(empleadoDto.getConvenio())){
			errors.rejectValue( "convenio", "empleado.convenio", mensajes.getString("ERR025"));
		}
		if(StringUtils.isEmpty(empleadoDto.getNroCuenta())){
			errors.rejectValue( "nroCuenta", "empleado.nrocuenta", mensajes.getString("ERR025"));
		}
		 Matcher mat = pattern.matcher(empleadoDto.getNroCuenta());
	     if (!mat.matches()) {
	    		errors.rejectValue( "nroCuenta", "empleado.nrocuenta", mensajes.getString("ERR039"));
	     }
	}else if(banco != null && !StringUtils.isEmpty(banco.getNombre()) && !banco.getNombre().toLowerCase().equals(GALICIA.toLowerCase())){
		if(StringUtils.isEmpty(empleadoDto.getCbu())){
			errors.rejectValue( "cbu", "empleado.cbu", mensajes.getString("ERR025"));
		}
	}
	
	if(empleadoDto.getClienteId() != null) {
		if(empleadoDto.getPuestoId() == null){
			errors.rejectValue( "puestoId", "empleado.puesto", mensajes.getString("ERR025"));
		}
		if(StringUtils.isEmpty(empleadoDto.getFechaInicio())){
			errors.rejectValue( "fechaInicio", "empleado.fechaInicio", mensajes.getString("ERR025"));
		}else{
			try{
				Date fechaIngreso = Utils.sdf.parse(empleadoDto.getFechaIngresoNovatium());
				Date fechaInicioRelacion = Utils.sdf.parse(empleadoDto.getFechaInicio());
				if(fechaInicioRelacion.before(fechaIngreso)){
					errors.rejectValue( "fechaInicio", "empleado.fechaInicio", mensajes.getString("ERR027"));
				}
			}catch(Exception e){
				logger.error("error al parsear fecha");
			}
		}
		
	
	}
 }
}
