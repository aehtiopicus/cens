package com.aehtiopicus.cens.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.EmpleadoViejoFecha;
import com.aehtiopicus.cens.domain.MotivoBaja;
import com.aehtiopicus.cens.domain.Nacionalidad;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.EmpleadoDto;
import com.aehtiopicus.cens.dto.HistorialNominaDto;
import com.aehtiopicus.cens.dto.SueldoDto;
import com.aehtiopicus.cens.dto.VacacionesDto;
import com.aehtiopicus.cens.enumeration.EstadoCivil;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;
import com.aehtiopicus.cens.util.Utils;

public class EmpleadoMapper {

	
	private static final Logger logger = Logger.getLogger(EmpleadoMapper.class);

	private static final String RELACION_LABORAL = "Cambio de cliente";
	private static final String INGRESO = "Ingreso a Novatium";
	private static final String SUELDO = "Cambio de Sueldo";
	private static final String PUESTO = "Cambio de Puesto";
	private static final String EGRESO = "Egreso de Novatium";
	/**
	 * transforma un empleadoDto en un empleado 
	 * @param dto
	 * @param empleadoDb 
	 * @return
	 */
	public static Empleado getEmpleadoFromEmpleadooDto(EmpleadoDto dto) {
		Empleado empleado = new Empleado();
		logger.info("convirtiendo dto a objeto empleado");
		
		if(dto != null){

			empleado.setCodigoContratacion(dto.getCodigoContratacion());
			
			if(dto.getBancoId() != null){
				Banco banco = new Banco();
				banco.setId(dto.getBancoId());
				empleado.setBanco(banco);
			}
			if(dto.getObraSocialId() != null){
				ObraSocial obraSocial = new ObraSocial();
				obraSocial.setId(dto.getObraSocialId());
				empleado.setObraSocial(obraSocial);
			}
			if(dto.getPrepagaId() != null){
				Prepaga prepaga = new Prepaga();
				prepaga.setId(dto.getPrepagaId());
				empleado.setPrepaga(prepaga);
			}
			if(dto.getMotivoBajaId() != null) {
				MotivoBaja mb = new MotivoBaja();
				mb.setId(dto.getMotivoBajaId());
				empleado.setMotivoBaja(mb);
			}else {
				empleado.setMotivoBaja(null);
			}
			if(dto.getCapitas() != null) {
				empleado.setCapitas(dto.getCapitas());
			}
			if(dto.getNacionalidadId() != null){
				Nacionalidad nacionalidad = new Nacionalidad();
				nacionalidad.setId(dto.getNacionalidadId());
				empleado.setNacionalidad(nacionalidad);
			}
			if(!StringUtils.isEmpty(dto.getApellidos())){
				empleado.setApellidos(dto.getApellidos());
			}
			if(!StringUtils.isEmpty(dto.getCelular())){
				empleado.setCelular(dto.getCelular());
			}
			if(!StringUtils.isEmpty(dto.getContactoParaUrgencias())){
				empleado.setContactoUrgencias(dto.getContactoParaUrgencias());
			}
			if(!StringUtils.isEmpty(dto.getCuil())){
				empleado.setCuil(dto.getCuil());
			}
			if(!StringUtils.isEmpty(dto.getDni())){
				empleado.setDni(dto.getDni());
			}
			if(!StringUtils.isEmpty(dto.getDomicilio())){
				empleado.setDomicilio(dto.getDomicilio());
			}
			if(!StringUtils.isEmpty(dto.getEmailEmpresa())){
				empleado.setMailmpresa(dto.getEmailEmpresa());
			}
			if(!StringUtils.isEmpty(dto.getEmailNovatium())){
				empleado.setMailNovatium(dto.getEmailNovatium());
			}
			if(!StringUtils.isEmpty(dto.getEmailPersonal())){
				empleado.setMailPersonal(dto.getEmailPersonal());
			}
			//Estado Civil
			if(!StringUtils.isEmpty(dto.getEstadoCivil())){
				EstadoCivil ec = EstadoCivil.getEstadoCivilFromString(dto.getEstadoCivil());
				if(ec != null){
					empleado.setEstadoCivil(ec);	
				}
				
			}
			if(StringUtils.isEmpty(dto.getFechaEgresoNovatium())){
				empleado.setEstado(EstadoEmpleado.ACTUAL);
			}else{
				empleado.setEstado(EstadoEmpleado.BAJA);
			}
			
			if(!StringUtils.isEmpty(dto.getFechaEgresoNovatium())){
				Date date;
				try {
					date = Utils.sdf.parse(dto.getFechaEgresoNovatium());
					empleado.setFechaEgresoNovatium(date);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha egreso novatium");
				}
				
			}
			if(!StringUtils.isEmpty(dto.getFechaIngresoNovatium())){
				Date date;
				try {
					date = Utils.sdf.parse(dto.getFechaIngresoNovatium());
					empleado.setFechaIngresoNovatium(date);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha ingreso novatium");
				}
			
				
			}
			if(!StringUtils.isEmpty(dto.getFechaNacimiento())){
				Date date;
				try {
					date = Utils.sdf.parse(dto.getFechaNacimiento());
					empleado.setFechaNacimiento(date);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha de nacimiento novatium");
				}
			
				
			}
			if(!StringUtils.isEmpty(dto.getFechaPreocupacional())){
				Date date;
				try {
					date = Utils.sdf.parse(dto.getFechaPreocupacional());
					empleado.setFechaPreOcupacional(date);
				} catch (ParseException e) {
					logger.error("no se pudo parsera fecha de preocupacional novatium");
				}
				
				
			}
			if(dto.getLegajo() != null){
				empleado.setLegajo(dto.getLegajo());
			}
			if(!StringUtils.isEmpty(dto.getNombres())){
				empleado.setNombres(dto.getNombres());
			}
			if(!StringUtils.isEmpty(dto.getNroCuenta())){
				empleado.setNroCuenta(dto.getNroCuenta());
			}
			if(!StringUtils.isEmpty(dto.getObservaciones())){
				empleado.setObservaciones(dto.getObservaciones());
			}
			if(!StringUtils.isEmpty(dto.getResultadoPreocupacional())){
				empleado.setResultadoPreOcupacional(dto.getResultadoPreocupacional());
			}
			if(!StringUtils.isEmpty(dto.getSucursal())){
				empleado.setSucursal(dto.getSucursal());
			}
			if(!StringUtils.isEmpty(dto.getTelefonoFijo())){
				empleado.setTelFijo(dto.getTelefonoFijo());
			}
			if(!StringUtils.isEmpty(dto.getTelefonoUrgencias())){
				empleado.setTelUrgencias(dto.getTelefonoUrgencias());
			}
			if(dto.getEmpleadoId() !=  null){
				empleado.setId(dto.getEmpleadoId());
			}
			if(dto.getChomba() != null){
				empleado.setChomba(dto.getChomba());
			}
			if(dto.getMochila() != null){
				empleado.setMochila(dto.getMochila());
			}
			if(dto.getConvenio() != null){
				empleado.setConvenio(dto.getConvenio());
			}
			if(!StringUtils.isEmpty(dto.getCbu())){
				empleado.setCbu(dto.getCbu());
			}
			
		}

	
		return empleado;
	}

	/**
	 * Transforma un empleado  en empleado dto
	 * @param dto
	 * @return
	 */
	public static EmpleadoDto getEmpleadoDtoFromEmpleadoo(Empleado empl) {
		EmpleadoDto empleadoDto = new EmpleadoDto();
		logger.info("convirtiendo empleado a objeto empleado dto");
		
		if(empl != null){
			
			empleadoDto.setCodigoContratacion(empl.getCodigoContratacion());
			
			if(empl.getBanco() != null){
				empleadoDto.setBancoId(empl.getBanco().getId());
			}
			if(empl.getObraSocial() != null){
				empleadoDto.setObraSocialId(empl.getObraSocial().getId());
			}
			if(empl.getPrepaga() != null && empl.getPrepaga().getId() != null){
				empleadoDto.setPrepagaId(empl.getPrepaga().getId());
			}
			if(empl.getCapitas() != null) {
				empleadoDto.setCapitas(empl.getCapitas());
			}
			if(empl.getNacionalidad() != null){
				empleadoDto.setNacionalidadId(empl.getNacionalidad().getId());
			}
			if(empl.getMotivoBaja() != null) {
				empleadoDto.setMotivoBajaId(empl.getMotivoBaja().getId());
			}
			if(!StringUtils.isEmpty(empl.getApellidos())){
				empleadoDto.setApellidos(empl.getApellidos());
			}
			if(!StringUtils.isEmpty(empl.getCelular())){
				empleadoDto.setCelular(empl.getCelular());
			}
			if(!StringUtils.isEmpty(empl.getContactoUrgencias())){
				empleadoDto.setContactoParaUrgencias(empl.getContactoUrgencias());
			}
			if(!StringUtils.isEmpty(empl.getCuil())){
				empleadoDto.setCuil(empl.getCuil());
			}
			if(!StringUtils.isEmpty(empl.getDni())){
				empleadoDto.setDni(empl.getDni());
			}
			if(!StringUtils.isEmpty(empl.getDomicilio())){
				empleadoDto.setDomicilio(empl.getDomicilio());
			}
			if(!StringUtils.isEmpty(empl.getMailmpresa())){
				empleadoDto.setEmailEmpresa(empl.getMailmpresa());
			}
			if(!StringUtils.isEmpty(empl.getMailNovatium())){
				empleadoDto.setEmailNovatium(empl.getMailNovatium());
			}
			if(!StringUtils.isEmpty(empl.getMailPersonal())){
				empleadoDto.setEmailPersonal(empl.getMailPersonal());
			}
			//Estado Civil
			if(empl.getEstadoCivil() != null){
						empleadoDto.setEstadoCivil(empl.getEstadoCivil().getNombre());	
			}
			
						
			if(empl.getFechaEgresoNovatium() != null){
					empleadoDto.setFechaEgresoNovatium(Utils.sdf.format(empl.getFechaEgresoNovatium()));
				
			}
			if(empl.getFechaIngresoNovatium() != null){
					empleadoDto.setFechaIngresoNovatium(Utils.sdf.format(empl.getFechaIngresoNovatium()));
					
			}
			if(empl.getFechaNacimiento() != null){
					empleadoDto.setFechaNacimiento(Utils.sdf.format(empl.getFechaNacimiento()));
					
			}
			if(empl.getFechaPreOcupacional() != null){
				empleadoDto.setFechaPreocupacional(Utils.sdf.format(empl.getFechaPreOcupacional()));
				
			}
			if(empl.getLegajo() != null){
				empleadoDto.setLegajo(empl.getLegajo());
			}
			if(!StringUtils.isEmpty(empl.getNombres())){
				empleadoDto.setNombres(empl.getNombres());
			}
			if(!StringUtils.isEmpty(empl.getNroCuenta())){
				empleadoDto.setNroCuenta(empl.getNroCuenta());
			}
			if(!StringUtils.isEmpty(empl.getObservaciones())){
				empleadoDto.setObservaciones(empl.getObservaciones());
			}
			if(!StringUtils.isEmpty(empl.getResultadoPreOcupacional())){
				empleadoDto.setResultadoPreocupacional(empl.getResultadoPreOcupacional());
			}
			if(!StringUtils.isEmpty(empl.getSucursal())){
				empleadoDto.setSucursal(empl.getSucursal());
			}
			if(!StringUtils.isEmpty(empl.getTelFijo())){
				empleadoDto.setTelefonoFijo(empl.getTelFijo());
			}
			if(!StringUtils.isEmpty(empl.getTelUrgencias())){
				empleadoDto.setTelefonoUrgencias(empl.getTelUrgencias());
			}
			if(empl.getId() !=  null){
				empleadoDto.setEmpleadoId(empl.getId());
			}
			if(empl.getChomba() != null){
				empleadoDto.setChomba(empl.getChomba());
			}
			if(empl.getMochila() != null){
				empleadoDto.setMochila(empl.getMochila());
			}
			if(empl.getEstado() != null){
				empleadoDto.setEstado(empl.getEstado().getNombre());
			}
		
			if(empl.getRelacionLaboralVigente() != null &&empl.getRelacionLaboralVigente() != null && empl.getRelacionLaboralVigente().getCliente()!= null){
				empleadoDto.setClienteNombre(empl.getRelacionLaboralVigente().getCliente().getNombre());
			}
			if(!StringUtils.isEmpty(empl.getConvenio())){
				empleadoDto.setConvenio(empl.getConvenio());
			}
			if(!StringUtils.isEmpty(empl.getCbu())){
				empleadoDto.setCbu(empl.getCbu());
			}
			
			// cargar relacion laboral
			RelacionLaboral rl = empl.getRelacionLaboralVigente();
			if(rl != null){
				if(rl.getCliente() != null){
					empleadoDto.setClienteId(rl.getCliente().getId());	
				}
				if(rl.getEmpleado() != null){
					empleadoDto.setEmpleadoId(rl.getEmpleado().getId());	
				}
				if(rl.getFechaInicio() != null){
					empleadoDto.setFechaInicio(Utils.sdf.format(rl.getFechaInicio()));	
				}
				if(rl.getPuesto() != null){
					empleadoDto.setPuestoId(rl.getPuesto().getId());	
				}
				
			}
			
			//Cargar sueldo actual
//			if(empl.getSueldoVigente() != null) {
//				empleadoDto.setBasico(empl.getSueldoVigente().getBasico().toString());
//				empleadoDto.setPresentismo(empl.getSueldoVigente().getPresentismo().toString());
//			}
			
		}
		return empleadoDto;
	}


	public static List<EmpleadoDto> getListEmpleadoDtoFromEmpleados(List<Empleado> empleados){
		List<EmpleadoDto> empleadosDto = new ArrayList<EmpleadoDto>();
		if(!CollectionUtils.isEmpty(empleados)){
			for(Empleado empl : empleados){
				empleadosDto.add(getEmpleadoDtoFromEmpleadoo(empl));	
			}
			
		}
		return empleadosDto;
	}

	public static List<ComboDto> getComboEmpleadoEstado() {
		List<ComboDto> comboData = new ArrayList<ComboDto>();
	    for (EstadoEmpleado b : EstadoEmpleado.values()) {
	    	ComboDto data = new ComboDto();
	    	data.setValue(b.getNombre());
	    	comboData.add(data);
	    }
		
	return comboData;

	}

	public static SueldoDto getSueldoDtoFromSueldo(Sueldo sueldo, Empleado empleado) {
		SueldoDto sueldoDto = new SueldoDto();
		if(sueldo.getBasico() != null){
			sueldoDto.setBasico(String.valueOf(sueldo.getBasico()));
		}
		if(sueldo.getPresentismo() != null){
			sueldoDto.setPresentismo(String.valueOf(sueldo.getPresentismo()));
		}
		if(sueldo.getFechaInicio() != null){
			sueldoDto.setFechaInicio(Utils.sdf.format(sueldo.getFechaInicio()));
		}
		sueldoDto.setSueldoId(sueldo.getId());
		sueldoDto.setEmpleadoId(empleado.getId());
		sueldoDto.setEmpleado(empleado.getApellidos()+", "+empleado.getNombres());
		return sueldoDto;
	}

	public static Sueldo getSueldoFromSueldoDto(SueldoDto sueldoDto) throws ParseException {
		logger.info("obteniendo sueldo a partir de sueldo dto");
		Sueldo sueldo = new Sueldo();
		if(sueldoDto.getBasico() != null){
			sueldo.setBasico(Double.valueOf(sueldoDto.getBasico()));
		}
		if(sueldoDto.getPresentismo() != null){
			sueldo.setPresentismo(Double.valueOf(sueldoDto.getPresentismo()));
		}
		if(sueldoDto.getFechaInicio() != null){
			sueldo.setFechaInicio(Utils.sdf.parse(sueldoDto.getFechaInicio()));
		}
		sueldo.setId(sueldoDto.getSueldoId());
		
		return sueldo;
		
	}

	public static List<VacacionesDto> getListVacacionesDtoFromEmpleados(
			List<Vacaciones> vacaciones) {
		logger.info("creando lista de vacaciones");
		List<VacacionesDto> vacacionesDto = new ArrayList<VacacionesDto>();
		if(!CollectionUtils.isEmpty(vacaciones)){
			for(Vacaciones vacacion : vacaciones){
				vacacionesDto.add(getVacacionDtoFromVacacion(vacacion));
			}
		}
		return vacacionesDto;
	}

	public static VacacionesDto getVacacionDtoFromVacacion(Vacaciones vacacion) {
		VacacionesDto vacacionDto = null;
		
		if(vacacion != null){
		    vacacionDto = new VacacionesDto();
			if(vacacion.getEmpleado() != null && vacacion.getEmpleado().getId() != null){
				vacacionDto.setEmpleadoId(vacacion.getEmpleado().getId());
			}
			if(vacacion.getFechaHasta() != null){
				vacacionDto.setFechaFin(Utils.sdf.format(vacacion.getFechaHasta()));
			}
			if(vacacion.getFechaInicio() != null){
				vacacionDto.setFechaInicio(Utils.sdf.format(vacacion.getFechaInicio()));
			}
			if(!StringUtils.isEmpty(vacacion.getObservaciones())){
				vacacionDto.setObservaciones(vacacion.getObservaciones());
			}
			if(vacacion.getId()!= null){
				vacacionDto.setVacacionesId(vacacion.getId());
			}
		}
		
		
		return vacacionDto;
	}

	public static Vacaciones getVacacionFromVacacionDto(VacacionesDto vacacionDto) throws ParseException {
		Vacaciones vacaciones = null;
		if(vacacionDto != null){
			vacaciones = new Vacaciones();
			if(!StringUtils.isEmpty(vacacionDto.getFechaFin())){
				vacaciones.setFechaHasta(Utils.sdf.parse(vacacionDto.getFechaFin()));
			}
			if(!StringUtils.isEmpty(vacacionDto.getFechaInicio())){
				vacaciones.setFechaInicio(Utils.sdf.parse(vacacionDto.getFechaInicio()));
			}
			if(!StringUtils.isEmpty(vacacionDto.getObservaciones())){
				vacaciones.setObservaciones(vacacionDto.getObservaciones());
			}
			if(vacacionDto.getVacacionesId() != null){
				vacaciones.setId(vacacionDto.getVacacionesId());
			}
		}
		return vacaciones;
	}

	public static List<HistorialNominaDto> getHistorialDtoFromEmpleadoo(Empleado empleado) {
		List<HistorialNominaDto> historiales = new ArrayList<HistorialNominaDto>();
		if (empleado != null) {
			List<RelacionLaboral> relacionesLaborales = empleado.getRelacionLaboral();
			// cargar concepto inicio en novatium
			HistorialNominaDto historialIngreso = new HistorialNominaDto();
			historialIngreso.setFecha(Utils.sdf.format(empleado
					.getFechaIngresoNovatium()));
			historialIngreso.setConcepto(INGRESO);
			historialIngreso.setEmpleadoId(empleado.getId());
			historiales.add(historialIngreso);

			if (!CollectionUtils.isEmpty(relacionesLaborales)) {
				// cargar concepto cambios de clientes
				for (RelacionLaboral relacion : relacionesLaborales) {
					HistorialNominaDto historialRelacion = new HistorialNominaDto();
					historialRelacion.setConcepto(RELACION_LABORAL);
					historialRelacion.setCliente(relacion.getCliente().getNombre());
					historialRelacion.setFecha(Utils.sdf.format(relacion.getFechaInicio()));
					historialRelacion.setEmpleadoId(empleado.getId());
					historiales.add(historialRelacion);

				}

			}
			// cargar concepto sueldo
			List<Sueldo> sueldos = empleado.getSueldo();
			if (!CollectionUtils.isEmpty(sueldos)) {
				for (Sueldo sueldo : sueldos) {
					HistorialNominaDto historialSueldo = new HistorialNominaDto();
					historialSueldo.setFecha(Utils.sdf.format(sueldo.getFechaInicio()));
					historialSueldo.setSueldoBasico(String.valueOf(sueldo.getBasico()));
					historialSueldo.setSueldoPresentismo(String.valueOf(sueldo.getPresentismo()));
					historialSueldo.setConcepto(SUELDO);
					historialSueldo.setEmpleadoId(empleado.getId());
					historiales.add(historialSueldo);
				}
			}
			// cargar cambios de puestos
			List<RelacionPuestoEmpleado> relaciones = empleado.getPuestos();
			if (!CollectionUtils.isEmpty(relaciones)) {
				for (RelacionPuestoEmpleado relacion : relaciones) {
					HistorialNominaDto historialPuesto = new HistorialNominaDto();
					historialPuesto.setFecha(Utils.sdf.format(relacion.getFechaInicio()));
					historialPuesto.setConcepto(PUESTO);
					historialPuesto.setEmpleadoId(empleado.getId());
					historialPuesto.setNombrePuesto(relacion.getPuesto().getNombre());
					
					if (relacion.getRelacionLaboral() != null
							&& relacion.getRelacionLaboral().getCliente() != null
							&& relacion.getRelacionLaboral().getCliente()
									.getNombre() != null) {
						
						historialPuesto.setCliente(relacion.getRelacionLaboral().getCliente().getNombre());
					}

					historiales.add(historialPuesto);
				}
			}
			if (empleado.getFechaEgresoNovatium() != null) {
				HistorialNominaDto historialEgreso = new HistorialNominaDto();
				historialEgreso.setFecha(Utils.sdf.format(empleado.getFechaEgresoNovatium()));
				if(empleado.getMotivoBaja() == null) {
					historialEgreso.setConcepto(EGRESO);
				}else {
					historialEgreso.setConcepto(EGRESO + ", Motivo: " + empleado.getMotivoBaja().getMotivo() + " (Art: " + empleado.getMotivoBaja().getArticuloLct() + ")");					
				}
				historialEgreso.setEmpleadoId(empleado.getId());
				historiales.add(historialEgreso);

			}
			//Agrego incorporaciones anteriores al historial.. si las tiene
			if(empleado.getEmpleadoViejoFechas() != null) {
				for (EmpleadoViejoFecha empleadoAntiguo : empleado.getEmpleadoViejoFechas()) {
					
					if(!empleadoAntiguo.getFechaIngresoNovatium().equals(empleado.getFechaIngresoNovatium()) &&
							!empleadoAntiguo.getFechaEgresoNovatium().equals(empleado.getFechaEgresoNovatium())) 
					{
					
						HistorialNominaDto hEmpleadoAntiguo = new HistorialNominaDto();
						hEmpleadoAntiguo.setFecha(Utils.sdf.format(empleadoAntiguo.getFechaIngresoNovatium()));
						hEmpleadoAntiguo.setConcepto(INGRESO);
						hEmpleadoAntiguo.setEmpleadoId(empleado.getId());
						historiales.add(hEmpleadoAntiguo);
						
						hEmpleadoAntiguo = new HistorialNominaDto();
						hEmpleadoAntiguo.setFecha(Utils.sdf.format(empleadoAntiguo.getFechaEgresoNovatium()));
						if(empleadoAntiguo.getMotivo() == null) {
							hEmpleadoAntiguo.setConcepto(EGRESO);
						}else {
							hEmpleadoAntiguo.setConcepto(EGRESO + ", Motivo: " + empleadoAntiguo.getMotivo().getMotivo() + " (Art: " + empleadoAntiguo.getMotivo().getArticuloLct() + ")");					
						}
						hEmpleadoAntiguo.setEmpleadoId(empleado.getId());
						historiales.add(hEmpleadoAntiguo);
					}
				}
			}

		}
		Collections.sort(historiales);
		return historiales;

	}

	public static Empleado getEmpleadoActualizadoFromEmpleadooDto(
			EmpleadoDto empleadoDto, Empleado empleadoDb) {
		
		EstadoEmpleado estadoEmpBeforeUpdate = empleadoDb.getEstado();
		EstadoEmpleado estadoEmpAfterUpdate;
		
		Empleado empleado = getEmpleadoFromEmpleadooDto(empleadoDto);
		empleado.setRelacionLaboral(empleadoDb.getRelacionLaboral());
		empleado.setPuestos(empleadoDb.getPuestos());
		empleado.setVacaciones(empleadoDb.getVacaciones());
		empleado.setSueldo(empleadoDb.getSueldo());
		empleado.setLegajo(empleadoDb.getLegajo());

		
		estadoEmpAfterUpdate = empleado.getEstado();
		
		//si se cumple esta condicion se esta dando de baja al empleado.. 
		//entonces guardo el regsitro historico de fechas ingreso y egreso de novatium para poder armar el historial luego
		if(estadoEmpBeforeUpdate.equals(EstadoEmpleado.ACTUAL) && estadoEmpAfterUpdate.equals(EstadoEmpleado.BAJA)) {
			empleado.setEmpleadoViejoFechas(empleadoDb.getEmpleadoViejoFechas());
			
			EmpleadoViejoFecha historico = new EmpleadoViejoFecha();
			historico.setLegajo(empleado.getLegajo());
			historico.setFechaIngresoNovatium(empleado.getFechaIngresoNovatium());
			historico.setFechaEgresoNovatium(empleado.getFechaEgresoNovatium());
			MotivoBaja mb;
			if(empleadoDto.getMotivoBajaId() != null) {
				mb = new MotivoBaja();
				mb.setId(empleadoDto.getMotivoBajaId());
				historico.setMotivo(mb);
			}
			
			empleado.addEmpleadoViejoFecha(historico);
		}
		
		return empleado;
		
	}

	public static Sueldo getSueldoFromEmpleadoDto(EmpleadoDto dto) throws ParseException {
		logger.info("obteniendo sueldo a partir de empleado dto");
		Sueldo entity = new Sueldo();
		if(dto.getBasico() != null){
			entity.setBasico(Double.valueOf(dto.getBasico()));
		}
		if(dto.getPresentismo() != null){
			entity.setPresentismo(Double.valueOf(dto.getPresentismo()));
		}
		if(dto.getFechaInicio() != null){
			entity.setFechaInicio(Utils.sdf.parse(dto.getFechaIngresoNovatium()));
		}
		
		return entity;
	}
}
