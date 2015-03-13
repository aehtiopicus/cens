package com.aehtiopicus.cens.aspect.cens;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;

@Component
@Aspect
public class CacheAspect {

	@Autowired
	private CacheManager cacheManager;

	private static final String CURS_PROFESOR_CACHE = "#{cacheProperties['curso_profesor']}";
	
	private static final String PROGRAMA_PROFESOR_CACHE = "#{cacheProperties['programa_profesor']}";
	
	private static final String PROFESOR_DASHBOARD_MAPPER_CACHE = "#{cacheProperties['profesor_dashboard_mapper']}";
	
	private static final String CURSO_ASESOR_CACHE = "#{cacheProperties['curso_asesor']}";

	private static final String PROGRAMA_ASESOR_CACHE = "#{cacheProperties['programa_asesor']}";
	
	private static final String ASESOR_DASHBOARD_MAPPER_CACHE = "#{cacheProperties['asesor_dashboard_mapper']}";

	private static final String COMMENT_SOURCE_CACHE = "#{cacheProperties['comment_source']}";
	
	@Value(COMMENT_SOURCE_CACHE)
	private String comentarioCache;
	
	@Value(CURSO_ASESOR_CACHE)
	private String cursoAsesor;
	
	@Value(PROGRAMA_ASESOR_CACHE)
	private String programaAsesor;
	
	@Value(ASESOR_DASHBOARD_MAPPER_CACHE)
	private String asesorDashboardMapper;
	
	@Value(PROFESOR_DASHBOARD_MAPPER_CACHE)
	private String profesorDashboardMapper;


	@Value(CURS_PROFESOR_CACHE)
	private String cursoProfesor;
	

	@Value(PROGRAMA_PROFESOR_CACHE)
	private String programaProfesor;

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.removeProfesorFromAsignaturas(..))")
	public void updateCursoCacheRemove() {
		cleanCursoProfesor();
	}

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.deleteAsignatura(..))")
	public void deleteCursoCacheSave() {
		cleanCursoProfesor();
	}

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.saveAsignaturas(..))")
	public void updateCursoCacheSave() {
		cleanCursoProfesor();
	}
	
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.savePrograma(..))")
	public void updateProgramaCacheRemove() {
		cleanProgramaProfesor();
	}

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.removePrograma(..))")
	public void deleteProgramaCacheSave() {
		cleanProgramaProfesor();
	}

	@After(value = "execution(* com.aehtiopicus.cens.service.cens.ProgramaCensService.updateProgramaStatus(..))")
	public void updateProgramaCacheSave() {
		cleanProgramaProfesor();
	}
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.saveMaterialDidactico(..))")
	public void saveMaterialCacheSave() {
		cleanProgramaProfesor();
	}
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.removeMaterialDidactico(..))")
	public void removeMaterialCacheSave() {
		cleanProgramaProfesor();
	}
	
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.updateMaterialDidacticoStatus(..))")
	public void updateMaterialCacheSave() {
		cleanProgramaProfesor();
	}
	
	
	@After(value = "execution(* com.aehtiopicus.cens.service.cens.MaterialDidacticoCensService.removeMaterialDidacticoCompleto(..))")
	public void removeMaterialCompletoCacheSave() {
		cleanProgramaProfesor();
	}

	
	@AfterReturning(pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensFeedService.save(..))",returning = "ccf")
	public void removeComentarioCache(JoinPoint joinPoint, ComentarioCensFeed ccf){
		if(cacheManager.getCache(comentarioCache)!=null){
			Cache c = cacheManager.getCache(comentarioCache);
			ComentarioTypeComentarioIdKey ctcik = new ComentarioTypeComentarioIdKey(ccf.getComentarioType(), ccf.getComentarioCensId(),ccf.getActivityFeed().getDateCreated());
			if(c.get(ctcik)!=null){
				c.evict(ctcik);
			}
			
		}
	}

	private void cleanCursoProfesor() {
		cacheManager.getCache(cursoProfesor).clear();
		cacheManager.getCache(cursoAsesor).clear();
		cleanProfesorMapperCache();
		cleanAsesorMapperCache();
	}
	
	private void cleanProgramaProfesor() {
		cacheManager.getCache(programaProfesor).clear();
		cacheManager.getCache(programaAsesor).clear();
		cleanProfesorMapperCache();
		cleanAsesorMapperCache();
	}
	
	
	private void cleanProfesorMapperCache(){
		cacheManager.getCache(profesorDashboardMapper).clear();
	}
	
	private void cleanAsesorMapperCache(){
		cacheManager.getCache(asesorDashboardMapper).clear();
	}
	
}
