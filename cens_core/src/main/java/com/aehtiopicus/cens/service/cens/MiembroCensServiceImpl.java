package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.repository.cens.MiembroCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class MiembroCensServiceImpl implements MiembroCensService {

	@Autowired
	private UsuarioCensService usuarioCensService;

	@Autowired
	private PerfilCensService perfilCensService;
	
	@Autowired
	private RolCensService rolCensService;

	@Autowired
	private MiembroCensRepository miembroCensRepository;

	

	@Transactional
	@Override
	public List<MiembroCens> saveMiembroSens(List<MiembroCens> miembroCensList)
			throws CensException {		
		if (CollectionUtils.isNotEmpty(miembroCensList)) {
			List<MiembroCens> miembroCensListResult = new ArrayList<MiembroCens>();
			for(MiembroCens  miembroCens:miembroCensList){
				List<Perfil> perfilList = miembroCens.getUsuario().getPerfil();
				miembroCens.setUsuario(usuarioCensService.saveUsuario(miembroCens.getUsuario()));
				miembroCens.getUsuario().setPerfil(perfilList);
				perfilCensService.addPerfilesToUsuarios(miembroCens.getUsuario());
				miembroCens.getUsuario().setPerfil(perfilCensService.listPerfilFromUsuario(miembroCens.getUsuario()));				
				miembroCens = miembroCensRepository.save(miembroCens);	
				rolCensService.assignRolToMiembro(miembroCens);
				miembroCensListResult.add(miembroCens);
			}
			return miembroCensListResult;
		} else {
			throw new CensException("El Usuario miembro no puede ser nulo");
		}
	}

	
	
	@Override
	public MiembroCens searchMiembroCensByUsuario(Usuarios usuario){
		MiembroCens miembroCens = null;
		if(usuario != null){
			miembroCens = miembroCensRepository.findOneByUsuario(usuario);
		}
		return miembroCens;
	}



	@Override
	public List<MiembroCens> listMiembrosCens() {
		
		return miembroCensRepository.findAll();
		
	}
	
	@Override
	public MiembroCens getMiembroCens(Long id){
		
		return miembroCensRepository.findOne(id);
	}
	
}
