package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
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
	public MiembroCens saveMiembroSens(MiembroCens miembroCens)
			throws CensException {
		if (miembroCens != null) {
			miembroCens.setUsuario(usuarioCensService.saveUsuario(miembroCens.getUsuario()));
			perfilCensService.addPerfilesToUsuarios(miembroCens.getUsuario());
			miembroCens.getUsuario().setPerfil(perfilCensService.listPerfilFromUsuario(miembroCens.getUsuario()));
			miembroCens.setUsuario(miembroCens.getUsuario());
			miembroCens = miembroCensRepository.save(miembroCens);	
			rolCensService.assignRolToMiembro(miembroCens);
			return miembroCens;
		} else {
			throw new CensException("El usuario miembro no puede ser nulo");
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
	
}
