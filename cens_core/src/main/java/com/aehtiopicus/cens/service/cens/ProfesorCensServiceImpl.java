package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Profesor;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ProfesorCensRepository;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class ProfesorCensServiceImpl implements ProfesorCensService{

	@Autowired
	private ProfesorCensRepository profesorCensRepository;
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.PROFESOR;

	@Override
	public Profesor saveProfesor(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Profesor");
		}
		
		Profesor p = getProfesor(miembroCens);
		if(p == null || p.getBaja()){
			if(p==null){
				p = new Profesor();
			}
			p.setMiembroCens(miembroCens);
		}else if (p.getBaja()){
			p.setBaja(false);
		}else{
			return p;
		}
		return profesorCensRepository.save(p);

	}
	
	@Override
	public Profesor getProfesor(MiembroCens usuario){
		Profesor p = null;
		if(usuario != null){
			return profesorCensRepository.findOneByMiembroCens(usuario);
		}
		return p;
	}

	@Override
	public void deleteProfesor(MiembroCens miembroCens) {
		profesorCensRepository.markAsesorAsDisable(miembroCens);
		
	}
	
	@Override
	public List<>
}
