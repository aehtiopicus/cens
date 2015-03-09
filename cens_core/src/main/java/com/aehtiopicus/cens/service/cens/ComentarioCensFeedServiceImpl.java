package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
import com.aehtiopicus.cens.domain.entities.NotificacionComentarioFeed;
import com.aehtiopicus.cens.enumeration.cens.ComentarioType;
import com.aehtiopicus.cens.enumeration.cens.PerfilTrabajadorCensType;
import com.aehtiopicus.cens.repository.cens.ComentarioCensFeedRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ComentarioCensFeedServiceImpl implements ComentarioCensFeedService{
	
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
			List<Object[]> resultList = entityManager.createNativeQuery("SELECT ccf.fecha_creacion, ccf.id_dirigido, ccf.prefil_dirigido, ccf.comentariotype, ccf.notificado FROM cens_comentario_feed as ccf  INNER JOIN  "
				+ "cens_miembros_cens as  cmc ON (cmc.id = ccf.id_dirigido AND cmc.id <> ccf.id_creador) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
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
					ncf.setNotificado(Boolean.valueOf(data[3].toString()));
					ccfList.add(ncf);
				}
			}
		}catch(Exception e){
			throw new CensException("No se pueden leer los comentarios");
		}
		return ccfList;
	}

}
