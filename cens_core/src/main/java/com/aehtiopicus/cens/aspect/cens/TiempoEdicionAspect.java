package com.aehtiopicus.cens.aspect.cens;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Asignatura;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.service.cens.TiempoEdicionCensService;
import com.aehtiopicus.cens.utils.CensException;

@Component
@Aspect
@Order(value = 1200)
public class TiempoEdicionAspect {

	@Autowired
	private TiempoEdicionCensService tiempoEdicionService;

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.updateMaterialDidacticoStatus(..))")
	public void materialDidacticoEstadoChange(JoinPoint joinPoint) throws CensException {
		MaterialDidactico md = (MaterialDidactico) joinPoint.getArgs()[0];
		tiempoEdicionService.invalidateEntries(md.getId());
	}

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.updateProgramaStatus(..))")
	public void programaEstadoChange(JoinPoint joinPoint) throws CensException {
		Programa md = (Programa) joinPoint.getArgs()[0];
		tiempoEdicionService.invalidateEntries(md.getId());
	}

	@AfterReturning(value = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.saveAsignaturas(..))", returning = "asignaturaList")
	public void asignaturaChange(JoinPoint joinPoint, List<Asignatura> asignaturaList) throws CensException {
		if (CollectionUtils.isNotEmpty(asignaturaList)) {
			for (Asignatura md : asignaturaList) {
				if (md.getNotificado() == Boolean.FALSE) {
					tiempoEdicionService.invalidateEntries(md.getId());
				}
			}
		}
	}

	@AfterReturning(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.savePrograma(..))", returning = "programa")
	public void programaUpdate(JoinPoint joinPoint, Programa programa) throws CensException {

		if (programa.getDocumentoModificado().getNotificado() == Boolean.FALSE) {
			tiempoEdicionService.invalidateEntries(programa.getId());
		}

	}
	
	@AfterReturning(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.saveMaterialDidactico(..))", returning = "material")
	public void materialUpdate(JoinPoint joinPoint, MaterialDidactico material) throws CensException {

		if (material.getDocumentoModificado().getNotificado() == Boolean.FALSE) {
			tiempoEdicionService.invalidateEntries(material.getId());
		}

	}
}
