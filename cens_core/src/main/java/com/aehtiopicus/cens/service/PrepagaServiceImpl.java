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

import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.repository.PrepagaRepository;
import com.aehtiopicus.cens.specification.PrepagaSpecification;

@Service
@Transactional
public class PrepagaServiceImpl implements PrepagaService{

	@Autowired
	protected PrepagaRepository prepagaRepository;

	public void setPrepagaRepository(PrepagaRepository prepagaRepository) {
		this.prepagaRepository = prepagaRepository;
	}

	@Override
	public List<Prepaga> getPrepagas() {
		return prepagaRepository.findAll(new Sort("nombre"));
	}

	@Override
	public Prepaga save(Prepaga prepaga) {
		return prepagaRepository.save(prepaga);
	}

	@Override
	public Prepaga getPrepagaByName(String nombre) {
		return prepagaRepository.findByNombreIgnoreCase(nombre);
	}

	@Override
	public List<Prepaga> search(String nombre, Integer page, Integer rows) {
		 Page<Prepaga> requestedPage = null;
		 if(page > 0){
			 page = page - 1;
		 }
		 		 
		 requestedPage = prepagaRepository.findAll(getSpecification(nombre),constructPageSpecification(page,rows));
		 return requestedPage.getContent();
	}

	@Override
	public int getTotal(String nombre) {
		return (int) Math.ceil(prepagaRepository.count(getSpecification(nombre)));
	}

	private Pageable constructPageSpecification(int pageIndex, int row) {
		Pageable pageSpecification = new PageRequest(pageIndex, row, sortByUsernameAsc());
		return pageSpecification;
	}

	private Sort sortByUsernameAsc() {
		return new Sort(Sort.Direction.ASC, "nombre");
	}

	private Specifications<Prepaga> getSpecification(String nombre) {
		Specifications<Prepaga> spec = null;
		if (StringUtils.isNotEmpty(nombre)) {
			spec = Specifications.where(PrepagaSpecification.nombreEquals(nombre));
		}
		return spec;
	}

	@Override
	public Prepaga getPrepagaById(Long prepagaId) {
		return prepagaRepository.findOne(prepagaId);
	}

	@Override
	public boolean deletePrepaga(Long prepagaId) {
		try {
			prepagaRepository.delete(prepagaId);
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

}
