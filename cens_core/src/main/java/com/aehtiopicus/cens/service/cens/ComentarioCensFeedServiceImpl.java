package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ComentarioCensFeedRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ComentarioCensFeedServiceImpl implements ComentarioCensFeedService{
	
	public static final String COMENTARIO_CURSO ="curso";
	public static final String COMENTARIO_CURSO_YEAR ="year";
	public static final String COMENTARIO_ASIGNATURA ="asignatura";
	public static final String COMENTARIO_PROGRAMA ="programa";
	public static final String COMENTARIO_MATERIAL ="material";
	public static final String COMENTARIO_SEPARATOR =",";
	
	
	@Autowired
	private ComentarioCensFeedRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	@Override
	@Transactional(rollbackFor={CensException.class,Exception.class})
	public void save(ComentarioCensFeed comentarioFeed){
		repository.save(comentarioFeed);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<NotificacionComentarioFeed> getGeneratedFeeds(String userName) throws CensException{
		List<NotificacionComentarioFeed> ccfList = null;
		try{	
			List<Object[]> resultList = entityManager.createNativeQuery("SELECT ccf.fecha_creacion, ccf.id_dirigido, ccf.prefil_dirigido, ccf.comentariotype, ccf.notificado, ccf.id, cc.tipoId "
					+ "FROM cens_comentario_feed as ccf  INNER JOIN  "
				+ "cens_miembros_cens as  cmc ON (cmc.id = ccf.id_dirigido AND cmc.id <> ccf.id_creador) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
				+ "INNER JOIN cens_comentario cc ON cc.id = ccf.comentariocensid "
				+ "WHERE cu.username = :username "
				+ "AND ccf.visto = false ").setParameter("username", userName).getResultList();
			if(CollectionUtils.isNotEmpty(resultList)){
				NotificacionComentarioFeed ncf =null;
				ccfList = new ArrayList<>();
				for(Object [] data : resultList){
					ncf = new NotificacionComentarioFeed();
					ncf.setFechaCreacion((Date)data[0]);
					ncf.setToId(((java.math.BigInteger)data[1]).longValue());
					ncf.setPerfilDirigido(PerfilTrabajadorCensType.getPrefilByName(data[2].toString()));
					ncf.setComentarioType(ComentarioType.valueOf(data[3].toString()));
					ncf.setNotificado(Boolean.valueOf(data[4].toString()));
					ncf.setFeedId(((java.math.BigInteger)data[5]).longValue());
					ncf.setTipoId(((java.math.BigInteger)data[6]).longValue());
					ccfList.add(ncf);
				}
			}
		}catch(Exception e){
			throw new CensException("No se pueden leer los comentarios");
		}
		return ccfList;
	}

	@Override
	public void obtenerFuenteDeComentarios(Map<ComentarioTypeComentarioIdKey, String> informationToRetrieve) {
		if(!informationToRetrieve.isEmpty()){
			Query q = null;
			Set<Entry<ComentarioTypeComentarioIdKey,String>> entrySet = informationToRetrieve.entrySet();
			for(Entry<ComentarioTypeComentarioIdKey,String> value : entrySet){
				
				switch(value.getKey().getComentarioType()){
				
				case MATERIAL:
					q = entityManager.createNativeQuery("SELECT cp.nombre, ca.nombre, cc.nombre, cc.yearcurso, cmm.nombre FROM cens_material_didactico cmm  "
							+ "INNER JOIN cens_programa cp ON cmm.programa_id = cp.id "
							+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
							+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
							+ "WHERE cmm.id = :id").setParameter("id", value.getKey().getTipoId());
					break;
				case PROGRAMA:
					q = entityManager.createNativeQuery("SELECT cp.nombre, ca.nombre, cc.nombre, cc.yearcurso FROM cens_programa cp "
							+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
							+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
							+ "WHERE cp.id = :id").setParameter("id", value.getKey().getTipoId());
					break;				
				
				}
				Object[] result = (Object[]) q.getSingleResult();
				StringBuilder sb = new StringBuilder();
				sb.append(COMENTARIO_CURSO).append(result[0]).append(COMENTARIO_SEPARATOR).
				append(COMENTARIO_CURSO_YEAR).append(result[1]).append(COMENTARIO_SEPARATOR).
				append(COMENTARIO_ASIGNATURA).append(result[2]).append(COMENTARIO_SEPARATOR).
				append(COMENTARIO_PROGRAMA).append(result[3]).append(COMENTARIO_SEPARATOR);
				if(result.length==5){
					sb.append(COMENTARIO_MATERIAL).append(result[4]);
				}
				informationToRetrieve.put(value.getKey(), sb.toString());
				
				
			}
		}
		
	}

}
