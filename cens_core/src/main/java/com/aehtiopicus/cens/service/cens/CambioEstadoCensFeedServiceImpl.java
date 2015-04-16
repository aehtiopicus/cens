package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.CambioEstadoCensFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionCambioEstadoFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.CambioEstadoCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class CambioEstadoCensFeedServiceImpl implements CambioEstadoCensFeedService{

	
	private static final Logger logger = LoggerFactory.getLogger(ComentarioCensFeedServiceImpl.class);
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private  CambioEstadoCensRepository cambioEstadoCensRepository;
	
	private static final String MAX_DAYS_NOT_SEEN = "#{notificacionProperties['max_no_notificado']}";
	
	@Value(MAX_DAYS_NOT_SEEN)
	private int days;
	
	
	@Override
	public CambioEstadoCensFeed save(CambioEstadoCensFeed aux)
			throws CensException {
		return cambioEstadoCensRepository.save(aux);		
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<NotificacionCambioEstadoFeed> getGeneratedFeeds(String userName)throws CensException {
		List<NotificacionCambioEstadoFeed> ccfList = null;
		try{	
			List<Object[]> resultList = entityManager.createNativeQuery("SELECT ccf.fecha_creacion, ccf.id_dirigido, ccf.prefil_dirigido, ccf.comentarioType, ccf.notificado, ccf.id, ccf.tipoId, ccf.estadorevisiontype "
					+ "FROM cens_cambio_estado_feed as ccf  INNER JOIN  "
				+ "cens_miembros_cens as  cmc ON (cmc.id = ccf.id_dirigido AND cmc.id <> ccf.id_creador) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
				+ "WHERE cu.username = :username "
				+ "AND ccf.visto = false ").setParameter("username", userName).getResultList();
			if(CollectionUtils.isNotEmpty(resultList)){
				NotificacionCambioEstadoFeed ncf =null;
				ccfList = new ArrayList<>();
				for(Object [] data : resultList){
					ncf = new NotificacionCambioEstadoFeed();
					ncf.setFechaCreacion((Date)data[0]);
					ncf.setToId(((java.math.BigInteger)data[1]).longValue());
					ncf.setPerfilDirigido(PerfilTrabajadorCensType.getPrefilByName(data[2].toString()));
					ncf.setComentarioType(ComentarioType.valueOf(data[3].toString()));
					ncf.setNotificado(Boolean.valueOf(data[4].toString()));
					ncf.setFeedId(((java.math.BigInteger)data[5]).longValue());
					ncf.setTipoId(((java.math.BigInteger)data[6]).longValue());
					ncf.setEstadoRevisionType(EstadoRevisionType.valueOf(data[7].toString()));
					ccfList.add(ncf);
				}
			}
		}catch(Exception e){
			throw new CensException("No se pueden leer los cambios de estados del material y/o programa");
		}
		return ccfList;
	}


	@Override
	@Transactional
	public void markAllFeedsForUserAsNotified(String username)
			throws CensException {
		try{
			entityManager.createNativeQuery("UPDATE cens_cambio_estado_feed   SET notificado = true, ultima_notificacion = :ultima_notificacion "
					+ "WHERE id_dirigido in  "
					+ "(SELECT cmc.id from cens_miembros_cens cmc INNER JOIN cens_usuarios as cu ON cmc.usuario_id = cu.id WHERE cu.username = :username) "
					+ "AND id_dirigido <> id_creador and ultima_notificacion is null ").setParameter("username", username).setParameter("ultima_notificacion", new java.util.Date()).executeUpdate();
		}catch(Exception e){
			logger.error("Error ",e);
			throw new CensException("Error al actualizar el estado de los feeds");
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<NotificacionCambioEstadoFeed> getUnReadFeeds()
			throws CensException {
		List<NotificacionCambioEstadoFeed> ccfList = null;
		try{	
			List<Object[]> resultList = entityManager.createNativeQuery("SELECT ccf.fecha_creacion, ccf.id_dirigido, ccf.prefil_dirigido, ccf.comentariotype, ccf.notificado, ccf.id, ccf.tipoId,trim(replace((replace(concat((CURRENT_DATE - (ultima_notificacion + INTERVAL '7 days'))),'days','')),'day','')) , ccf.ultima_notificacion,ccf.estadorevisiontype "
					+ "FROM cens_cambio_estado_feed as ccf  INNER JOIN  "
				+ "cens_miembros_cens as  cmc ON (cmc.id = ccf.id_dirigido AND cmc.id <> ccf.id_creador) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
				+ "WHERE ccf.visto = false "
				+ "AND ccf.ultima_notificacion is not null AND ccf.notificado = true "
				+ "AND ccf.ignorado = false "
				+ "AND ccf.ultima_notificacion + INTERVAL "+"'"+days+" days' < CURRENT_DATE").getResultList();
			if(CollectionUtils.isNotEmpty(resultList)){
				NotificacionCambioEstadoFeed ncf =null;
				ccfList = new ArrayList<>();
				for(Object [] data : resultList){
					ncf = new NotificacionCambioEstadoFeed();
					ncf.setFechaCreacion((Date)data[0]);
					ncf.setToId(((java.math.BigInteger)data[1]).longValue());
					ncf.setPerfilDirigido(PerfilTrabajadorCensType.getPrefilByName(data[2].toString()));
					ncf.setComentarioType(ComentarioType.valueOf(data[3].toString()));
					ncf.setNotificado(Boolean.valueOf(data[4].toString()));
					ncf.setFeedId(((java.math.BigInteger)data[5]).longValue());
					ncf.setTipoId(((java.math.BigInteger)data[6]).longValue());
					ncf.setDaysAgo(Long.parseLong(data[7].toString()));
					ncf.setFechaNotificacion((Date)data[8]);
					ncf.setEstadoRevisionType(EstadoRevisionType.valueOf(data[9].toString()));
					ccfList.add(ncf);
				}
			}
		}catch(Exception e){
			throw new CensException("No se pueden leer los comentarios");
		}
		return ccfList;
	}


	@Override
	@Transactional
	public void markCambioEstadoFeedAsRead(Long ctId, Long miembroId,
			ComentarioType ct)throws CensException {
		try{
			cambioEstadoCensRepository.markCambioEstadoFeedAsRead(ctId,miembroId,ct);
		}catch(Exception e){
			throw new CensException("Error al marcar cambio de estado de "+ct.name() +" como visto");
		}
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllKeys(Long tipoId, Long miembroId,
			ComentarioType ct) {
	
		
		return entityManager.createNativeQuery("select concat(ccef.comentariotype,trim(to_char(ccef.tipoid,'99999999')),ccef.fecha_creacion,'ACTIVIDAD',ccef.id_dirigido) "
				+ "FROM cens_cambio_estado_feed ccef "+
				"WHERE ccef.tipoid = :tipoId AND ccef.id_dirigido = :miembroId AND ccef.comentariotype = :ct ").
				setParameter("tipoId", tipoId).
				setParameter("miembroId", miembroId).
				setParameter("ct", ct.toString()).getResultList();
				
	}


	@Override
	@Transactional
	public int markCommetnsAsIgnored(Long tipoId, ComentarioType tipoType)
			throws CensException {
		try{
			return entityManager.createNativeQuery("update cens_cambio_estado_feed  SET ignorado = true WHERE tipoid  = :tipoid AND comentariotype = :tipotype").
					setParameter("tipoid", tipoId).
					setParameter("tipotype", tipoType.name()).executeUpdate();
		}catch(Exception e){
			throw new CensException("Error al marcar cambio de estado como ignorados",e);
		}
		
	}

}
