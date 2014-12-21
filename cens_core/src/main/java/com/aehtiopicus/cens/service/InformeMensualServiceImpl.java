package com.aehtiopicus.cens.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.aehtiopicus.cens.aspect.InformeMensualAspect;
import com.aehtiopicus.cens.domain.BeneficioCliente;
import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.domain.InformeMensualDetalle;
import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;
import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.Sueldo;
import com.aehtiopicus.cens.domain.Usuario;
import com.aehtiopicus.cens.domain.Vacaciones;
import com.aehtiopicus.cens.enumeration.InformeIntermedioEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;
import com.aehtiopicus.cens.enumeration.TipoBeneficioEnum;
import com.aehtiopicus.cens.repository.ClienteRepository;
import com.aehtiopicus.cens.repository.InformeMensualDetalleBeneficioRepository;
import com.aehtiopicus.cens.repository.InformeMensualDetalleRepository;
import com.aehtiopicus.cens.repository.InformeMensualRepository;
import com.aehtiopicus.cens.repository.RelacionLaboralRepository;
import com.aehtiopicus.cens.specification.InformeMensualSpecification;
import com.aehtiopicus.cens.utils.PeriodoUtils;

@Service
@Transactional
public class InformeMensualServiceImpl implements InformeMensualService {
	private static final Logger logger = Logger.getLogger(InformeMensualServiceImpl.class);
	
	@Value("${periodo.dias}") 
	protected Integer diasPeriodoCompleto;
	
	@Autowired
	protected InformeMensualRepository informeMensualRepository;
	
	@Autowired
	protected InformeMensualDetalleRepository informeMensualDetalleRepository;

	@Autowired
	protected InformeMensualDetalleBeneficioRepository informeMensualDetalleBeneficioRepository;
	
	@Autowired
	protected RelacionLaboralRepository relacionLaboralRepository;
	
	
	public final static String DD_MM_YYYY = "dd/MM/yyyy";
		
	public static SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
	
	public void setInformeMensualRepository(
			InformeMensualRepository informeMensualRepository) {
		this.informeMensualRepository = informeMensualRepository;
	}

	public void setRelacionLaboralRepository(
			RelacionLaboralRepository relacionLaboralRepository) {
		this.relacionLaboralRepository = relacionLaboralRepository;
	}
	
	
	public void setDiasPeriodoCompleto(Integer diasPeriodoCompleto) {
		this.diasPeriodoCompleto = diasPeriodoCompleto;
	}

	public void setInformeMensualDetalleRepository(
			InformeMensualDetalleRepository informeMensualDetalleRepository) {
		this.informeMensualDetalleRepository = informeMensualDetalleRepository;
	}

	public void setInformeMensualDetalleBeneficioRepository(
			InformeMensualDetalleBeneficioRepository informeMensualDetalleBeneficioRepository) {
		this.informeMensualDetalleBeneficioRepository = informeMensualDetalleBeneficioRepository;
	}

	
	@Override
	public List<InformeMensual> searchInformesMensuales(Usuario usuario, String clienteStr,	String periodoStr, Integer page, Integer rows) {
		Page<InformeMensual> requestedPage = null; 
		
		if(page > 0){
			page = page - 1;
		}
		 
		Long clienteId = null;
		if(clienteStr != null) {
			try {
				clienteId = Long.parseLong(clienteStr);
			}catch(Exception e) {
				clienteId = null;
			}
		}
		
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodoStr)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		}
		
		Specifications<InformeMensual> specs = null ;
		specs = getSpecifications(usuario, clienteId, periodo, specs);
		requestedPage = informeMensualRepository.findAll(specs, constructPageSpecification(page,rows));	 
	
		return requestedPage.getContent();
	}

	@Override
	public int getNroTotalInformesMensuales(Usuario usuario, String clienteStr, String periodoStr) {
    	long cantUsers = 0;

		Long clienteId = null;
		if(clienteStr != null) {
			try {
				clienteId = Long.parseLong(clienteStr);
			}catch(Exception e) {
				clienteId = null;
			}
		}
		Date periodo = null;
		if(StringUtils.isNotEmpty(periodoStr)) {
			periodo = PeriodoUtils.getDateFormPeriodo(periodoStr);
		}
		
		Specifications<InformeMensual> specs = null ;
		specs = getSpecifications(usuario, clienteId, periodo, specs);
    	
   		cantUsers = informeMensualRepository.count(specs);
    	return (int) Math.ceil(cantUsers);
	}

	
	private Specifications<InformeMensual> getSpecifications(Usuario usuario, Long clienteId, Date periodo, Specifications<InformeMensual> specs){
		boolean needWhere = true;
		if(clienteId != null){
			specs = Specifications.where(InformeMensualSpecification.clienteEquals(clienteId));
			needWhere = false;
		}
		
		if(periodo != null) {
			if(needWhere) {
				specs = Specifications.where(InformeMensualSpecification.periodoEquals(periodo));
				needWhere = false;
			}else {
				specs = specs.and(InformeMensualSpecification.periodoEquals(periodo));				
			}
		}

		if(usuario != null) {
			if(needWhere) {
				specs = Specifications.where(InformeMensualSpecification.usuarioEquals(usuario.getId()));
				needWhere = false;
			}else {
				specs = specs.and(InformeMensualSpecification.usuarioEquals(usuario.getId()));				
			}
		}
		
		return specs;
	}	
	
    private Pageable constructPageSpecification(int pageIndex, int row) {
        Pageable pageSpecification = new PageRequest(pageIndex, row, sortByPeriodoAsc());
     
        return pageSpecification;
    }
    

    private Sort sortByPeriodoAsc() {
    	
    	// return new Sort(Sort.Direction.ASC, "apellidos", "nombres");
        return new Sort(Sort.Direction.DESC, "periodo").and(new Sort(Sort.Direction.ASC, "cliente.razonSocial"));
    }

    /**
     * calcula el numero de dias habiles que el empleado debe haber trabajado durante el periodo
     * descontando las vacaciones si las tuvo y los dias que no estuvo asignado al cliente si es que 
     * comenzo y dejo de trabajar para el cliente durante el periodo en cuestion
     * @param rl
     * @param periodoFIni
     * @param periodoFFin
     * @return
     */
    private Double calcularDiasTrabajados(RelacionLaboral rl, Date periodoFIni, Date periodoFFin) {
    	Integer diasPeriodo = diasPeriodoCompleto;
    	//modifico fechas inicial y final del periodo si el empleado comenzo o dejo de trabajar en el cliente durante ese periodo
    	//EJEMPLO: el tipo comienza a trabajar el dia 10 del periodo, la fecha inicial del periodo para el sera el 10 y  no el 1ro del mes.
    	if(rl.getFechaInicio() != null && PeriodoUtils.fechaEnRangoFechas(rl.getFechaInicio(), periodoFIni, periodoFFin)) {
    		diasPeriodo = (diasPeriodo - rl.getFechaInicio().getDate())+1;
    	}
    	if(rl.getFechaFin() != null && PeriodoUtils.fechaEnRangoFechas(rl.getFechaFin(), periodoFIni, periodoFFin)) {
    		diasPeriodo = diasPeriodo - (diasPeriodoCompleto - rl.getFechaFin().getDate());
    	}
    	
		//calculo dias del periodo para el empleado
//		int diasPeriodo = PeriodoUtils.getDiasEntreFechas(periodoFIni, periodoFFin);

    	if(diasPeriodo > diasPeriodoCompleto) {
			diasPeriodo = diasPeriodoCompleto;
		}else if(diasPeriodo < 0){
			diasPeriodo = 0;
		}

		//..ESTE CALCULO NO SE HACE..
		//int diasEnVacaciones = calcularDiasDeVacacionesEnPeriodo(rl.getEmpleado(), periodoFIni, periodoFFin);

		//returno la diferencia que debe ser igual a los dias habiles trabajados por el empleado durante el periodo
		return new Double(diasPeriodo);
    }
    
    private Integer calcularDiasDeVacacionesEnPeriodo(Empleado empleado, Date periodoFIni, Date periodoFFin){
    	Integer nroDias = 0;
    	
    	if(empleado.getVacaciones() == null || empleado.getVacaciones().size() == 0) {
    		return nroDias;
    	}
    	
    	Date start;
    	Date end;
    	for (Vacaciones vacacion : empleado.getVacaciones()) {
			if(PeriodoUtils.fechaEnRangoFechas(vacacion.getFechaInicio(), periodoFIni, periodoFFin) ||
					PeriodoUtils.fechaEnRangoFechas(vacacion.getFechaHasta(), periodoFIni, periodoFFin)){
				
				if(vacacion.getFechaInicio().after(periodoFIni)) {start = vacacion.getFechaInicio();}
				else {start = periodoFIni;}
				
				if(vacacion.getFechaHasta().before(periodoFFin)) {end = vacacion.getFechaHasta();}
				else {end = periodoFFin;}
				
				nroDias += PeriodoUtils.getDiasEntreFechas(start, end);
			}
		}
    	
    	return nroDias;
    }
    
    
    private Map<Long, InformeMensualDetalle> getDetalleMapFromDetalleList(List<InformeMensualDetalle> detalles){
    	if(detalles == null) {
    		return null;
    	}
    	
    	Map<Long, InformeMensualDetalle> map = new HashMap<Long, InformeMensualDetalle>();
    	for (InformeMensualDetalle detalle : detalles) {
			map.put(detalle.getRelacionLaboral().getEmpleado().getId(), detalle);
		}
    	
    	return map;
    }
    
    private Map<Long, Double> getDetalleBeneficioMapFromDetalleBeneficioList(List<InformeMensualDetalleBeneficio> detalles){
    	if(detalles == null) {
    		return null;
    	}
    	
    	Map<Long, Double> map = new HashMap<Long, Double>();
    	for (InformeMensualDetalleBeneficio detalle : detalles) {
			map.put(detalle.getBeneficio().getId(), detalle.getValor());
		}
    	
    	return map;
    }
    
	@Override
	public InformeMensual crear(InformeMensual informeMensual, Boolean usarInformeAnterior) {
		
		//Obtengo comienzo proximo periodo:
		Date proximoPeriodo = PeriodoUtils.getNextPeriodo(informeMensual.getPeriodo());
		
		//Obtengo dia final del periodo
		Calendar finPeriodo = Calendar.getInstance();
		finPeriodo.setTime(proximoPeriodo);
		finPeriodo.add(Calendar.DATE, -1);
				
		//Obtengo las relaciones laborales correspondientes al periodo y cliente
		List<RelacionLaboral> relaciones = relacionLaboralRepository.getRelacionesLaboralesByClienteAndPeriodo(informeMensual.getCliente(),proximoPeriodo, informeMensual.getPeriodo());
		List<BeneficioCliente> beneficiosCliente = null;
		
		//si usarInformeAnterior=true obtengo el informe mensual previo y cargo en un map los detalles del informe previo
		Map<Long, InformeMensualDetalle> imPrevioDetallesMap = null;
		if(usarInformeAnterior) {
			Pageable topOne = new PageRequest(0, 1);
			List<InformeMensual> imPrevioList = informeMensualRepository.findByClienteOrderByPeriodoDesc(informeMensual.getCliente(), topOne);
			if(imPrevioList != null && imPrevioList.size() > 0) {
				imPrevioDetallesMap = getDetalleMapFromDetalleList(imPrevioList.get(0).getDetalle());
			}
		}
		
		if(relaciones != null && relaciones.size() > 0) {
			InformeMensualDetalle detalle;
			beneficiosCliente = relaciones.get(0).getCliente().getBeneficios();
		
			Map<Long, InformeMensualDetalle> aux = new HashMap<Long, InformeMensualDetalle>();
			
			//creo los detalles del informe (uno por cada empleado)
			for (RelacionLaboral relacionLaboral : relaciones) {
				if(aux.containsKey(relacionLaboral.getEmpleado().getId())) {
					detalle = aux.get(relacionLaboral.getEmpleado().getId());
				}else {
					detalle = new InformeMensualDetalle();
					aux.put(relacionLaboral.getEmpleado().getId(), detalle);
				}

				this.addOrUpdateEmpleadoToInforme(detalle, relacionLaboral, informeMensual, usarInformeAnterior, imPrevioDetallesMap);
			}
		}
		
		informeMensualRepository.save(informeMensual);		
		
		return informeMensual;
	}

	@Override
	public InformeMensual getByClienteAndPeriodo(Cliente cliente, Date periodo) {
		return informeMensualRepository.findByClienteAndPeriodo(cliente, periodo);
	}

	@Override
	public InformeMensual getById(Long informeMensualId) {
		return informeMensualRepository.findOne(informeMensualId);
	}

	@Override
	public List<InformeMensual> getInformesNoConsolidadosPaginado(String cliente, String periodo, String gerente,
			String estado,List<Cliente> clientes) throws ParseException {
		
		Date periodoActual = getPeriodo(periodo);
		Specifications<InformeMensual> spec = Specifications.where(InformeMensualSpecification.periodoEquals(periodoActual));
		if(StringUtils.isNotEmpty(cliente)){
			Long clienteId = Long.valueOf(cliente);
			spec = spec.and(InformeMensualSpecification.clienteEquals(clienteId));
		}
		if(StringUtils.isNotBlank(gerente)){
			Long gerenteId = Long.valueOf(gerente);
			spec = spec.and(InformeMensualSpecification.usuarioEquals(gerenteId));
		}
		if(StringUtils.isNotBlank(estado)){
			InformeIntermedioEstadoEnum estad = InformeIntermedioEstadoEnum.getInformeMensualEstadoEnumFromString(estado);
			InformeMensualEstadoEnum estadoInforme = null;
			if(estad.equals(InformeIntermedioEstadoEnum.RECIBIDO)){
				estadoInforme = InformeMensualEstadoEnum.ENVIADO;
			}
			if(estad.equals(InformeIntermedioEstadoEnum.PENDIENTE)){
				estadoInforme = InformeMensualEstadoEnum.BORRADOR;
			}
			if(estad.equals(InformeIntermedioEstadoEnum.CONSOLIDADO)){
				estadoInforme = InformeMensualEstadoEnum.CONSOLIDADO;
			}
			spec = spec.and(InformeMensualSpecification.estadoEquals(estadoInforme));
		}else{
			spec = spec.and(InformeMensualSpecification.estadoEqualsAllStatus());
		}
		
		return  informeMensualRepository.findAll(spec);
	
	}

	/**
	 * obtener el periodo segun el string enviado o si es null devolver el periodo actual
	 * @param periodo
	 * @return
	 */
	private Date getPeriodo(String periodo) {
		Date periodoActual = null;
		if(!StringUtils.isEmpty(periodo)){
			periodoActual = PeriodoUtils.getDateFormPeriodo(PeriodoUtils.getPeriodoFromDate(PeriodoUtils.getDateFormPeriodo(periodo)));
			
		}else{
			periodoActual = PeriodoUtils.getDateFormPeriodo(PeriodoUtils.getPeriodoFromDate(new Date())); 
		}
		return periodoActual;
	}

	@Override
	public InformeMensualDetalle getDetalleById(Long id){
		return informeMensualDetalleRepository.findOne(id);
	}

	@Override
	public List<InformeMensualDetalle> updateDetalles(List<InformeMensualDetalle> detalles) {
		for (InformeMensualDetalle detalle : detalles) {
			if(detalle.getBeneficios().size() > 0) {
				informeMensualDetalleBeneficioRepository.save(detalle.getBeneficios());
			}
		}
		return informeMensualDetalleRepository.save(detalles);
		
	}

	@Override
	public List<Cliente> getClientesSinInformes(List<Cliente> clientes, String periodo) {
		List<Cliente> clientesSinInforme = new ArrayList<Cliente>();
		if(!CollectionUtils.isEmpty(clientes)){
			Date periodoActual = getPeriodo(periodo);
			for(Cliente cliente : clientes){
				Specifications<InformeMensual> spec = Specifications.where(InformeMensualSpecification.periodoEquals(periodoActual));
				spec = spec.and(InformeMensualSpecification.clienteEquals(cliente.getId()));
				List<InformeMensual> informes = informeMensualRepository.findAll(spec);
				if(CollectionUtils.isEmpty(informes)){
					clientesSinInforme.add(cliente);
				}
			}
		
		}
		return clientesSinInforme;
	}

	@Override
	public boolean enviarInforme(Long informeMensuyalId) {
		if(informeMensuyalId != null) {
			InformeMensual informe = informeMensualRepository.findOne(informeMensuyalId);
			if(informe != null){
				informe.setEstado(InformeMensualEstadoEnum.ENVIADO);
				informeMensualRepository.save(informe);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean rechazarInforme(Long informeMensualId) {
		if(informeMensualId != null) {
			InformeMensual informe = informeMensualRepository.findOne(informeMensualId);
			if(informe != null){
				informe.setEstado(InformeMensualEstadoEnum.BORRADOR);
				informeMensualRepository.save(informe);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean eliminarInforme(Long informeMensualId) {
		InformeMensual im = informeMensualRepository.findOne(informeMensualId);
		if(im == null) {
			return false;
		}
		//elimino los detalles
		for (InformeMensualDetalle det : im.getDetalle()) {
			informeMensualDetalleBeneficioRepository.delete(det.getBeneficios());
			informeMensualDetalleRepository.delete(det);
		}
		//elimino el informe		
		informeMensualRepository.delete(informeMensualId);
		return true;
	}

	/**
	 * Agrega o Actualiza un detalle luego de que una relacion laboral ah sido dada de alta
	 * @params:
	 * RelacionLaboral relacionLaboral: la relacion laboral que se ha agregado
	 */
	@Override
	public void addInformeMensualDetalle(RelacionLaboral relacionLaboral) {
		logger.info(">>> Actualizando InformesMensuales luego de crear una nueva RelacionLaboral #" + relacionLaboral.getId());
		
		List<InformeMensual> informes = informeMensualRepository.findByClienteAndEstado(relacionLaboral.getCliente(), InformeMensualEstadoEnum.BORRADOR);
		
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se encuentran Informes abiertos para el cliente #" + relacionLaboral.getCliente().getId());
			return;
		}
		
		//me quedo solo con los informes cuya fecha final del periodo es mayor a la fecha ingreso de la relacion laboral
		List<InformeMensual> noActualizarList = new ArrayList<InformeMensual>();
		for (InformeMensual im : informes) {
			if(!relacionLaboral.getFechaInicio().before(PeriodoUtils.getFechaFinPeriodo(im.getPeriodo()))){
				noActualizarList.add(im);
			}
		}
		informes.removeAll(noActualizarList);
		
		//si la collecion quedo vacia no se debe actualizar nada
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se necesario modificar ningun informe mensual.");
			return;
		}
		
		
		for (InformeMensual im : informes) {
			//Busco si ya existe un detalle para el empleado dentro del informe.. de ser asi acutalizo ese detalle.. sino creo uno nuevo
			InformeMensualDetalle detalle = this.getDetalleByEmpleado(relacionLaboral.getEmpleado(), im);
			if(detalle == null){
				detalle = new InformeMensualDetalle();
			}
			
			this.addOrUpdateEmpleadoToInforme(detalle, relacionLaboral, im, false, null);
			informeMensualRepository.save(im);
			
			logger.info("Empleado: Leg#" + relacionLaboral.getEmpleado().getLegajo() + " -> agregado a informeMensual: " + im.getId());
		}
	}

	/**
	 * Actualiza un detalle luego de que una relacion laboral ah sido dada de baja
	 * @params:
	 * RelacionLaboral relacionLaboral: la relacion laboral que ha concluido
	 */
	@Override
	public void updateInformeMensualDetalle(RelacionLaboral relacionLaboral) {
		logger.info(">>> Actualizando InformesMensuales luego de dar de baja la RelacionLaboral #" + relacionLaboral.getId());
		
		Date periodo = PeriodoUtils.getFechaComienzoPeriodo(relacionLaboral.getFechaFin());
		InformeMensual im = informeMensualRepository.findByClienteAndPeriodoAndEstado(relacionLaboral.getCliente(),periodo, InformeMensualEstadoEnum.BORRADOR);
		
		if(im == null){
			logger.info("No se necesario modificar ningun informe mensual.");
			return;			
		}
		
		InformeMensualDetalle detalle = this.getDetalleByEmpleado(relacionLaboral.getEmpleado(), im);
		if(detalle == null){
			logger.info("No se encontro detalle en el informe a editar");
			return;
		}
		
		Integer diaBaja = relacionLaboral.getFechaFin().getDate();
		if(diaBaja > diasPeriodoCompleto){
			diaBaja = diasPeriodoCompleto;
		}
		
		detalle.setDiasTrabajados(detalle.getDiasTrabajados() + (diaBaja - diasPeriodoCompleto));
		informeMensualDetalleRepository.save(detalle);
		
		logger.info("Empleado: Leg#" + relacionLaboral.getEmpleado().getLegajo() + " -> editado en informeMensual: " + im.getId());

	}
	
	/**
	 * Actualiza los detalles de los informes mensuales en estado 'borrador' cuando se agrega un nuevo beneficio al cliente
	 * @param 
	 * beneficioCliente: Nuevo beneficio asociado al cliente.
	 */
	@Override
	public void addBeneficioToInformeMensualDetalles(BeneficioCliente beneficioCliente){
		logger.info(">>> Actualizando InformesMensuales luego de agregar el beneficioCliente #" + beneficioCliente.getId());
		
		List<InformeMensual> informes = informeMensualRepository.findByClienteAndEstado(beneficioCliente.getCliente(), InformeMensualEstadoEnum.BORRADOR);
		
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se encuentran Informes abiertos para el cliente " + beneficioCliente.getCliente().getNombre());
			return;
		}		
		
		for (InformeMensual im : informes) {
			for (InformeMensualDetalle imDetalle : im.getDetalle()) {
				
				InformeMensualDetalleBeneficio informeBeneficio = addBeneficioToDetalle(beneficioCliente, imDetalle, null);
				if(informeBeneficio != null){
					informeMensualDetalleBeneficioRepository.save(informeBeneficio);
					imDetalle.getBeneficios().add(informeBeneficio);
				}				
			}	
			informeMensualDetalleRepository.save(im.getDetalle());		
			logger.info("BeneficioCliente #: " + beneficioCliente.getId() + " -> agregado a informeMensual: " + im.getId());

		}
	}
	
	/**
	 * Actualiza los detalles de los informes mensuales en estado 'borrador' cuando se desactiva/activa un beneficio beneficio cliente
	 * @param 
	 * beneficioCliente: Beneficio del cliente que paso a estar activado o desactivado
	 */
	@Override
	public void updateBeneficioInInformeMensualDetalles(BeneficioCliente beneficioCliente) {
		logger.info(">>> Actualizando InformesMensuales luego de activar/desactivar el beneficioCliente #" + beneficioCliente.getId());
		
		List<InformeMensual> informes = informeMensualRepository.findByClienteAndEstado(beneficioCliente.getCliente(), InformeMensualEstadoEnum.BORRADOR);
		
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se encuentran Informes abiertos para el cliente " + beneficioCliente.getCliente().getNombre());
			return;
		}		
		
		for (InformeMensual im : informes) {
			for (InformeMensualDetalle imDetalle : im.getDetalle()) {
				if(beneficioCliente.getVigente()) {
					//si activaron el beneficio lo agrego a los detalles
					InformeMensualDetalleBeneficio informeBeneficio = addBeneficioToDetalle(beneficioCliente, imDetalle, null);
					if(informeBeneficio != null){
						informeMensualDetalleBeneficioRepository.save(informeBeneficio);
						imDetalle.getBeneficios().add(informeBeneficio);
					}						
				}else {
					//si desactivaron el beneficio lo elimino de los detalles
					for (InformeMensualDetalleBeneficio imdBeneficio : imDetalle.getBeneficios()) {
						if(imdBeneficio.getBeneficio().getId().equals(beneficioCliente.getId())) {
							imDetalle.getBeneficios().remove(imdBeneficio);
							informeMensualDetalleBeneficioRepository.delete(imdBeneficio);
							break;
						}
					}
				}
		
			}	
			informeMensualDetalleRepository.save(im.getDetalle());		
			if(beneficioCliente.getVigente()) {
				logger.info("BeneficioCliente #: " + beneficioCliente.getId() + " -> agregado a informeMensual: " + im.getId());				
			}else {
				logger.info("BeneficioCliente #: " + beneficioCliente.getId() + " -> removido de informeMensual: " + im.getId());								
			}

		}		
	}

	
	/**
	 * Actualiza los detalles de los informes mensuales en estado 'borrador' cuando se modifica el sueldo de un empleado
	 * @param 
	 * sueldo: Sueldo modificado del empleado
	 */
	@Override
	public void updateSalarioInInformeMensualDetalles(Sueldo sueldo) {
		logger.info(">>> Actualizando InformesMensuales luego de crear/modificar el sueldo #" + sueldo.getId());
		
		if(sueldo.getEmpleado().getRelacionLaboralVigente().getCliente() == null){
			logger.info("El empleado no posee una relacion laboral vigente => no hay informes que actualizar");
			return;
		}
		
		List<InformeMensual> informes = informeMensualRepository.findByClienteAndEstado(sueldo.getEmpleado().getRelacionLaboralVigente().getCliente(), InformeMensualEstadoEnum.BORRADOR);
		
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se encuentran Informes abiertos para el cliente #" + sueldo.getEmpleado().getRelacionLaboralVigente().getCliente().getId());
			return;
		}		
		
		//me quedo solo con los informes cuya fecha final del periodo es mayor a la fecha del nuevo sueldo
		List<InformeMensual> noActualizarList = new ArrayList<InformeMensual>();
		for (InformeMensual im : informes) {
			if(!sueldo.getFechaInicio().before(PeriodoUtils.getFechaFinPeriodo(im.getPeriodo()))){
				noActualizarList.add(im);
			}
		}
		informes.removeAll(noActualizarList);
		
		//si la collecion quedo vacia no se debe actualizar nada
		if(CollectionUtils.isEmpty(informes)){
			logger.info("No se necesario modificar ningun informe mensual.");
			return;
		}
		
		for (InformeMensual im : informes) {
			for (InformeMensualDetalle imDetalle : im.getDetalle()) {
				if(imDetalle.getRelacionLaboral().getEmpleado().getId().equals(sueldo.getEmpleado().getId())) {
					imDetalle = informeMensualDetalleRepository.findOne(imDetalle.getId());
					if(imDetalle.getSueldoBasico() != null){
						imDetalle.setSueldoBasico(sueldo.getBasico());
					}
					if(imDetalle.getPresentismo() != null && 
						(!imDetalle.getPresentismo().equals(0d) || imDetalle.getDiasTrabajados().equals(30d))) {
						
						imDetalle.setPresentismo(sueldo.getPresentismo());
					}
					informeMensualDetalleRepository.save(imDetalle);
					break;
				}
			}
			
			logger.info("Sueldo actualizado al Empleado: Leg#" + sueldo.getEmpleado().getLegajo() + " en informeMensual: " + im.getId());	
		}
	}
	
	private InformeMensualDetalle getDetalleByEmpleado(Empleado empleado, InformeMensual informeMensual){
		for (InformeMensualDetalle imDetalle : informeMensual.getDetalle()) {
			if(imDetalle.getRelacionLaboral().getEmpleado().getId().equals(empleado.getId())){
				return imDetalle;
			}
		}
		return null;
	}
	
	private void addOrUpdateEmpleadoToInforme(InformeMensualDetalle detalle, RelacionLaboral relacionLaboral, InformeMensual informeMensual, Boolean usarInformeAnterior, Map<Long, InformeMensualDetalle> imPrevioDetallesMap) {

		detalle.setRelacionLaboral(relacionLaboral);
		
		detalle.setDiasTrabajados( detalle.getDiasTrabajados() + this.calcularDiasTrabajados(relacionLaboral, informeMensual.getPeriodo(), PeriodoUtils.getFechaFinPeriodo(informeMensual.getPeriodo())));
		
		detalle.setPresentismo(relacionLaboral.getEmpleado().getSueldoVigente().getPresentismo());
		detalle.setSueldoBasico(relacionLaboral.getEmpleado().getSueldoVigente().getBasico());
		
		if(detalle.getId() == null) {
			List<InformeMensualDetalleBeneficio> beneficios = null;
			List<BeneficioCliente> beneficiosCliente = relacionLaboral.getCliente().getBeneficios();
			
			if(beneficiosCliente != null) {
				beneficios = new ArrayList<InformeMensualDetalleBeneficio>();
				InformeMensualDetalleBeneficio informeBeneficio = null;
				
				Map<Long, Double> beneficiosImDetallePrevio = null;
				if(imPrevioDetallesMap != null) {
					InformeMensualDetalle imDetallePrevio = imPrevioDetallesMap.get(detalle.getRelacionLaboral().getEmpleado().getId());
					if(imDetallePrevio != null) {
						beneficiosImDetallePrevio = getDetalleBeneficioMapFromDetalleBeneficioList(imDetallePrevio.getBeneficios());
					}
				}
				
				for (BeneficioCliente beneficioCliente : beneficiosCliente) {

					Double valorInformePrevio = null;
					if(beneficiosImDetallePrevio != null) {
						valorInformePrevio = beneficiosImDetallePrevio.get(beneficioCliente.getId());
					}
					informeBeneficio = addBeneficioToDetalle(beneficioCliente, detalle, valorInformePrevio);
					if(informeBeneficio != null) {
						beneficios.add(informeBeneficio);
					}
				}

				informeMensualDetalleBeneficioRepository.save(beneficios);
			}
			detalle.setBeneficios(beneficios);
			informeMensual.getDetalle().add(detalle);
			detalle = informeMensualDetalleRepository.save(detalle);
		}
	}
	
	private InformeMensualDetalleBeneficio addBeneficioToDetalle(BeneficioCliente beneficioCliente, InformeMensualDetalle imDetalle, Double valorInformePrevio){
		InformeMensualDetalleBeneficio informeBeneficio = null;
		
		//evita que se dupliquen lo beneficios cuando estos son editados.
		for (InformeMensualDetalleBeneficio imdBeneficio : imDetalle.getBeneficios()) {
			if(imdBeneficio.getBeneficio().getId().equals(beneficioCliente.getId())) {
				return null;
			}
		}
		
		
		if(beneficioCliente.getVigente()) {
			
			informeBeneficio = new InformeMensualDetalleBeneficio();
			informeBeneficio.setBeneficio(beneficioCliente);
			
			if(valorInformePrevio != null) {
				informeBeneficio.setValor(valorInformePrevio);
				
			}else if(beneficioCliente.getTipo().equals(TipoBeneficioEnum.PESO)) {
				//si el tipo es peso.. simplementa se agrega el valor al beneficio en el detalle
				informeBeneficio.setValor(beneficioCliente.getValor().doubleValue());								
			}else {
				//si el tipo es porcentaje.. se aplica el mismo al sueldo del empleado (basico + presentismo)
				Sueldo sueldo = imDetalle.getRelacionLaboral().getEmpleado().getSueldoVigente();
				if(sueldo == null) {
					informeBeneficio.setValor(0d);
				}else {
					informeBeneficio.setValor((sueldo.getBasico() + sueldo.getPresentismo()) * beneficioCliente.getValor() / 100);
				}
			}
		}
		return informeBeneficio;
	}

}
