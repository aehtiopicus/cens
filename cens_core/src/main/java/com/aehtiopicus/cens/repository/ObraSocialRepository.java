package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.ObraSocial;


@Repository
public interface ObraSocialRepository extends JpaRepository<ObraSocial, Long> ,JpaSpecificationExecutor<ObraSocial> {

	ObraSocial findByNombreIgnoreCase(String nombre);
	
	//List<ObraSocial> findByNombreContainingIgnoreCase(String nombre);	
}
