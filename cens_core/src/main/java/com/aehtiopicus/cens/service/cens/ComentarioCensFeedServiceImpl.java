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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
	
	public static final String COMENTARIO_CURSO ="Curso";
	public static final String COMENTARIO_CURSO_YEAR ="Year";
	public static final String COMENTARIO_ASIGNATURA ="Asignatura";
	public static final String COMENTARIO_PROGRAMA ="Programa";
	public static final String COMENTARIO_MATERIAL ="Material";
	public static final String COMENTARIO_SEPARATOR =",";
	
	private static final Logger logger = LoggerFactory.getLogger(ComentarioCensFeedServiceImpl.class);
	@Autowired
	private ComentarioCensFeedRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	@Override
	@Transactional(rollbackFor={CensException.class,Exception.class})
	public ComentarioCensFeed save(ComentarioCensFeed comentarioFeed){
		 comentarioFeed = repository.save(comentarioFeed);
		 return comentarioFeed;
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
	public void obtenerFuenteDeComentarios(Map<ComentarioTypeComentarioIdKey, String> informationToRetrieve) throws CensException{
		if(!informationToRetrieve.isEmpty()) {
			
			Set<Entry<ComentarioTypeComentarioIdKey,String>> entrySet = informationToRetrieve.entrySet();
			for(Entry<ComentarioTypeComentarioIdKey,String> value : entrySet){								
				informationToRetrieve.put(value.getKey(), getCommentSource(value.getKey()));				
				
			}
		}
		
	}
	
	@Cacheable(value="commentSource",key="#ctik.comentarioType#ctick.tipoId")
	private String getCommentSource(ComentarioTypeComentarioIdKey ctik) throws CensException {
		try{
			Query q = null;
			switch(ctik.getComentarioType()){		
			case MATERIAL:
				q = entityManager.createNativeQuery("SELECT cp.nombre as pnombre, ca.nombre as canombre, cc.nombre as ccnombre, cc.yearcurso, cmm.nombre as cmmnombre FROM cens_material_didactico cmm  "
						+ "INNER JOIN cens_programa cp ON cmm.programa_id = cp.id "
						+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
						+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
						+ "WHERE cmm.id = :id").setParameter("id", ctik.getTipoId());
				break;
			case PROGRAMA:
				q = entityManager.createNativeQuery("SELECT cp.nombre as pnombre, ca.nombre as canombre, cc.nombre as ccnombre, cc.yearcurso FROM cens_programa cp "
						+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
						+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
						+ "WHERE cp.id = :id").setParameter("id", ctik.getTipoId());
				break;				
		
			}
			Object[] result = (Object[]) q.getSingleResult();
			StringBuilder sb = new StringBuilder();
			sb.append(COMENTARIO_PROGRAMA).append(COMENTARIO_SEPARATOR).append(result[0]).append(COMENTARIO_SEPARATOR).
			append(COMENTARIO_ASIGNATURA).append(COMENTARIO_SEPARATOR).append(result[1]).append(COMENTARIO_SEPARATOR).
			append(COMENTARIO_CURSO).append(COMENTARIO_SEPARATOR).append(result[2]).append(COMENTARIO_SEPARATOR).
			append(COMENTARIO_CURSO_YEAR).append(COMENTARIO_SEPARATOR).append(result[3]);
			if(result.length==5){	
				sb.append(COMENTARIO_SEPARATOR).append(COMENTARIO_MATERIAL).append(COMENTARIO_SEPARATOR).append(result[4]);
			}
			return sb.toString();
		}catch(Exception e){
				logger.error("Error ",e);
				throw new CensException ("No se puede extraer la información de notificaci&oacute;n de comentarios");
		}
	}

	@Override
	@Transactional
	public void markAllFeedsForUserAsNotified(String username)
			throws CensException {
		try{
			entityManager.createNativeQuery("UPDATE cens_comentario_feed   SET notificado =true "
					+ "WHERE id_dirigido in  "
					+ "(SELECT cmc.id from cens_miembros_cens cmc INNER JOIN cens_usuarios as cu ON cmc.usuario_id = cu.id WHERE cu.username = :username) "
					+ "AND id_dirigido <> id_creador").setParameter("username", username).executeUpdate();
		}catch(Exception e){
			logger.error("Error ",e);
			throw new CensException("Error al actualizar el estado de los feeds");
		}
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Long> getAsesoresIdExcludingCaller(Long fromId) {
		List<Long> miembroAsesorId = new ArrayList<>();
		List<Object []> objList = entityManager.createNativeQuery("SELECT cmc.id FROM cens_miembros_cens as cmc INNER JOIN cens_usuarios as cu ON cmc.usuario_id = cu.id "
				+ "INNER JOIN cens_perfil_usuario_cens as cpuc  ON cpuc.usuario_id = cu.id "
				+ "WHERE cmc.id <> :fromId AND cpuc.perfiltype = 'ROLE_ASESOR' ").setParameter("fromId", fromId).getResultList();
		if(CollectionUtils.isNotEmpty(objList)){
			for(Object [] obj : objList){
				miembroAsesorId.add(((java.math.BigInteger)obj[0]).longValue());
			}
		}
		return miembroAsesorId;
	}

}
