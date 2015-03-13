package com.aehtiopicus.cens.aspect.cens;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.aspect.cens.mappers.ComentarioCensFeedMapper;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.ComentarioCensFeedService;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;

@Component
@Aspect
public class ComentarioAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(ComentarioAspect.class);
	@Autowired
	private ComentarioCensFeedService censFeedService;

	@Autowired
	private ProgramaCensService programaCensService;

	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;

	@Autowired
	private ComentarioCensFeedMapper mapper;

	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.delete(..))", returning ="comentarioIds")
	public void deleteNotification(JoinPoint joinPoint, List<Long> comentarioIds){
		Long comentarioDeletedId = (Long) joinPoint.getArgs()[0];
//		censFeedService.de
		
	}
	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.saveComentario(..))", returning = "comentarioCens")
	public void saveNotification(JoinPoint joinPoint,
			ComentarioCens comentarioCens) {
		try {
			Long originalInitializerId = null;
			PerfilTrabajadorCensType ptct = null;
			switch (comentarioCens.getTipoComentario()) {
			case MATERIAL:
				originalInitializerId = materialDidacticoCensService.findById(
						comentarioCens.getTipoId()).getProfesor().getMiembroCens().getId();
				ptct = PerfilTrabajadorCensType.PROFESOR;
				break;
			case PROGRAMA:
				originalInitializerId = programaCensService.findById(
						comentarioCens.getTipoId()).getProfesor().getMiembroCens().getId();
				ptct = PerfilTrabajadorCensType.PROFESOR;
				break;
			default:
				break;

			}
			ComentarioCensFeed ccf = mapper.convertComentarioToFeed(comentarioCens,
					originalInitializerId, ptct);
			//broadcast
			if(ccf.getActivityFeed().getToId()==null){
				
				List<Long> miembroAsesorIdList = censFeedService.getAsesoresIdExcludingCaller(ccf.getActivityFeed().getFromId());
				if(CollectionUtils.isNotEmpty(miembroAsesorIdList)){
					for(Long miembroAsesorId : miembroAsesorIdList){
						ccf.getActivityFeed().setToId(miembroAsesorId);
						censFeedService.save(ccf);
					}
				}else{
					logger.warn("Descartando feed por ser de un asesor a si mismo");
				}
			}else{
				censFeedService.save(ccf);
			}
		} catch (Exception e) {
			logger.error("Comentario Feed Error ", e);
		}
	}
}
