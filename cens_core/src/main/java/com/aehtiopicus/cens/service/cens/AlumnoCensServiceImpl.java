package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Alumno;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.AlumnoCensRepository;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class AlumnoCensServiceImpl implements AlumnoCensService{

	@Autowired
	private AlumnoCensRepository alumnoCensRepository;
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.ALUMNO;

	@Override
	public Alumno saveAlumno(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Alumno");
		}
		
		Alumno p = getAlumno(miembroCens);
		if(p == null || p.getBaja()){
			if(p==null){
				p = new Alumno();
			}
			p.setMiembroCens(miembroCens);
		}else if (p.getBaja()){
			p.setBaja(false);
		}else{
			return p;
		}
		return alumnoCensRepository.save(p);

	}
	
	@Override
	public Alumno getAlumno(MiembroCens usuario){
		Alumno p = null;
		if(usuario != null){
			return alumnoCensRepository.findOneByMiembroCens(usuario);
		}
		return p;
	}

	@Override
	public void deleteAlumno(MiembroCens miembroCens) {
		alumnoCensRepository.markAsesorAsDisable(miembroCens);
		
	}
}
