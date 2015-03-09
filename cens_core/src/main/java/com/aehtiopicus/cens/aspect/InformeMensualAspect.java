package com.aehtiopicus.cens.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

//@Aspect
public class InformeMensualAspect {
//	private static final Logger logger = Logger.getLogger(InformeMensualAspect.class);
//	
//	@Autowired
//	private InformeMensualService imService;
//	
//	@Autowired
//	private ClienteService clienteService;
//	
//	public InformeMensualService getImService() {
//		return imService;
//	}
//	public void setImService(InformeMensualService imService) {
//		this.imService = imService;
//	}	
//	public ClienteService getClienteService() {
//		return clienteService;
//	}
//	public void setClienteService(ClienteService clienteService) {
//		this.clienteService = clienteService;
//	}
//	
//	
//	@AfterReturning(
//		pointcut = "execution(* com.aehtiopicus.cens.service.RelacionLaboralService.createAndSave(..))", 
//		returning = "relacionLaboral")
//	public void after_createRelacionLaboral(JoinPoint joinPoint, RelacionLaboral relacionLaboral) {
//		
//		logger.info("******");
//		logger.info("corriendo: after_createRelacionLaboral()");
//		
//		relacionLaboral.setCliente(clienteService.getClienteById(relacionLaboral.getCliente().getId()));		
//		imService.addInformeMensualDetalle(relacionLaboral);
//		
//		logger.info("******");
//	}
//	
//	@AfterReturning(
//		pointcut = "execution(* com.aehtiopicus.cens.service.RelacionLaboralService.update(..))", 
//		returning = "relacionLaboral")
//	public void after_updateRelacionLaboral(JoinPoint joinPoint, RelacionLaboral relacionLaboral) {
//		
//		logger.info("******");
//		logger.info("corriendo: after_updateRelacionLaboral()");
//		
//		imService.updateInformeMensualDetalle(relacionLaboral);
//		
//		logger.info("******");
//	}
//	
//	@AfterReturning(
//		pointcut = "execution(* com.aehtiopicus.cens.service.ClienteService.saveBeneficioCliente(..))",
//		returning = "beneficioCliente")
//	public void after_addBeneficioCliente(JoinPoint joinPoint, BeneficioCliente beneficioCliente){
//		logger.info("*******");
//		logger.info("corriendo: after_addBeneficioCliente");
//		
//		imService.addBeneficioToInformeMensualDetalles(beneficioCliente);
//		
//		logger.info("*******");
//	}
//	
//
//	@AfterReturning(
//		pointcut = "execution(* com.aehtiopicus.cens.service.ClienteService.cambiarEstadoBeneficioCliente(..))",
//		returning = "beneficioCliente")
//	public void after_updateBeneficioCliente(JoinPoint joinPoint, BeneficioCliente beneficioCliente){
//		logger.info("*******");
//		logger.info("corriendo: after_updateBeneficioCliente");
//		
//		imService.updateBeneficioInInformeMensualDetalles(beneficioCliente);
//		
//		logger.info("*******");
//	}
//
//	
//	@AfterReturning(
//		pointcut = "execution(* com.aehtiopicus.cens.service.EmpleadoService.saveSueldo(..))",
//		returning = "sueldo")
//	public void after_saveSueldo(JoinPoint joinPoint, Sueldo sueldo){
//		logger.info("*******");
//		logger.info("corriendo: after_saveSueldo");
//		
//		imService.updateSalarioInInformeMensualDetalles(sueldo);
//		
//		logger.info("*******");
//	}
	
}
