package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Puesto;


@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> ,JpaSpecificationExecutor<Puesto> {

	Puesto findByNombreIgnoreCase(String string);
	
		
}
