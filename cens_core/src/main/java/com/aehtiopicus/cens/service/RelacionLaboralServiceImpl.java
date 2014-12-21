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

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.Puesto;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;
import com.aehtiopicus.cens.repository.EmpleadoRepository;
import com.aehtiopicus.cens.repository.PuestoRepository;
import com.aehtiopicus.cens.repository.RelacionLaboralRepository;
import com.aehtiopicus.cens.repository.RelacionPuestoEmpleadoRepository;
import com.aehtiopicus.cens.specification.EmpleadoSpecification;
import com.aehtiopicus.cens.specification.RelacionPuestoEmpleadoSpecification;
import com.aehtiopicus.cens.utils.Utils;

@Service
@Transactional
public class RelacionLaboralServiceImpl implements RelacionLaboralService{

	private static final Logger logger = Logger.getLogger(RelacionLaboralServiceImpl.class);
	public final static String DD_MM_YYYY = "dd/MM/yyyy";
	public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	
	@Autowired
	protected RelacionLaboralRepository relacionLaboralRepository;
	
	@Autowired
	protected PuestoRepository puestoRepository;
	
	@Autowired
	protected EmpleadoRepository empeladoRepository;
	
	@Autowired
	protected RelacionPuestoEmpleadoRepository relacionPuestoRepository;
	
	@Autowired
	protected EmpleadoService empleadoService;
	
	public void setRelacionPuestoRepository(
			RelacionPuestoEmpleadoRepository relacionPuestoRepository) {
		this.relacionPuestoRepository = relacionPuestoRepository;
	}
	public void setRelacionLaboralRepository(
			RelacionLaboralRepository relacionLaboralRepository) {
		this.relacionLaboralRepository = relacionLaboralRepository;
	}
	public void setPuestoRepository(PuestoRepository puestoRepository) {
		this.puestoRepository = puestoRepository;
	}
	public void setEmpeladoRepository(EmpleadoRepository empeladoRepository) {
		this.empeladoRepository = empeladoRepository;
	}
	public void setEmpleadoService(EmpleadoService empleadoService) {
		this.empleadoService = empleadoService;
	}

	@Override
	public List<RelacionLaboral> search(String clienteIdStr, String apellido, String nombre, Integer pageIndex, Integer rows) {
		Page<Empleado> requestedPage = null; 
		
		if(pageIndex > 0){
			pageIndex = pageIndex - 1;
		}
		 
		Long clienteId = null;
		if(clienteIdStr != null) {
			try {
				clienteId = Long.parseLong(clienteIdStr);
			}catch(Exception e) {
				clienteId = null;
			}
		}
		
		Specifications<Empleado> specs = null ;
		specs = getSpecifications(EstadoEmpleado.ACTUAL, apellido, nombre, clienteId, specs);
		requestedPage = empeladoRepository.findAll(specs, constructPageSpecification(pageIndex,rows));	 

		List<RelacionLaboral> relacionesLaborales = new ArrayList<RelacionLaboral>();
		
		if(requestedPage.getNumberOfElements() > 0){
			RelacionLaboral relacionLaboral = null;
			for (Empleado empleado : requestedPage.getContent()) {
				relacionLaboral = empleado.getRelacionLaboralVigente();
				relacionesLaborales.add(relacionLaboral);
			}
		}
		
		return relacionesLaborales;
	}	
	
	private Specifications<Empleado> getSpecifications(EstadoEmpleado estado, String apellido, String nombre, Long clienteId, Specifications<Empleado> specs){
		boolean needWhere = true;
		if(estado != null){
			specs = Specifications.where(EmpleadoSpecification.estadoEmpleadoEquals(estado));
			needWhere = false;
		}
		
		if(StringUtils.isNotEmpty(apellido)) {
			if(needWhere) {
				specs = Specifications.where(EmpleadoSpecification.apellidoLike(apellido));
				needWhere = false;
			}else {
				specs = specs.and(EmpleadoSpecification.apellidoLike(apellido));				
			}
		}
		
		if(StringUtils.isNotEmpty(nombre)) {
			if(needWhere) {
				specs = Specifications.where(EmpleadoSpecification.nombreLike(nombre));
				needWhere = false;
			}else {
				specs = specs.and(EmpleadoSpecification.nombreLike(nombre));				
			}
		}
		
		
		if(clienteId != null) {
			if(needWhere) {
				specs = Specifications.where(EmpleadoSpecification.clienteIdEquals(clienteId));
				needWhere = false;
			}else {
				specs = specs.and(EmpleadoSpecification.clienteIdEquals(clienteId));				
			}
		}
		
		return specs;
	}
	

	
	 /**
     * Retorna una nueva pagina con el especificado tipo de objeto
     * @param pageIndex Numero de pagina que se quiere obtener
     * @return
     */
    private Pageable constructPageSpecification(int pageIndex, int row) {
        Pageable pageSpecification = new PageRequest(pageIndex, row, sortByApellidoNombreEmpleadoAsc());
     
        return pageSpecification;
    }
    
    /**
     * Retorna  Sort object que ordena empleados acorde al nombre del empleado
     * @return
     */
    private Sort sortByApellidoNombreEmpleadoAsc() {
        return new Sort(Sort.Direction.ASC, "apellidos", "nombres");
    }
	
	
	@Override
	public int getTotalRelacionesLaboralesFilterByCliente(String clienteIdStr, String apellido, String nombre) {
    	logger.info("obteniendo numero de registros");
    	long cantUsers = 0;

		Long clienteId = null;
		if(clienteIdStr != null) {
			try {
				clienteId = Long.parseLong(clienteIdStr);
			}catch(Exception e) {
				clienteId = null;
			}
		}
    	
    	Specifications<Empleado> specs = null;
    	specs = getSpecifications(EstadoEmpleado.ACTUAL, apellido, nombre, clienteId, specs);
    	
   		 cantUsers = empeladoRepository.count(specs);

    	return (int) Math.ceil(cantUsers);

	}

	@Override
	public List<Puesto> getPuestos() {
		logger.info("obteniendo listado de puestos de trabajo");
		return puestoRepository.findAll(new Sort(Sort.Direction.ASC, "nombre"));
	}

	@Override
	public Puesto savePuesto(Puesto puesto) {
		logger.info("creando puesto");
		
		return puestoRepository.saveAndFlush(puesto);
	}

	@Override
	public RelacionLaboral createAndSave(RelacionLaboral relacionLaboral) {
		Empleado empleado = empeladoRepository.findById(relacionLaboral.getEmpleado().getId());
		empleado.addRelacionLaboral(relacionLaboral);
		relacionLaboralRepository.saveAndFlush(relacionLaboral);
		
		RelacionPuestoEmpleado puestoEmpleado = new RelacionPuestoEmpleado();
		puestoEmpleado.setPuesto(relacionLaboral.getPuesto());
		puestoEmpleado.setFechaInicio(relacionLaboral.getFechaInicio());
		puestoEmpleado.setRelacionLaboral(relacionLaboral);
		relacionPuestoRepository.save(puestoEmpleado);
		updateEmpleadoRelacionPuesto(empleado, puestoEmpleado);
		empeladoRepository.saveAndFlush(empleado);
		return relacionLaboral;
	}

	private void updateEmpleadoRelacionPuesto(Empleado empleado,
			RelacionPuestoEmpleado puestoEmpleado) {
		if(CollectionUtils.isEmpty(empleado.getPuestos())){
			List<RelacionPuestoEmpleado> relacionPuesto = new ArrayList<RelacionPuestoEmpleado>();
			relacionPuesto.add(puestoEmpleado);
			relacionPuestoRepository.save(relacionPuesto);
			empleado.setPuestos(relacionPuesto);
		}else{
			List<RelacionPuestoEmpleado> relacion = empleado.getPuestos();
			relacion.add(puestoEmpleado);
			empleado.setPuestos(relacion);
		}
	}	
	
	@Override
	public RelacionLaboral update(RelacionLaboral relacionLaboral) {
		
		relacionLaboralRepository.saveAndFlush(relacionLaboral);
		updateRelacionPuesto(relacionLaboral);
		return relacionLaboral;
	}

	private void updateRelacionPuesto(RelacionLaboral relacionLaboral) {
		List<RelacionPuestoEmpleado> puestos = getPuestos(relacionLaboral);
		RelacionPuestoEmpleado puesto = puestos.get(0);
		puesto.setFechaFin(new Date());
		relacionPuestoRepository.saveAndFlush(puesto);
	}

	private List<RelacionPuestoEmpleado> getPuestos(
			RelacionLaboral relacionLaboral) {
		Specifications<RelacionPuestoEmpleado> spec = Specifications.where(RelacionPuestoEmpleadoSpecification.idEquals(relacionLaboral.getId()));
		spec = spec.and(RelacionPuestoEmpleadoSpecification.fechaFinEmpty());
		List<RelacionPuestoEmpleado> puestos = relacionPuestoRepository.findAll(spec);
		return puestos;
	}

	@Override
	public RelacionLaboral getRelacionLaboralById(Long relacionLaboralId) {
		return relacionLaboralRepository.findOne(relacionLaboralId);
	}

	

	@Override
	public void updateRelacionLaboralAndPuesto(RelacionPuestoEmpleado relacion) {
		RelacionLaboral relacionLaboral = relacionLaboralRepository.findOne(relacion.getRelacionLaboral().getId());
		List<RelacionPuestoEmpleado> puestos = getPuestos(relacionLaboral);
		if(!CollectionUtils.isEmpty(puestos)){
			RelacionPuestoEmpleado puesto = puestos.get(0);
			
			if(sdf.format(puesto.getFechaInicio()).equals(sdf.format(relacion.getFechaInicio()))){
				puesto.setPuesto(relacion.getPuesto());
				relacionLaboral.setPuesto(relacion.getPuesto());
				relacionPuestoRepository.save(puesto);
				relacionLaboralRepository.save(relacionLaboral);
				
			}else{
				puesto.setFechaFin(new Date());
				relacionPuestoRepository.save(puesto);
				RelacionPuestoEmpleado puestoEmpleado = new RelacionPuestoEmpleado();
				puestoEmpleado.setFechaInicio(relacion.getFechaInicio());
				puestoEmpleado.setPuesto(relacion.getPuesto());
				puestoEmpleado.setRelacionLaboral(relacionLaboral);
			    relacionPuestoRepository.save(puestoEmpleado);
				relacionLaboral.setPuesto(puestoEmpleado.getPuesto());
				relacionLaboralRepository.save(relacionLaboral);
				updateEmpleadoRelacionPuesto(relacionLaboral.getEmpleado(), puestoEmpleado);
				empeladoRepository.saveAndFlush(relacionLaboral.getEmpleado());
			}
		}	
	}

	@Override
	public RelacionPuestoEmpleado getRelacionPuestoEmpleado(
			Long relacionLaboralId) {
		RelacionLaboral relacionLaboral = relacionLaboralRepository.findOne(relacionLaboralId);
		List<RelacionPuestoEmpleado> puestos = getPuestos(relacionLaboral);
		RelacionPuestoEmpleado puesto = null;
		if(!CollectionUtils.isEmpty(puestos)){
			puesto = puestos.get(0);
		}
	
		return puesto;
	}

	@Override
	public Puesto getPuestoByNombre(String nombre){
		return puestoRepository.findByNombreIgnoreCase(nombre);
	}

	@Override
	public List<Sueldo> aplicarIncrementoMasivo(Long clienteId, Date fechaInicio, Double incBasico, Double incPresentismo, String tipoIncremento) {
		List<Sueldo> sueldosUpdated = new ArrayList<Sueldo>();
		Cliente c = new Cliente();
		c.setId(clienteId);
		
		List<RelacionLaboral> relaciones = relacionLaboralRepository.findByClienteAndFechaFinIsNull(c);
		logger.info(relaciones.size());
		
		Empleado empleado;
		Sueldo lastSueldo;
		Sueldo newSueldo;
		for (RelacionLaboral relacionLaboral : relaciones) {
			empleado = relacionLaboral.getEmpleado();
			lastSueldo = empleado.getSueldoVigente();
			
			if(!lastSueldo.getFechaInicio().after(fechaInicio)) {
				newSueldo = new Sueldo();
				newSueldo.setId(lastSueldo.getId());
				if(tipoIncremento.equals("%")) {
					newSueldo.setBasico(Utils.redondear2Decimales(lastSueldo.getBasico() * (incBasico + 100) / 100));
					newSueldo.setPresentismo(Utils.redondear2Decimales(lastSueldo.getPresentismo() * (incPresentismo + 100) / 100));
				}else if(tipoIncremento.equals("$")) {
					newSueldo.setBasico(Utils.redondear2Decimales(lastSueldo.getBasico() + incBasico));
					newSueldo.setPresentismo(Utils.redondear2Decimales(lastSueldo.getPresentismo() + incPresentismo));					
				}
				newSueldo.setFechaInicio(fechaInicio);
				newSueldo.setEmpleado(empleado);
				newSueldo = empleadoService.saveSueldo(newSueldo, empleado.getId());
				sueldosUpdated.add(newSueldo);
			}
			
		}
		
		return sueldosUpdated;
	}

	@Override
	public List<RelacionLaboral> getRelacionesLaboralesVigentesByCliente(Long clienteId){
		Cliente c = new Cliente();
		c.setId(clienteId);
		return relacionLaboralRepository.findByClienteAndFechaFinIsNull(c);
	}
}
