package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Preceptor;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.PreceptorCensRepository;
import com.aehtiopicus.cens.utils.CensException;
import com.aehtiopicus.cens.utils.Utils;

@Service
public class PreceptorCensServiceImpl implements PreceptorCensService{

	@Autowired
	private PreceptorCensRepository preceptorCensRepository;
	
	private static final PerfilTrabajadorCensType PERFIL_TYPE = PerfilTrabajadorCensType.PRECEPTOR;

	@Override
	public Preceptor savePreceptor(MiembroCens miembroCens) throws CensException{
		if(miembroCens==null  || !Utils.checkIsCensMiembro(miembroCens.getUsuario().getPerfil(), PERFIL_TYPE)){
			throw new CensException("El usuario no puede asignarse como un Preceptor");
		}
		
		Preceptor p = getPreceptor(miembroCens);
		if(p == null ){
			p = new Preceptor();
			p.setMiembroCens(miembroCens);
		}else if (p.getBaja()){
			p.setBaja(false);
		}else{
			return p;
		}
		return preceptorCensRepository.save(p);

	}
	
	@Override
	public Preceptor getPreceptor(MiembroCens usuario){
		Preceptor p = null;
		if(usuario != null){
			return preceptorCensRepository.findOneByMiembroCens(usuario);
		}
		return p;
	}

	@Override
	public void deletePreceptor(MiembroCens miembroCens) {
		preceptorCensRepository.markAsesorAsDisable(miembroCens);
		
	}
}
