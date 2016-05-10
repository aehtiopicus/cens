package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.NotificacionTiempoEdicionFeed;
import com.aehtiopicus.cens.domain.entities.TiempoEdicion;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class TiempoEdicionCensFeedServiceImpl implements TiempoEdicionCensFeedService{

	@PersistenceContext
	private EntityManager entityManager;
	
	private static final String FEEDS_EMAIL = "SELECT ctev.fecha_vencido, ctev.to_id, ctev.prefil_dirigido,ctev.comentario_type, ctev.resulto, ctev.id, ctev.tipo_id, ctev.estado_revision_type " 
		+" FROM cens_tiempo_edicion_vencido as ctev  INNER JOIN "  
		+" cens_miembros_cens as  cmc ON (cmc.id = ctev.to_id) "
		+" INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id "  
		+" WHERE cu.username = :username "
		+" AND ctev.cancelado = false "
		+" AND ctev.resulto = false";
	
	private static final String FEEDS_APP = "SELECT ctev.fecha_vencido, ctev.to_id, ctev.prefil_dirigido,ctev.comentario_type, ctev.resulto, ctev.id, ctev.tipo_id, ctev.estado_revision_type " 
		+" FROM cens_tiempo_edicion_vencido as ctev  INNER JOIN "  
		+" cens_miembros_cens as  cmc ON (cmc.id = ctev.to_id) "
		+" INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id "  
		+" WHERE cu.username = :username "
		+" AND ctev.cancelado = false ";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificacionTiempoEdicionFeed> getGeneratedFeeds(String username, boolean email) throws CensException {
		List<NotificacionTiempoEdicionFeed> ccfList = null;
		try{	
			List<Object[]> resultList = entityManager.createNativeQuery(email ? FEEDS_EMAIL : FEEDS_APP).setParameter("username", username).getResultList();
			if(CollectionUtils.isNotEmpty(resultList)){
				NotificacionTiempoEdicionFeed ncf =null;
				ccfList = new ArrayList<>();
				for(Object [] data : resultList){
					ncf = new NotificacionTiempoEdicionFeed();
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
	public List<NotificacionTiempoEdicionFeed> getUnReadFeeds() throws CensException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int markCommentsAsIgnored(Long tipoId, ComentarioType tipoType) throws CensException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TiempoEdicion save(TiempoEdicion mValue) throws CensException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markAllFeedsForUserAsNotified(String username) throws CensException {
		// TODO Auto-generated method stub
		
	}

}
