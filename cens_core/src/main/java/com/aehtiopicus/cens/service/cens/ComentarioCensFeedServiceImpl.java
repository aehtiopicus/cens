package com.aehtiopicus.cens.service.cens;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.ComentarioCensFeed;
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
	public List<ComentarioCensFeed> getGeneratedFeeds(String userName) throws CensException{
		List<ComentarioCensFeed> ccfList = null;
		try{
		 ccfList = entityManager.createNativeQuery("SELECT ccf.* FROM cens_comentario_feed as ccf  INNER JOIN  "
				+ "cens_miembro_cens as  cmc ON (cmc.id = ccf.toId AND cmc.id <> ccf.fromId) "
				+ "INNER JOIN cens_usuarios as cu ON cu.id = cmc.usuario_id  "
				+ "WHERE cu.username = :username "
				+ "AND ccf.visto = false ",ComentarioCensFeed.class).setParameter("username", userName).getResultList();
		}catch(Exception e){
			throw new CensException("No se pueden leer los comentarios");
		}
		return ccfList;
	}

}
