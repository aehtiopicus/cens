package com.aehtiopicus.cens.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;
import com.aehtiopicus.cens.repository.EmpleadoRepository;
import com.aehtiopicus.cens.repository.RelacionLaboralRepository;
import com.aehtiopicus.cens.repository.SueldoRepository;
import com.aehtiopicus.cens.repository.VacacionesRepository;
import com.aehtiopicus.cens.specification.EmpleadoSpecification;
import com.aehtiopicus.cens.specification.SueldoSpecification;
import com.aehtiopicus.cens.specification.VacacionesSpecification;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService{

	 private static final Logger logger = Logger.getLogger(EmpleadoServiceImpl.class);
	 
	 public final static String DD_MM_YYYY = "dd/MM/yyyy";
		
	 public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	 
	 private static int offset = 0;
	 
	 @Autowired
	 protected EmpleadoRepository empleadoRepository;
	 
	 @Autowired
	 protected SueldoRepository sueldoRepository;
	 
	 @Autowired
	 protected VacacionesRepository vacacionesRepository;
	 
	 @Autowired
	 protected RelacionLaboralRepository relacionLaboralRepository;
	 
	 

	public void setRelacionLaboralRepository(
			RelacionLaboralRepository relacionLaboralRepository) {
		this.relacionLaboralRepository = relacionLaboralRepository;
	}

	public void setVacacionesRepository(VacacionesRepository vacacionesRepository) {
		this.vacacionesRepository = vacacionesRepository;
	}

	public void setSueldoRepository(SueldoRepository sueldoRepository) {
		this.sueldoRepository = sueldoRepository;
	}

	public EmpleadoRepository getEmpleadoRepository() {
		return empleadoRepository;
	}

	public void setEmpleadoRepository(EmpleadoRepository empleadoRepository) {
		this.empleadoRepository = empleadoRepository;
	}

	@Override
	public Empleado saveEmpleado(Empleado empleado) {
		if(empleado.getLegajo() == null && empleado.getId() ==null) {
			Integer legajo = empleadoRepository.getLastLegajo() + 1 + offset++;
			empleado.setLegajo(legajo);
			logger.info("save empleado -> LEGAJO:" + empleado.getLegajo());
			
			try {
				empleado = empleadoRepository.saveAndFlush(empleado);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}finally {
				offset--;
			}
		}else {
			empleado = empleadoRepository.saveAndFlush(empleado);			
		}
		return empleado;
	}

	@Override
	public Empleado reincorporarEmpleado(Empleado empleado) {
		Integer legajo = empleadoRepository.getLastLegajo() + 1 + offset++;
		empleado.setLegajo(legajo);
		logger.info("save empleado -> LEGAJO:" + empleado.getLegajo());
		
		try {
			empleado = empleadoRepository.saveAndFlush(empleado);
		}catch(Exception e) {
			logger.error(e.getMessage());
		}finally {
			offset--;
		}
		return empleado;
	}
	
	@Override
	public List<Empleado> getEmpleados() {
		logger.info("get empleados");
		return empleadoRepository.findAll();
	}

	@Override
	public List<Empleado> getEmpleadosPaginado(Integer page, Integer rows,
			String cuil, String apellido, String cliente, String estado){
		logger.info("get empleados paginados y filtrados");
		 Page<Empleado> requestedPage = null;
		 EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification() ;
		 Specifications<Empleado> spec = null ;
		 Boolean where = true;
		 if(page > 0){
			 page = page - 1;
		 }
		 spec = getSpecification(cuil,cliente, apellido, estado, empleadoSpecification,
				spec, where);

		 requestedPage = empleadoRepository.findAll(spec,constructPageSpecification(page,rows));	 
	     
         return requestedPage.getContent();
	}

	private Specifications<Empleado> getSpecification(String cuil,String cliente,
			String apellido, String estado,
			EmpleadoSpecification empleadoSpecification,
			Specifications<Empleado> spec, Boolean where) {
		logger.info("getSpecification ");
		if(!StringUtils.isEmpty(cuil)){
			 spec = Specifications.where(empleadoSpecification.cuilEquals(cuil));
			 where = false;
			
		 }
		if(StringUtils.isNotEmpty(cliente)){
			try{
				Long clienteId = Long.valueOf(cliente);
				if(where == true){
					spec = Specifications.where(EmpleadoSpecification.clienteIdEquals(clienteId));
					where = false;
				}else{
					spec = spec.and(EmpleadoSpecification.clienteIdEqualsAll(clienteId));
				}
				
				 
			}catch(Exception e){
				logger.error("cliente id tiene formato incorrecto");
			}
			
			
		}
		 if(!StringUtils.isEmpty(apellido)){
			 if(where == true){
				 spec = Specifications.where(empleadoSpecification.apellidoEquals(apellido));
				 where = false;
			 }else{
				 spec = spec.and(empleadoSpecification.apellidoEquals(apellido));	 
			 }
			 	 
		 }
		 if(!StringUtils.isEmpty(estado)){
			 EstadoEmpleado es= EstadoEmpleado.getEstadoEmpleadoFromString(estado);
			 if(where == true){
				 spec = Specifications.where(empleadoSpecification.estadoEquals(es));
				 where = false;
			 }else{
				 spec = spec.and(empleadoSpecification.estadoEquals(es)); 
			 }
			 
			
		 }
		return spec;
	}

	private Pageable constructPageSpecification(Integer page, Integer rows) {
		logger.info("constructPageSpecification ");
		 Pageable pageSpecification = new PageRequest(page, rows, sortByNameAsc());
	     return pageSpecification;
	}

	private Sort sortByNameAsc() {
		logger.info("sortByNameAsc ");
		 return new Sort(Sort.Direction.ASC, "apellidos", "nombres");
	}

	
	@Override
	public Empleado getEmpleadoByEmpleadoId(Long empleadoId) {
		return empleadoRepository.findById(empleadoId);
		
	}

	@Override
	public int getTotalEmpleados(String cuil, String apellido, String cliente,
			String estado) {
	     logger.info("getTotalEmpleados ");
		 EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification() ;
		 Specifications<Empleado> spec = null ;
		 Boolean where = true;
		
		 spec = getSpecification(cuil,cliente, apellido, estado, empleadoSpecification,
					spec, where);
		return  (int) Math.ceil(empleadoRepository.count(spec));
	}

	@Override
	public long getCountEmpleadoByDNI(String dni, Long id) {
		logger.info("getCountEmpleadoByDNI ");
	    if(StringUtils.isNotEmpty(dni)){
	    	EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification() ;
		    Specifications<Empleado> spec =  Specifications.where(empleadoSpecification.dniEquals(dni));
			if(id != null && id>0){
				spec = spec.and(empleadoSpecification.idNoEquals(id));
			}
			return empleadoRepository.count(spec);
	    }
		return 0;
		
	}

	@Override
	public List<Empleado> getAllEmpleados(String cuil, String apellido,
			String cliente, String estado) {
		logger.info("getAllEmpleados ");
		 EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification() ;
		 Specifications<Empleado> spec = null ;
		 Boolean where = true;
		
		 spec = getSpecification(cuil,cliente, apellido, estado, empleadoSpecification,
					spec, where);
		return empleadoRepository.findAll(spec, new Sort("legajo"));
	}

	@Override
	public Sueldo getSueldoActualByEmpleadoId(Long empleadoId) {
		logger.info("getSueldoActualByEmpleadoId ");
		Specifications<Sueldo> spec = Specifications.where(SueldoSpecification.emptyFinalDateEquals());
		spec = spec.and(SueldoSpecification.empleadoEquals(empleadoId));
		List<Sueldo> sueldos = sueldoRepository.findAll(spec);
		Sueldo sueldo = null;
		if(!CollectionUtils.isEmpty(sueldos)){
			sueldo = sueldos.get(0);
		}
		return sueldo;
	}

	@Override
	public Sueldo saveSueldo(Sueldo sueldo, Long empleadoId) {
		logger.info("saveSueldo ");
		if(sueldo.getId() != null){
			Sueldo sueldoBD = sueldoRepository.findById(sueldo.getId());
			if(sdf.format(sueldoBD.getFechaInicio()).equals(sdf.format(sueldo.getFechaInicio()))){
				// si las fechas de inicio son iguales se actualiza el objeto
				sueldoBD.setBasico(sueldo.getBasico());
				sueldoBD.setPresentismo(sueldo.getPresentismo());
				sueldo = sueldoRepository.saveAndFlush(sueldoBD);
			}else{
				//si no se toma como ultimo sueldo el cargado recientemente cerrando el sueldo anterior y guardando el nuevo
				sueldoBD.setFechaFin(new Date());
				sueldoRepository.saveAndFlush(sueldoBD);
				sueldo = crearNuevoSueldo(sueldo, empleadoId);
			}
		}else{
			sueldo = crearNuevoSueldo(sueldo, empleadoId);
		}
		return sueldo;
	}

	private Sueldo crearNuevoSueldo(Sueldo sueldo, Long empleadoId) {
		logger.info("crearNuevoSueldo ");
		Empleado empleado = empleadoRepository.findById(empleadoId);
		sueldo.setEmpleado(empleado);
		sueldo.setId(null);
		sueldo = sueldoRepository.saveAndFlush(sueldo);
		//actualizo la lista de sueldos del cliente.
		if(CollectionUtils.isEmpty(empleado.getSueldo())){
			List<Sueldo> sueldos = new ArrayList<Sueldo>();
			sueldos.add(sueldo);
			empleado.setSueldo(sueldos);
		}else{
			empleado.getSueldo().add(sueldo);
			
		}
		empleadoRepository.saveAndFlush(empleado);
		return sueldo;
	}



	@Override
	public List<Vacaciones> getVacacionesPaginado(Integer page, Integer rows,Long empleadoId) {
		logger.info("get vacaciones paginados y filtrados");
		 Page<Vacaciones> requestedPage = null;
		 
		 if(page > 0){
			 page = page - 1;
		 }
		 Specifications<Vacaciones> spec = Specifications.where(VacacionesSpecification.idEquals(empleadoId));

		requestedPage = vacacionesRepository.findAll(spec,constructPageVacacionesSpecification(page,rows));	 
	     
        return requestedPage.getContent();
	}
	
	private Pageable constructPageVacacionesSpecification(Integer page, Integer rows) {
		logger.info("constructPageSpecification ");
		 Pageable pageSpecification = new PageRequest(page, rows, new Sort(Sort.Direction.ASC, "fechaInicio"));
	     return pageSpecification;
	}

	@Override
	public int getTotalVacacionesByEmpleado(Long empleadoId) {
		return (int) vacacionesRepository.count(Specifications.where(VacacionesSpecification.idEquals(empleadoId)));
	}

	@Override
	public void saveVacaciones(Vacaciones vacacion, Long empleadoId) {
		Empleado empleado = empleadoRepository.findById(empleadoId);
		vacacion.setEmpleado(empleado);
		
		if(vacacion.getId() == null) {
			empleado.getVacaciones().add(vacacion);
		}
		
		vacacionesRepository.saveAndFlush(vacacion);
		empleadoRepository.save(empleado);
	  }

	@Override
	public Vacaciones getVacacionById(Long vacacionId) {
		return vacacionesRepository.findById(vacacionId);
		
	}

	@Override
	public void deleteVacaciones(Vacaciones vacaciones) {
		vacacionesRepository.delete(vacaciones);
		
	}

	@Override
	public Long getCountEmpleadoByLegajo(Integer legajo, Long id) {
		logger.info("getCountEmpleadoByLegajo ");
	    if(legajo != null){
	    	EmpleadoSpecification empleadoSpecification = new EmpleadoSpecification() ;
		    Specifications<Empleado> spec =  Specifications.where(empleadoSpecification.legajoEquals(legajo));
			if(id != null && id>0){
				spec = spec.and(empleadoSpecification.idNoEquals(id));
			}
			return empleadoRepository.count(spec);
	    }
		return 0l;
	}

	@Override
	public List<Empleado> getEmpleadosByEstado(EstadoEmpleado estado) {
		return empleadoRepository.findByEstadoOrderByApellidosAndNombres(estado);
	}

	@Override
	public List<Vacaciones> getVacacionesByEmpleado(Long empleadoId) {
	
	 Specifications<Vacaciones> spec = Specifications.where(VacacionesSpecification.idEquals(empleadoId));
	 List<Vacaciones> requestedPage = vacacionesRepository.findAll(spec);	 
	 return requestedPage;
	}

	@Override
	public boolean isOldEmpleado(String dni) {
		List<Empleado> list = empleadoRepository.findByEstadoAndDniOrderByIdDesc(EstadoEmpleado.BAJA, dni);
		if(list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Empleado getEmpleadoByDni(String dni) {
		List<Empleado> list = empleadoRepository.findByDniOrderByIdDesc(dni);
		if(list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}

	