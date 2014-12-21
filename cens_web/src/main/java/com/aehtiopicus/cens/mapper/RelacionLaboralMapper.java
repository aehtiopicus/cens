package com.aehtiopicus.cens.mapper;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.Puesto;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.dto.ComboDto;
import com.aehtiopicus.cens.dto.EmpleadoDto;
import com.aehtiopicus.cens.dto.IncrementoSalarialEmpleadoDto;
import com.aehtiopicus.cens.dto.RelacionLaboralDto;
import com.aehtiopicus.cens.dto.RelacionPuestoEmpleadoDto;
import com.aehtiopicus.cens.util.Utils;


public class RelacionLaboralMapper {

	public static List<RelacionLaboralDto> getRelacionesLaboralesDtoFromRelacionesLaborales(List<RelacionLaboral> entities){
		List<RelacionLaboralDto> dtos = new ArrayList<RelacionLaboralDto>(); 
		
		if(entities != null){
			for (RelacionLaboral entity : entities) {
				dtos.add(getRelacionLaboralDtoFromRelacionLaboral(entity));
			}
		}
		
		return dtos;
	}
	
	public static RelacionLaboralDto getRelacionLaboralDtoFromRelacionLaboral(RelacionLaboral entity){
		RelacionLaboralDto dto = new RelacionLaboralDto();
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		
		if(entity != null){
			if(entity.getCliente() != null){
				dto.setClienteId(entity.getCliente().getId());
				dto.setRazonSocialCliente(entity.getCliente().getNombre());
			}
			if(entity.getEmpleado() != null){
				dto.setEmpleadoId(entity.getEmpleado().getId());
				dto.setLegajoEmpleado(entity.getEmpleado().getLegajo());
				dto.setNombreEmpleado(entity.getEmpleado().getApellidos() + ", " + entity.getEmpleado().getNombres());
			}
			
			if(entity.getPuesto() != null){
				dto.setPuestoId(entity.getPuesto().getId());
				dto.setPuestoNombre(entity.getPuesto().getNombre());
			}
			
			if(entity.getFechaInicio() != null){
				dto.setFechaInicio(sdf.format(entity.getFechaInicio()));
			}
			if(entity.getFechaFin() != null){
				dto.setFechaFin(sdf.format(entity.getFechaFin()));
			}
			
			if(entity.getId() != null){
				dto.setRelacionLaboralId(entity.getId());
			}
		}
		
		return dto;
	}
	
	public static List<ComboDto> getComboDtosFromPuestos(List<Puesto> entities){
		List<ComboDto> dtos = new ArrayList<ComboDto>();
		ComboDto dto = null;
		if(entities != null){
			for (Puesto entity : entities) {
				dto = new ComboDto();
				dto.setId(entity.getId());
				dto.setValue(entity.getNombre());
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	public static RelacionLaboralDto createRelacionLaboralDtoForEmpleado(Empleado empleado) {
		RelacionLaboralDto dto = new RelacionLaboralDto();
		if(empleado != null){
			dto.setEmpleadoId(empleado.getId());
			dto.setNombreEmpleado(empleado.getApellidos() + ", " + empleado.getNombres());
			dto.setLegajoEmpleado(empleado.getLegajo());
		}
		return dto;
	}

	public static RelacionLaboral getRelacionLaboralFromRelacionLaboralDto(RelacionLaboralDto dto) {
		RelacionLaboral entity = new RelacionLaboral();
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		
		if(dto != null){
			if(dto.getClienteId() != null){
				Cliente c = new Cliente();
				c.setId(dto.getClienteId());
				entity.setCliente(c);
			}
			if(dto.getEmpleadoId() != null){
				Empleado e = new Empleado();
				e.setId(dto.getEmpleadoId());
				e.setMailmpresa(dto.getMailEmpresa());
				entity.setEmpleado(e);
			}
			if(dto.getPuestoId() != null){
				Puesto p = new Puesto();
				p.setId(dto.getPuestoId());
				entity.setPuesto(p);
			}
			if(dto.getFechaInicio() != null){
				try {
					entity.setFechaInicio(sdf.parse(dto.getFechaInicio()));
				} catch (ParseException e) {
					entity.setFechaInicio(null);
				}
			}
			if(dto.getFechaFin() != null){
				try {
					entity.setFechaFin(sdf.parse(dto.getFechaFin()));
				} catch (ParseException e) {
					entity.setFechaFin(null);
				}
			}
		}
		
		return entity;
	}

	public static RelacionLaboral updateRelacionLaboralFromRelacionLaboralDto(RelacionLaboral relacionLaboral,	RelacionLaboralDto relacionLaboralDto) {
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		if(relacionLaboralDto != null && relacionLaboral != null){
			if(relacionLaboralDto.getFechaFin() != null){
				try {
					relacionLaboral.setFechaFin(sdf.parse(relacionLaboralDto.getFechaFin()));
				} catch (ParseException e) {
					relacionLaboral.setFechaFin(null);
				}
			}
		}
		
		return relacionLaboral;
	}

	public static RelacionPuestoEmpleado updateRelacionPuestoEmpleadoFromRelacionPuestoEmpleadoDto(
			RelacionPuestoEmpleadoDto dto) {
		RelacionPuestoEmpleado entity = new RelacionPuestoEmpleado();
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		
		if(dto != null){
			if(dto.getRelacionLaboralId() != null){
				RelacionLaboral c = new RelacionLaboral();
				c.setId(dto.getRelacionLaboralId());
				entity.setRelacionLaboral(c);
			}
			
			if(dto.getPuestoId() != null){
				Puesto p = new Puesto();
				p.setId(dto.getPuestoId());
				entity.setPuesto(p);
			}
			
			if(dto.getFechaInicio() != null){
				try {
					entity.setFechaInicio(sdf.parse(dto.getFechaInicio()));
				} catch (ParseException e) {
					entity.setFechaInicio(null);
				}
			}
		}
		
		return entity;
	}

	public static RelacionPuestoEmpleadoDto getRelacionPuestoEmpleadoDtoFromRelacionPuestoEmpleado(
			RelacionPuestoEmpleado entity) {
		RelacionPuestoEmpleadoDto dto = new RelacionPuestoEmpleadoDto();
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		
		if(entity != null){
			if(entity.getRelacionLaboral() != null){
				dto.setRelacionLaboralId(entity.getRelacionLaboral().getId());
				dto.setRazonSocialCliente(entity.getRelacionLaboral().getCliente().getNombre());
			}
			if(entity.getRelacionLaboral() != null){
				dto.setClienteId(entity.getRelacionLaboral().getCliente().getId());
				dto.setRazonSocialCliente(entity.getRelacionLaboral().getCliente().getNombre());
			}
			if(entity.getRelacionLaboral() != null){
				dto.setEmpleadoId(entity.getRelacionLaboral().getEmpleado().getId());
				dto.setNombreEmpleado(entity.getRelacionLaboral().getEmpleado().getNombres()+" "+entity.getRelacionLaboral().getEmpleado().getApellidos());
			}
			
			if(entity.getPuesto() != null){
				dto.setPuestoId(entity.getPuesto().getId());
				dto.setPuestoNombre(entity.getPuesto().getNombre());
			}
			
			if(entity.getFechaInicio() != null){
				dto.setFechaInicio(sdf.format(entity.getFechaInicio()));
			}
				
		}
		
		return dto;
	}

	public static RelacionLaboral getRelacionLaboralFromEmpleadoDto(
			EmpleadoDto dto) {
		RelacionLaboral entity = new RelacionLaboral();
		SimpleDateFormat sdf = new SimpleDateFormat(Utils.DD_MM_YYYY);
		
		if(dto != null){
			if(dto.getClienteId() != null){
				Cliente c = new Cliente();
				c.setId(dto.getClienteId());
				entity.setCliente(c);
			}
			if(dto.getEmpleadoId() != null){
				Empleado e = new Empleado();
				e.setId(dto.getEmpleadoId());
				entity.setEmpleado(e);
			}
			if(dto.getPuestoId() != null){
				Puesto p = new Puesto();
				p.setId(dto.getPuestoId());
				entity.setPuesto(p);
			}
			if(dto.getFechaInicio() != null){
				try {
					entity.setFechaInicio(sdf.parse(dto.getFechaInicio()));
				} catch (ParseException e) {
					entity.setFechaInicio(null);
				}
			}
			
		}
		
		return entity;
	}

	public static List<IncrementoSalarialEmpleadoDto> getIncrementosDtoFromRelacionesLaborales(List<RelacionLaboral> relaciones, Date fechaInicio) {
		List<IncrementoSalarialEmpleadoDto> dtoList = new ArrayList<IncrementoSalarialEmpleadoDto>();
		DecimalFormat df = new DecimalFormat("#0.00");
		
		if(CollectionUtils.isNotEmpty(relaciones)) {
			IncrementoSalarialEmpleadoDto dto;
			Sueldo sueldoVigente;
			
			for (RelacionLaboral entity : relaciones) {
				dto = new IncrementoSalarialEmpleadoDto();
				sueldoVigente = entity.getEmpleado().getSueldoVigente();
				
				dto.setEmpleado(entity.getEmpleado().getApellidos() + ", " + entity.getEmpleado().getNombres());
				dto.setLegajo(entity.getEmpleado().getLegajo());
				dto.setEmpleadoId(entity.getEmpleado().getId());
				dto.setFechaSueldo(Utils.sdf.format(sueldoVigente.getFechaInicio()));
				dto.setSueldoBasico(df.format(sueldoVigente.getBasico()));
				dto.setSueldoPresentismo(df.format(sueldoVigente.getPresentismo()));
				if(fechaInicio.equals(sueldoVigente.getFechaInicio())) {
					dto.setEstado("ACTUALIZADO");
				}else {
					dto.setEstado("NO ACTUALIZADO");
				}
				dtoList.add(dto);
			}
		}
		
		return dtoList;
	}
}
