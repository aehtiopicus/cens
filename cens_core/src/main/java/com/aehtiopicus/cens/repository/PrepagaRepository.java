package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Prepaga;


@Repository
public interface PrepagaRepository extends JpaRepository<Prepaga, Long> ,JpaSpecificationExecutor<Prepaga> {

	public Prepaga findByNombreIgnoreCase(String nombre);
	
		
}
