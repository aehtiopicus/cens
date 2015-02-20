package com.aehtiopicus.cens.service.cens;

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
	
	@Override
	@Transactional(rollbackFor={CensException.class,Exception.class})
	public void save(ComentarioCensFeed comentarioFeed){
		repository.save(comentarioFeed);
	}

}
