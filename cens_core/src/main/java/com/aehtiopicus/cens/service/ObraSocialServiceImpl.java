package com.aehtiopicus.cens.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.ObraSocial;
import com.aehtiopicus.cens.enumeration.EstadoClienteEnum;
import com.aehtiopicus.cens.repository.ObraSocialRepository;
import com.aehtiopicus.cens.specification.ClienteSpecification;
import com.aehtiopicus.cens.specification.ObraSocialSpecification;

@Service
@Transactional
public class ObraSocialServiceImpl implements ObraSocialService{

	 
	@Autowired
	protected ObraSocialRepository obraSocialRepository;

	 
	public void setObraSocialRepository(ObraSocialRepository obraSocialRepository) {
		this.obraSocialRepository = obraSocialRepository;
	}


	@Override
	public List<ObraSocial> getObrasSociales() {
		return obraSocialRepository.findAll(new Sort("nombre"));
	}


	@Override
	public ObraSocial saveObraSocial(ObraSocial obraSocial) {
		return obraSocialRepository.saveAndFlush(obraSocial);
	}

	@Override
	public ObraSocial getObraSocialByNombre(String nombre) {
		return obraSocialRepository.findByNombreIgnoreCase(nombre);
	}


	@Override
	public ObraSocial getObraSocialById(Long obraSocialId) {
		return obraSocialRepository.findOne(obraSocialId);
	}
	
	@Override
	public List<ObraSocial> search(String nombre, Integer page, Integer rows) {
		 Page<ObraSocial> requestedPage = null;
		 if(page > 0){
			 page = page - 1;
		 }
		 		 
		 requestedPage = obraSocialRepository.findAll(getSpecification(nombre),constructPageSpecification(page,rows));
		 return requestedPage.getContent();
	}

	private Pageable constructPageSpecification(int pageIndex, int row) {
		Pageable pageSpecification = new PageRequest(pageIndex, row, sortByUsernameAsc());
		return pageSpecification;
	}
	private Sort sortByUsernameAsc() {
		return new Sort(Sort.Direction.ASC, "nombre");
	}
	private Specifications<ObraSocial> getSpecification(String nombre){
		 Specifications<ObraSocial> spec = null;
		 if(StringUtils.isNotEmpty(nombre)){
			 spec = Specifications.where(ObraSocialSpecification.nombreEquals(nombre));
		 }
		 return spec;
	}
	
	@Override
	public int getTotal(String nombre) {
		return (int) Math.ceil(obraSocialRepository.count(getSpecification(nombre)));
	}


	@Override
	public boolean deleteObraSocial(Long obraSocialId) {
		try {
			obraSocialRepository.delete(obraSocialId);
			return true;
		}catch(Exception e) {
			//logger.info(e.getMessage());
			throw e;
		}
	}
	
}
