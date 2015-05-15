package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.MiembroCens;
import com.aehtiopicus.cens.domain.entities.Perfil;
import com.aehtiopicus.cens.domain.entities.PerfilRol;
import com.aehtiopicus.cens.domain.entities.Usuarios;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
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
	
	@PersistenceContext
	private EntityManager entityManager;

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
			Usuarios usuario) throws CensException{
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

	@Override
	public List<PerfilTrabajadorCensType> listPerfil() {
		List<PerfilTrabajadorCensType> list =new  ArrayList<PerfilTrabajadorCensType>();
		for(PerfilTrabajadorCensType ptct : PerfilTrabajadorCensType.values()){
			if(!ptct.equals(PerfilTrabajadorCensType.ADMINISTRADOR)){
				list.add(ptct);
			}
		}
		return list;
	}

	@Override
	public PerfilTrabajadorCensType getPerfilByName(String perfilName) {
		return PerfilTrabajadorCensType.getPrefilByName(perfilName);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Cacheable("users")
	public List<PerfilRol> getPerfilIdBasedOnUsuario(
			String nombreUsuario) {
		Query q =entityManager.createNativeQuery("select  CASE WHEN cpuc.perfilType = 'ROLE_ASESOR' THEN (SELECT ca.id FROM cens_asesor ca where ca.miembrocens_id = cm.id AND ca.baja = false) "
				+ "WHEN cpuc.perfilType = 'ROLE_PROFESOR' THEN (SELECT cpp.id FROM cens_profesor cpp where cpp.miembrocens_id = cm.id AND cpp.baja = false) "
				+ "WHEN cpuc.perfilType = 'ROLE_PRECEPTOR' THEN (SELECT cppp.id FROM cens_preceptor cppp where cppp.miembrocens_id = cm.id AND cppp.baja = false) "
				+ "WHEN cpuc.perfilType = 'ROLE_ALUMNO' THEN (SELECT caa.id FROM cens_alumno caa where caa.miembrocens_id = cm.id AND caa.baja = false) " 
				+ "WHEN cpuc.perfilType = 'ROLE_ADMINISTRADOR' THEN -1 "
				+ "END typeId,cpuc.perfiltype as perfil "
				+ "from cens_miembros_cens cm " 
				+ "inner join cens_usuarios cu on cm.usuario_id = cu.id "
				+"inner join cens_perfil_usuario_cens cpuc on cu.id = cpuc.usuario_id "
				+"WHERE cu.username = :username").setParameter("username", nombreUsuario);
		List<Object[]> result =q.getResultList();
		
		List<PerfilRol> prList = new java.util.ArrayList<PerfilRol>();

		PerfilRol pr = null;
		if(CollectionUtils.isNotEmpty(result)){
			for(Object [] data : result){
				pr = new PerfilRol();
				pr.setPerfilId(((java.math.BigInteger)data[0]).longValue());
				pr.setPerfilType(String.valueOf(data[1]));
				prList.add(pr);
			}
		}

		return prList;
	}
	
	
}

