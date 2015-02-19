package com.aehtiopicus.cens.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.aehtiopicus.cens.service.cens.LoginCensService;

@Configuration
@EnableWebSecurity 
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
  	@Qualifier("censDataSource")
  	private DataSource dataSource;
	
	@Autowired
	private LoginCensService loginCensService;
  	
      
	@Autowired
  	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  	    auth.userDetailsService(loginCensService);
//  	  .passwordEncoder( new ShaPasswordEncoder() );  Md5PasswordEncoder encoder = new Md5PasswordEncoder(); 	            
  	}
		
		@Override
        protected void configure(HttpSecurity http) throws Exception {
        	 http.authorizeRequests()        	 	
     			.antMatchers("/css/**").permitAll()
     			.antMatchers("/js/**").permitAll()
     			.antMatchers("/mvc/**").access("hasRole('ROLE_ASESOR') or hasRole('ROLE_ADMINISTRADOR') or hasRole('ROLE_PROFESOR') or hasRole('ROLE_PRECEPTOR')")
     			.anyRequest().authenticated()
     			.and()     			
     				.formLogin()
     				.loginPage("/login")
     				.loginProcessingUrl("/j_spring_security_check")
     				.usernameParameter("j_username")
     				.passwordParameter("j_password") 
     				.failureUrl("/loginfailed")
     				.defaultSuccessUrl("/mvc/main")
     				.permitAll()
     				    			
     			.and()
     				.logout().logoutSuccessUrl("/login")
     				.permitAll()
     		 	.and()
     		 		.exceptionHandling().accessDeniedPage("/errors/401")     		 		
     		 	.and()
     		 		.csrf()
     		 		.disable();
     	
        }
        
        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception
        {
            return super.authenticationManagerBean();
        }

    
}
