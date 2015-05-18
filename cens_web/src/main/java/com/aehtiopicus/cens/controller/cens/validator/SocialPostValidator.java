package com.aehtiopicus.cens.controller.cens.validator;

import org.springframework.stereotype.Component;

import com.aehtiopicus.cens.domain.entities.Programa;
import com.aehtiopicus.cens.enumeration.cens.EstadoRevisionType;
import com.aehtiopicus.cens.utils.CensException;

@Component
public class SocialPostValidator {

	public void validar(Programa p) throws CensException{
		if(p == null){
			throw new CensException("Imposible de publicar: programa inexistente");
		}
		
		
		if(!p.getEstadoRevisionType().equals(EstadoRevisionType.ACEPTADO)){
			throw new CensException("Imposible de publicar: programa no aceptado"); 
		}
		
	}
}
