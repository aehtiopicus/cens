package com.aehtiopicus.cens.aspect.cens;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;

@Aspect
public class ComentarioAspect {

	@AfterReturning(
			pointcut = "execution(* com.aehtiopicus.cens.service.cens.ComentarioCensService.saveComentario(..))", 
			returning = "comentario")
	public void saveNotification(JoinPoint joinPoint, ComentarioCens comentario){
		
	}
}
