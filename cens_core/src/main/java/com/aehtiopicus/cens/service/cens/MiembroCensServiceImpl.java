package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.MiembroCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class MiembroCensServiceImpl implements MiembroCensService {
	@Autowired
	private UsuarioCensService usuarioCensService;
	@Autowired
	private PerfilCensService perfilCensService;
	@Autowired
	private MiembroCensRepository miembroCensRepository;

	@Transactional
	@Override
	public MiembroCens saveMiembroSens(MiembroCens miembroCens,
			Usuarios usuario, List<PerfilTrabajadorCensType> perfilTypeList)
			throws CensException {
		if(miembroCens!=null){
			usuario = usuarioCensService.saveUsuario(usuario);
			usuario.setPerfil(perfilCensService.addPerfilesToUsuarios(perfilTypeList,usuario));
			miembroCens.setUsusario(usuario);
			return miembroCensRepository.save(miembroCens);
		}else{
			throw new CensException("El usuario miembro no puede ser nulo");
		}
	}
}
