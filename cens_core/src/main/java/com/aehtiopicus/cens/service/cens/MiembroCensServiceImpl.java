package com.aehtiopicus.cens.service.cens;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
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

	@Autowired
	private AsesorCensService asesorCensService;

	@Transactional
	@Override
	public MiembroCens saveMiembroSens(MiembroCens miembroCens,
			Usuarios usuario, List<PerfilTrabajadorCensType> perfilTypeList)
			throws CensException {
		if (miembroCens != null) {
			usuario = usuarioCensService.saveUsuario(usuario);
			usuario.setPerfil(perfilCensService.addPerfilesToUsuarios(
					perfilTypeList, usuario));
			miembroCens.setUsusario(usuario);
			miembroCens = miembroCensRepository.save(miembroCens);
			assignRolToMiembro(miembroCens);
			return miembroCens;
		} else {
			throw new CensException("El usuario miembro no puede ser nulo");
		}
	}

	@Override
	@Transactional
	public void assignRolToMiembro(MiembroCens miembroCens)throws CensException {

		if (CollectionUtils.isNotEmpty(miembroCens.getUsusario().getPerfil())) {
			for (Perfil p : miembroCens.getUsusario().getPerfil()) {
				switch (p.getPerfilType()) {
				case ASESOR:
					asesorCensService.saveAsesor(miembroCens);
					break;
				case PRECEPTOR:
					throw new CensException("preceptor no implementado");					
				case PROFESOR:
					throw new CensException("profesor no implementado");					
				default:
					break;
				}
			}
		}
	}
}
