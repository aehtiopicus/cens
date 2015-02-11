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

	private static final String CURS_PROFESOR_CACHE = "#{cacheProperties['curso_profesor']}";
	
	@Value(CURS_PROFESOR_CACHE)
	private String cursoProfesor;
	@Bean
	public CacheManager cacheManager() {		
		   SimpleCacheManager cacheManager = new SimpleCacheManager();
	         cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(cursoProfesor),new ConcurrentMapCache("users")));
	         return cacheManager;
		
	}
	
	
}
