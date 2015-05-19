package com.aehtiopicus.cens.service.cens;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.domain.entities.SocialPost;
import com.aehtiopicus.cens.utils.CensException;

public interface SocialPostCensService {

	public Programa findProgramaById(Long programaId) throws CensException;

	public SocialPost saveSocialPost(Programa p, String postId,String provider,String message) throws CensException;

	public SocialPost findByPrograma(Programa p);

	public void deleteSocialPost(Programa p);

}
