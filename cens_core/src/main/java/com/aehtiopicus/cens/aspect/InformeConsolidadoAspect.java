package com.aehtiopicus.cens.aspect;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;
import com.aehtiopicus.cens.service.EmpleadoService;
import com.aehtiopicus.cens.service.InformeConsolidadoService;

@Aspect
public class InformeConsolidadoAspect {
	private static final Logger logger = Logger.getLogger(InformeConsolidadoAspect.class);

	@Autowired
	private InformeConsolidadoService icService;

	@Autowired
	private EmpleadoService eService;
	
	
	public void setIcService(InformeConsolidadoService icService) {
		this.icService = icService;
	}
	public void seteService(EmpleadoService eService) {
		this.eService = eService;
	}


	@Before("execution(* com.aehtiopicus.cens.service.EmpleadoService.saveEmpleado(*)) && args(empleado)")
	public void updateCargasSocialesInInformeConsolidado(Empleado empleado) {
		
		if(empleado.getId() == null) {
			logger.info("Nuevo empleado, no se requiere actualizar informes consolidados");
			return;
		}
		
		Empleado empDB = eService.getEmpleadoByEmpleadoId(empleado.getId());
		if(empDB.getCodigoContratacion() != null && empleado.getCodigoContratacion() != null && empDB.getCodigoContratacion().equals(empleado.getCodigoContratacion())) {
			logger.info("No se ha modificado el codigo de contratacion del empleado, no se requiere actualizar informes consolidados");
			return;
		}else if(empDB.getCodigoContratacion() == null && empleado.getCodigoContratacion() == null) {
			logger.info("No se ha modificado el codigo de contratacion del empleado, no se requiere actualizar informes consolidados");
			return;
		}
		
		logger.info("Se modifico el codigo de cotratacion del empleado, se deben actualziar los informes consolidados abiertos");

		List<InformeConsolidadoDetalle> detalles = icService.getDetallesAbiertosByEmpleado(empleado.getId());
		if(CollectionUtils.isEmpty(detalles)){
			logger.info("No hay informes consolidados abiertos para el empleado modificado");
			return;
		}
		
		for (InformeConsolidadoDetalle detalle : detalles) {
			logger.info("Detalle a actualizar: " + detalle.getId());
			detalle = icService.calcularCargasSociales(detalle, empleado);
		}
		icService.updateDetalles(detalles);
		
	}
	
	
}
