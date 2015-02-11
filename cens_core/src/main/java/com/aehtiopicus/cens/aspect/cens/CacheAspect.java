package com.aehtiopicus.cens.aspect.cens;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;

@Aspect
public class CacheAspect {

	@Autowired
	private CacheManager cacheManager;
	
	private static final String CURS_PROFESOR_CACHE = "#{cacheProperties['curso_profesor']}";
	
	@Value(CURS_PROFESOR_CACHE)
	private String cursoProfesor;
	
	@AfterReturning(
			pointcut = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.removeProfesorFromAsignaturas(..))")
	public void updateCursoCacheRemove(){
		cleanCursoProfesor();		
	}
	@AfterReturning(
			pointcut = "execution(* com.aehtiopicus.cens.service.cens.AsignaturaCensService.saveAsignaturas(..))")
	public void updateCursoCacheSave(){
		cleanCursoProfesor();
	}
	
	private void cleanCursoProfesor(){
		cacheManager.getCache(cursoProfesor).clear();	
	}
}
