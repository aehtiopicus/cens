package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Nacionalidad;


@Repository
public interface NacionalidadRepository extends JpaRepository<Nacionalidad, Long> ,JpaSpecificationExecutor<Nacionalidad> {

	public Nacionalidad findByNombreIgnoreCase(String nombre);
	
}
