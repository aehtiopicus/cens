package com.aehtiopicus.cens.service;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aehtiopicus.cens.domain.Banco;
import com.aehtiopicus.cens.domain.Prepaga;
import com.aehtiopicus.cens.repository.BancoRepository;
import com.aehtiopicus.cens.specification.BancoSpecification;
import com.aehtiopicus.cens.specification.PrepagaSpecification;

@Service
@Transactional
public class BancoServiceImpl implements BancoService{

	 //private static final Logger logger = Logger.getLogger(BancoServiceImpl.class);
	 
	 @Autowired
	 protected BancoRepository bancoRepository;

	public void setBancoRepository(BancoRepository bancoRepository) {
		this.bancoRepository = bancoRepository;
	}

	@Override
	public List<Banco> getBancos() {
		return bancoRepository.findAll(new Sort("nombre"));
	}

	@Override
	public void saveBancos(List<Banco> bancos) {
		 bancoRepository.save(bancos);
		
	}

	@Override
	public Banco findById(Long bancoId) {
	
		Banco banco = null;
		if(bancoId != null){
			banco = bancoRepository.findOne(bancoId); 
		}
		return banco;
		
	}

	@Override
	public Banco findByNombre(String nombre) {
		return bancoRepository.findByNombreIgnoreCase(nombre);
	}

	@Override
	public Banco save(Banco banco) {
		return bancoRepository.save(banco);
	}

	@Override
	public List<Banco> search(String nombre, Integer page, Integer rows) {
		 Page<Banco> requestedPage = null;
		 if(page > 0){
			 page = page - 1;
		 }
		 		 
		 requestedPage = bancoRepository.findAll(getSpecification(nombre),constructPageSpecification(page,rows));
		 return requestedPage.getContent();
	}

	@Override
	public int getTotal(String nombre) {
		return (int) Math.ceil(bancoRepository.count(getSpecification(nombre)));
	}

	private Pageable constructPageSpecification(int pageIndex, int row) {
		Pageable pageSpecification = new PageRequest(pageIndex, row, sortByNameAsc());
		return pageSpecification;
	}

	private Sort sortByNameAsc() {
		return new Sort(Sort.Direction.ASC, "nombre");
	}

	private Specifications<Banco> getSpecification(String nombre) {
		Specifications<Banco> spec = null;
		if (StringUtils.isNotEmpty(nombre)) {
			spec = Specifications.where(BancoSpecification.nombreEquals(nombre));
		}
		return spec;
	}
	
	
	@Override
	public boolean deleteBanco(Long bancoId) {
		try {
			bancoRepository.delete(bancoId);
			return true;
		}catch(Exception e) {
			throw e;
		}
	}

	 
}
