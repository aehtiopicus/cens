package com.aehtiopicus.cens.service.cens;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class RolCensServiceImpl implements RolCensService {

	@Autowired
	private AsesorCensService asesorCensService;

	@Autowired
	private ProfesorCensService profesorCensService;

	@Autowired
	private PreceptorCensService preceptorCensService;

	@Autowired
	private AlumnoCensService alumnoCensService;

	@Autowired
	private MiembroCensService miembroCensService;

	@Override
	@Transactional
	public void assignRolToMiembro(MiembroCens miembroCens)
			throws CensException {

		if (CollectionUtils.isNotEmpty(miembroCens.getUsuario().getPerfil())) {
			for (Perfil p : miembroCens.getUsuario().getPerfil()) {
				switch (p.getPerfilType()) {
				case ASESOR:
					asesorCensService.saveAsesor(miembroCens);
					break;
				case PRECEPTOR:
					preceptorCensService.savePreceptor(miembroCens);
					break;
				case PROFESOR:
					profesorCensService.saveProfesor(miembroCens);
					break;
				case ALUMNO:
					alumnoCensService.saveAlumno(miembroCens);
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public void removeRolToMiembro(PerfilTrabajadorCensType perfil,
			Usuarios usuario) {
		MiembroCens miembroCens = miembroCensService
				.searchMiembroCensByUsuario(usuario);
		if (miembroCens != null) {
			switch (perfil) {
			case ASESOR:
				asesorCensService.deleteAsesor(miembroCens);
				break;
			case PRECEPTOR:
				preceptorCensService.deletePreceptor(miembroCens);
				break;
			case PROFESOR:
				profesorCensService.deleteProfesor(miembroCens);
				break;
			case ALUMNO:
				alumnoCensService.deleteAlumno(miembroCens);
				break;
			default:
				break;
			}
		}
	}
}
