package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Banco;


@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> ,JpaSpecificationExecutor<Banco> {

	public Banco findByNombreIgnoreCase(String nombre);
	
		
}
