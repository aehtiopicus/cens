package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.ComentarioCens;
import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.ComentarioTypeComentarioIdKey;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ComentarioCensFeedRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
@Scope(value=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ComentarioCensFeedServiceImpl implements ComentarioCensFeedService{
		
	private static final Logger logger = LoggerFactory.getLogger(ComentarioCensFeedServiceImpl.class);
	@Autowired
	private ComentarioCensFeedRepository repository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	private static final String MAX_DAYS_NOT_SEEN = "#{notificacionProperties['max_no_notificado']}";
	
	@Value(MAX_DAYS_NOT_SEEN)
	private int days;
	
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
	@Cacheable(value="commentSource", key="#ctik.toString()")
	public Map<String,String> getCommentSource(ComentarioTypeComentarioIdKey ctik) throws CensException {
		try{
			Query q = null;
			switch(ctik.getComentarioType()){		
			case MATERIAL:
				q = entityManager.createNativeQuery("SELECT cp.nombre as pnombre, ca.nombre as canombre, cc.nombre as ccnombre, cc.yearcurso, "
						+ "cp.id as cpid, ca.id as caid, cc.id as ccid "
						+ ",cmm.nombre as cmmnombre, cmm.id as cmmid "
						+ "FROM cens_material_didactico cmm  "
						+ "INNER JOIN cens_programa cp ON cmm.programa_id = cp.id "
						+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
						+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
						+ "WHERE cmm.id = :id").setParameter("id", ctik.getTipoId());
				break;
			case PROGRAMA:
				q = entityManager.createNativeQuery("SELECT cp.nombre as pnombre, ca.nombre as canombre, cc.nombre as ccnombre, cc.yearcurso,  "
						+ "cp.id as cpid, ca.id as caid, cc.id as ccid "
						+ "FROM cens_programa cp "
						+ "INNER JOIN cens_asignatura ca ON cp.asignatura_id = ca.id "
						+ "INNER JOIN cens_curso cc on cc.id = ca.curso_id "
						+ "WHERE cp.id = :id").setParameter("id", ctik.getTipoId());
				break;				
		
			}
			Object[] result = (Object[]) q.getSingleResult();
		
			Map<String,String> resultMap = new HashMap<>();
			resultMap.put(CensServiceConstant.COMENTARIO_PROGRAMA, result[0].toString());
			resultMap.put(CensServiceConstant.COMENTARIO_PROGRAMA_ID, result[4].toString());
			
			resultMap.put(CensServiceConstant.COMENTARIO_ASIGNATURA, result[1].toString());
			resultMap.put(CensServiceConstant.COMENTARIO_ASIGNATURA_ID, result[5].toString());
			
			resultMap.put(CensServiceConstant.COMENTARIO_CURSO, result[2].toString());
			resultMap.put(CensServiceConstant.COMENTARIO_CURSO_ID, result[6].toString());
			
			resultMap.put(CensServiceConstant.COMENTARIO_CURSO_YEAR, result[3].toString());
			
			
														
			if(result.length==9){	
				resultMap.put(CensServiceConstant.COMENTARIO_MATERIAL, result[7].toString());
				resultMap.put(CensServiceConstant.COMENTARIO_MATERIAL_ID, result[8].toString());
			}
			return resultMap;
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
			entityManager.createNativeQuery("UPDATE cens_comentario_feed   SET notificado = true, ultima_notificacion = :ultima_notificacion "
					+ "WHERE id_dirigido in  "
					+ "(SELECT cmc.id from cens_miembros_cens cmc INNER JOIN cens_usuarios as cu ON cmc.usuario_id = cu.id WHERE cu.username = :username) "
					+ "AND id_dirigido <> id_creador and ultima_notificacion is null ").setParameter("username", username).setParameter("ultima_notificacion", new java.util.Date()).executeUpdate();
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

	@Override
	@Transactional(rollbackFor={CensException.class})
	public void deleteAllComentarios(List<Long> list) throws CensException {
		try{
			Query q = entityManager.createNativeQuery("DELETE FROM cens_comentario_feed WHERE comentariocensid = :id");
			for(Long id : list){
				q.setParameter("id", id).executeUpdate();
			}
		}catch(Exception e){
			throw new CensException("Error capturado al eliminar ",e);
		}
		
	}

	@Override
	@Transactional
	public void markAllFeedsFromCommentsAsRead(Long id,
			List<ComentarioCens> comentarioList)  throws CensException{
		try{
			StringBuilder sb = new StringBuilder();
			for(ComentarioCens cc : comentarioList){
				sb.append(cc.getId()).append(",");
			}
			String result = sb.toString().substring(0,sb.length()-1);
			entityManager.createNativeQuery("UPDATE cens_comentario_feed SET visto = true WHERE id_dirigido = :idMiembroCens "
				+ "AND comentariocensid in ("+result+")").setParameter("idMiembroCens", id).executeUpdate();
		}catch(Exception e){
			throw new  CensException("Error al modificar los feeds");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificacionComentarioFeed> getUnReadFeeds() throws CensException {
		List<NotificacionComentarioFeed> ccfList = null;
		try{	
			List<Object[]> resultList = entityManager.createNativeQuery("SELECT ccf.fecha_creacion, ccf.id_dirigido, ccf.prefil_dirigido, ccf.comentariotype, ccf.notificado, ccf.id, cc.tipoId,trim(replace((replace(concat((CURRENT_DATE - (ultima_notificacion + INTERVAL '7 days'))),'days','')),'day',''))  "
					+ "FROM cens_comentario_feed as ccf  INNER JOIN  "
				+ "cens_miembros_cens as  cmc ON (cmc.id = ccf.id_dirigido AND cmc.id <> ccf.id_creador) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
				+ "INNER JOIN cens_comentario cc ON cc.id = ccf.comentariocensid "
				+ "WHERE ccf.visto = false "
				+ "AND ccf.ultima_notificacion is not null "
				+ "AND ccf.ultima_notificacion + INTERVAL "+"'"+days+" days' <= CURRENT_DATE").getResultList();
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
					ncf.setDaysAgo(Long.parseLong(data[7].toString()));
					ccfList.add(ncf);
				}
			}
		}catch(Exception e){
			throw new CensException("No se pueden leer los comentarios");
		}
		return ccfList;
	}

}
