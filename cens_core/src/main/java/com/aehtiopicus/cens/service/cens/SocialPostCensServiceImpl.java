package com.aehtiopicus.cens.service.cens;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.SocialPost;
import com.aehtiopicus.cens.repository.cens.SocialPostCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class SocialPostCensServiceImpl implements SocialPostCensService{

	@Autowired
	private SocialPostCensRepository repository;
	
	@Autowired
	private ProgramaCensService programaService;

	
	@Override
	public Programa findProgramaById(Long programaId) throws CensException{
		return programaService.findById(programaId);
	}
	
	@Override
	public SocialPost saveSocialPost(Programa p, String postId,String provider,String message)throws CensException{
		if(findByPrograma(p)==null){
			SocialPost sp = new SocialPost();
			sp.setPrograma(p);
			sp.setPublishEventId(postId);
			sp.setProvider(provider);
			sp.setMessage(message);
			repository.save(sp);
			return sp;
		}else{
			throw new CensException("Ya existe un post asociado");
		}
	}
	@Override
	public SocialPost findByPrograma(Programa p){
		return repository.findByPrograma(p);
	}
	
	@Override
	@Transactional
	public void deleteSocialPost(Programa p){
		repository.deleteByPrograma(p.getId());
	}
}
