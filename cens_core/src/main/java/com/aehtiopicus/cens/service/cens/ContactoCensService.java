package com.aehtiopicus.cens.service.cens;

import java.util.List;

import com.aehtiopicus.cens.domain.entities.Contacto;
import com.aehtiopicus.cens.utils.CensException;

public interface ContactoCensService {

	public List<Contacto> save(List<Contacto> contacto) throws CensException;

	public List<Contacto> getContactos(Long miembroCensId);

	public Contacto getContacto(Long contactoId) throws CensException;

	public void deleteContacto(Long contactoId) throws CensException;

}
