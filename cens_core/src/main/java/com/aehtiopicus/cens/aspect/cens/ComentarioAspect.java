package com.aehtiopicus.cens.aspect.cens;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.aspect.cens.mappers.ComentarioCensFeedMapper;
import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.service.cens.ComentarioCensFeedService;
import com.aehtiopicus.cens.service.cens.ComentarioCensFeedServiceImpl;
import com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.service.cens.ProgramaCensService;
import com.aehtiopicus.cens.utils.CensException;

@Component
@Aspect
@Order(value=600)
public class ComentarioAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(ComentarioAspect.class);
	@Autowired
	private ComentarioCensFeedServiceImpl censFeedService;

	@Autowired
	private ProgramaCensService programaCensService;

	@Autowired
	private MaterialDidacticoCensService materialDidacticoCensService;

	@Autowired
	private ComentarioCensFeedMapper mapper;
	
	@Autowired
	private MiembroCensService miembroCensService;
	

	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.delete(..))", returning ="comentarioIds")
	public void deleteNotification(JoinPoint joinPoint, List<Long> comentarioIds){
		try{
			censFeedService.deleteAllComentarios(comentarioIds);
		}catch(CensException e){
			logger.error("Bulk deletion error",e);
		}
		
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
			ccf.setTipoId(comentarioCens.getTipoId());
			
			//broadcast
			if(ccf.getActivityFeed().getToId()==null){
				
				List<Long> miembroAsesorIdList = censFeedService.getAsesoresIdExcludingCaller(ccf.getActivityFeed().getFromId());
				if(CollectionUtils.isNotEmpty(miembroAsesorIdList)){
					for(Long miembroAsesorId : miembroAsesorIdList){
						ccf.getActivityFeed().setToId(miembroAsesorId);
						ComentarioCensFeed ccfAux = new ComentarioCensFeed();
						ccfAux.setActivityFeed(ccf.getActivityFeed());
						ccfAux.setComentarioCensId(ccf.getComentarioCensId());
						ccfAux.setId(ccf.getId());
						ccfAux.setTipoId(ccf.getTipoId());

						censFeedService.save(ccfAux);
					}
				}else{
					logger.warn("Descartando feed por ser de un asesor a si mismo");
				}
				//self message
			}else if(ccf.getActivityFeed().getToId().equals(ccf.getActivityFeed().getFromId())){
					return;
			}else{
				censFeedService.save(ccf);
			}
		} catch (Exception e) {
			logger.error("Comentario Feed Error ", e);
		}
	}
	
	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.findAllParentcomments(..))", returning = "comentarioList")
	public void markCommentsAsRead(JoinPoint joinPoint,
			 List<ComentarioCens> comentarioList) {
		try{
			if(CollectionUtils.isNotEmpty(comentarioList)){
				MiembroCens mc = miembroCensService.getMiembroCensByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
				censFeedService.markAllFeedsFromCommentsAsRead(mc.getId(),comentarioList);
			}
		}catch(Exception e){
			logger.error("Error al marcar comentarios como leidos",e);
		}
	}
	
}
