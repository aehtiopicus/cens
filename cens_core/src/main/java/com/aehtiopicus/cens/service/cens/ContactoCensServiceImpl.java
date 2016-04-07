package com.aehtiopicus.cens.service.cens;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aehtiopicus.cens.domain.entities.Contacto;
import com.aehtiopicus.cens.repository.cens.ContactoCensRepository;
import com.aehtiopicus.cens.utils.CensException;

@Service
public class ContactoCensServiceImpl implements ContactoCensService{

	@Autowired
	private ContactoCensRepository repository;
	
	@Autowired
	private MiembroCensService miembroCensService;
	
	private static final Logger logger = LoggerFactory.getLogger(ContactoCensServiceImpl.class);

	@Override
	public List<Contacto> save(List<Contacto> contactoList) throws CensException {
		if (CollectionUtils.isNotEmpty(contactoList)) {
			List<Contacto> contactoListResult = new ArrayList<Contacto>();
			for(Contacto  contacto:contactoList){
				logger.info("Guardando contacto");
				contacto.setMiembroCens(miembroCensService.getMiembroCens(contacto.getMiembroCens().getId()));								
				contactoListResult.add(repository.save(contacto));
			}
			return contactoListResult;
		} else {
			throw new CensException("El contacto no puede ser nulo");
		}
	}
	
	@Override
	public List<Contacto> getContactos(Long miembroCensId){
		return repository.findContactoByMiembro(miembroCensId);
	}
	
	@Override
	public Contacto getContacto(Long contactoId) throws CensException{
		Contacto c = repository.findOne(contactoId);
		if(c==null){
			throw new CensException("El contacto no existe");
		}
		return c;
	}
	
	@Override
	public void deleteContacto(Long contactoId) throws CensException{
		Contacto c =getContacto(contactoId);
		repository.delete(c);
	}
}
