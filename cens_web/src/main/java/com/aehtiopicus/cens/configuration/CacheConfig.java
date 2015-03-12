package com.aehtiopicus.cens.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

	private static final String CURSO_PROFESOR_CACHE = "#{cacheProperties['curso_profesor']}";

	private static final String PROGRAMA_PROFESOR_CACHE = "#{cacheProperties['programa_profesor']}";
	
	private static final String PROFESOR_DASHBOARD_MAPPER_CACHE = "#{cacheProperties['profesor_dashboard_mapper']}";
	
	private static final String CURSO_ASESOR_CACHE = "#{cacheProperties['curso_asesor']}";

	private static final String PROGRAMA_ASESOR_CACHE = "#{cacheProperties['programa_asesor']}";
	
	private static final String ASESOR_DASHBOARD_MAPPER_CACHE = "#{cacheProperties['asesor_dashboard_mapper']}";

	private static final String COMMENT_SOURCE_CACHE = "#{cacheProperties['comment_source']}";
	
	@Value(CURSO_ASESOR_CACHE)
	private String cursoAsesor;
	
	@Value(PROGRAMA_ASESOR_CACHE)
	private String programaAsesor;
	
	@Value(ASESOR_DASHBOARD_MAPPER_CACHE)
	private String asesorDashboardMapper;
	
	@Value(CURSO_PROFESOR_CACHE)
	private String cursoProfesor;
	
	@Value(PROGRAMA_PROFESOR_CACHE)
	private String programaProfesor;
	
	@Value(PROFESOR_DASHBOARD_MAPPER_CACHE)
	private String profesorDashboardMapper;
	
	@Value(COMMENT_SOURCE_CACHE)
	private String comentarioCache;

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();		
		cacheManager.setCaches(Arrays.asList(
				new ConcurrentMapCache(cursoProfesor),
				new ConcurrentMapCache(programaProfesor),
				new ConcurrentMapCache(profesorDashboardMapper),
				new ConcurrentMapCache(cursoAsesor),
				new ConcurrentMapCache(programaAsesor),
				new ConcurrentMapCache(asesorDashboardMapper),
				new ConcurrentMapCache("users"),
				new ConcurrentMapCache("comentarioCache")));
		return cacheManager;

	}

}
