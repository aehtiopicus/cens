package com.aehtiopicus.cens.configuration;

import javax.servlet.Filter;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;




public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	 
		@Override
		protected Class<?>[] getRootConfigClasses() {
			 AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
			 Class<?> c;
			 if(isDevelop(ctx)){
				 c = AnnotationDrivenConfigDevelopment.class;
		     }else{
		        c = AnnotationDrivenConfigProduction.class;
		       }	
			 return new Class[]{c};
		}
	 
		@Override
		protected Class<?>[] getServletConfigClasses() {
			return null;
		}
	 
		@Override
		protected String[] getServletMappings() {
			return new String[] { "/" };
		}
	 
/*
 * Adding sitemesh as servlet filtering to avoid preloading of it
 **/
		@Override
	    protected Filter[] getServletFilters() {
	        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	        characterEncodingFilter.setEncoding("UTF-8");
	        return new Filter[]{ characterEncodingFilter, new SiteMeshFilter() };
	    }
	
	private boolean isDevelop(AnnotationConfigWebApplicationContext ctx){
		for(String prof : ctx.getEnvironment().getActiveProfiles()){
			if(prof.equals("development")){
				return true;				
			}
		}
		return false;
	}
}
