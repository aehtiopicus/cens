package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.SchedulerJobs;
import com.aehtiopicus.cens.repository.cens.MiembroCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class LoginCensServiceImpl implements LoginCensService{
	
	@Autowired
	private MiembroCensRepository repository;
	@Autowired
	private SchedulerService scheduler;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		MiembroCens mc = repository.findByUsername(username);
		SchedulerJobs job = new SchedulerJobs();
		job.setJobName("token_fb");
		job.setEnabled(true);		
		try {
			scheduler.scheduleJobs(job);
			scheduler.unScheduleJob(job.getJobName());
		} catch (CensException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( mc == null || mc.getBaja()){
			throw new UsernameNotFoundException( "No existe el usuario" );
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for(Perfil p :mc.getUsuario().getPerfil()){
			authorities.add( new SimpleGrantedAuthority(p.getPerfilType().getNombre()));
		}
				

		return new User( mc.getUsuario().getUsername(), mc.getUsuario().getPassword(), authorities );
	}

}
