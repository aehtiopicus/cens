package com.aehtiopicus.cens.aspect.cens;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.aspect.cens.mappers.CambioEstadoFeedMapper;
import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.domain.entities.MaterialDidactico;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.service.cens.CambioEstadoCensFeedService;
import com.aehtiopicus.cens.service.cens.ComentarioCensFeedService;
import com.aehtiopicus.cens.service.cens.MiembroCensService;
import com.aehtiopicus.cens.utils.CensException;

@Component
@Aspect
@Order(value=600)
public class CambioEstadoAspect {

	private static final Logger logger = LoggerFactory
			.getLogger(CambioEstadoAspect.class);
	
	@Autowired
	private CambioEstadoFeedMapper mapper;
	
	@Autowired
	private ComentarioCensFeedService censFeedService;
	
	@Autowired
	private CambioEstadoCensFeedService cambioEstadoCensService;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.updateProgramaStatus(..))")
	public void programaEstadoChange(JoinPoint joinPoint){
		try{
			Programa programa = (Programa)joinPoint.getArgs()[0];
			EstadoRevisionType estadoRevision = (EstadoRevisionType)joinPoint.getArgs()[1];
			CambioEstadoCensFeed cambioEstadoFeed = mapper.convertProgramaToCambioEstadoFeed(programa,estadoRevision);
			if(cambioEstadoFeed.getActivityFeed().getToId()==null){
				List<Long> miembroAsesorIdList = censFeedService.getAsesoresIdExcludingCaller(cambioEstadoFeed.getActivityFeed().getFromId());
				for(Long ids : miembroAsesorIdList){
					CambioEstadoCensFeed aux = new CambioEstadoCensFeed();
					cambioEstadoFeed.getActivityFeed().setToId(ids);
					aux.setActivityFeed(cambioEstadoFeed.getActivityFeed());					
					aux.setEstadoRevisionType(cambioEstadoFeed.getEstadoRevisionType());
					aux.setEstadoRevisionTypeViejo(cambioEstadoFeed.getEstadoRevisionTypeViejo());
					aux.setTipoId(cambioEstadoFeed.getTipoId());
					cambioEstadoCensService.save(aux);
				}
			}else{
				cambioEstadoCensService.save(cambioEstadoFeed);
			}
		}catch(CensException e){
			logger.error("Cambio Estado Feed Error ", e);
		}
	}
	
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.updateMaterialDidacticoStatus(..))")
	public void materialDidacticoEstadoChange(JoinPoint joinPoint){
		try{
			MaterialDidactico md = (MaterialDidactico)joinPoint.getArgs()[0];
			EstadoRevisionType estadoRevision = (EstadoRevisionType)joinPoint.getArgs()[1];
			CambioEstadoCensFeed cambioEstadoFeed = mapper.convertMaterialDidacticoToCambioEstadoFeed(md,estadoRevision);
			if(cambioEstadoFeed.getActivityFeed().getToId()==null){
				List<Long> miembroAsesorIdList = censFeedService.getAsesoresIdExcludingCaller(cambioEstadoFeed.getActivityFeed().getFromId());
				for(Long ids : miembroAsesorIdList){
					CambioEstadoCensFeed aux = new CambioEstadoCensFeed();
					cambioEstadoFeed.getActivityFeed().setToId(ids);
					aux.setActivityFeed(cambioEstadoFeed.getActivityFeed());
					aux.setEstadoRevisionType(cambioEstadoFeed.getEstadoRevisionType());
					aux.setEstadoRevisionTypeViejo(cambioEstadoFeed.getEstadoRevisionTypeViejo());
					aux.setTipoId(cambioEstadoFeed.getTipoId());
					cambioEstadoCensService.save(aux);
				}
			}else{
				cambioEstadoCensService.save(cambioEstadoFeed);
			}
		}catch(CensException e){
			logger.error("Cambio Estado Feed Error ", e);
		}
	}
	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.findById(..))", returning = "programa")
	public void markCambioEstadoProgramaLeido(Programa programa) throws CensException{
		MiembroCens mc = miembroCensService.getMiembroCensByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		cambioEstadoCensService.markCambioEstadoFeedAsRead(programa.getId(),mc.getId(),ComentarioType.PROGRAMA);
	}
	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.findById(..))", returning = "material")
	public void markCambioEstadoMaterialLeido(MaterialDidactico material) throws CensException{
		MiembroCens mc = miembroCensService.getMiembroCensByUsername(((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		cambioEstadoCensService.markCambioEstadoFeedAsRead(material.getId(),mc.getId(),ComentarioType.MATERIAL);
	}
}
