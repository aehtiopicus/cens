package com.aehtiopicus.cens.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.aehtiopicus.cens.controller.cens.mvc.AbstractControllerInterceptor;
import com.aehtiopicus.cens.util.Utils;
import com.aehtiopicus.profiles.Production;

@Configuration
@EnableWebMvc
@ComponentScan("com.aehtiopicus.cens")
@Production

@ImportResource("classpath:/cens-properties.xml")
@Import({ SpringSecurityConfig.class })
public class AnnotationDrivenConfigProduction extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(gsonCustomConverter());

	}

	/**
	 * Since spring 4.1 jackson is replaced by gson then dates should be handle
	 * 
	 * @return GsonHttpMessageConverter
	 */
	@Bean
	public GsonHttpMessageConverter gsonCustomConverter() {
		GsonHttpMessageConverter ghmc = new GsonHttpMessageConverter();
		ghmc.setGson(Utils.getSon());
		return ghmc;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver vr = new InternalResourceViewResolver();
		vr.setPrefix("/WEB-INF/views/");
		vr.setSuffix(".jsp");
		return vr;
	}

	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setMaxUploadSize(1 * 1024 * 1024 * 1024);// 1gb??
		return cmr;
	}

	@Bean
	public AbstractControllerInterceptor localeInterceptor() {
		return new AbstractControllerInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(localeInterceptor()).
		addPathPatterns("/mvc/profesor/**").
		addPathPatterns("/mvc/asignatura/**").
		addPathPatterns("/mvc/programa/**").
		addPathPatterns("/mvc/asesor/**");		
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
}
