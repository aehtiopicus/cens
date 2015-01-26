package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Asesor;
import com.aehtiopicus.cens.domain.entities.Curso;
import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.AsesorCensRepository;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class AsesorCensServiceImpl implements AsesorCensService{

	@Autowired
	private AsesorCensRepository asesorCensRepository;
	
	@Autowired
	private CursoCensService cursoCensService;
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.ASESOR;

	@Override
	public Asesor saveAsesor(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Asesor");
		}
		
		Asesor a = getAsesor(miembroCens);
		if(a == null ){
			a = new Asesor();
			a.setMiembroCens(miembroCens);
		}else if (a.getBaja()){
			a.setBaja(false);
		}else{
			return a;
		}
		return asesorCensRepository.save(a);

	}
	
	@Override
	public Asesor getAsesor(MiembroCens usuario){
		Asesor a = null;
		if(usuario != null){
			return asesorCensRepository.findOneByMiembroCens(usuario);
		}
		return a;
	}

	@Override
	public void deleteAsesor(MiembroCens miembroCens) {
		asesorCensRepository.markAsesorAsDisable(miembroCens);
		
	}

	@Override
	public Asesor findById(Long asesorId) {
		return asesorCensRepository.findOne(asesorId);
	}

	@Override
	public List<Curso> listCursos() {
		return cursoCensService.listCursoAsignatura();
	}
	
	
}
