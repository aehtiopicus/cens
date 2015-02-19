package com.aehtiopicus.cens.aspect.cens;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.aspect.cens.mappers.ComentarioCensFeedMapper;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.ComentarioCensFeedService;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;

@Component
@Aspect
public class ComentarioAspect {

	@Autowired
	private ComentarioCensFeedService censFeedService;
	
	@Autowired
	private ProgramaCensService programaCensService;
	
	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;
	
	@Autowired
	private ComentarioCensFeedMapper mapper;
	
	@AfterReturning(
			pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.saveComentario(..))", 
			returning = "comentario")
	public void saveNotification(JoinPoint joinPoint, ComentarioCens comentario){
		Long originalInitializerId = null;
		PerfilTrabajadorCensType ptct = null;
		switch (comentario.getTipoComentario()){
		case MATERIAL:
			originalInitializerId = materialDidacticoCensService.findById(comentario.getTipoId()).getId();
			ptct = PerfilTrabajadorCensType.PROFESOR;
			break;
		case PROGRAMA:
			originalInitializerId =programaCensService.findById(comentario.getTipoId()).getId();
			ptct = PerfilTrabajadorCensType.PROFESOR;
			break;
		default:
			break;
		
		}
	}
}
