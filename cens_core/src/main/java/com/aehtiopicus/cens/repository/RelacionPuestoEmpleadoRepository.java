package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.RelacionLaboral;
import com.aehtiopicus.cens.domain.RelacionPuestoEmpleado;


@Repository
public interface RelacionPuestoEmpleadoRepository extends JpaRepository<RelacionPuestoEmpleado, Long> ,JpaSpecificationExecutor<RelacionPuestoEmpleado> {

	void findByRelacionLaboral(RelacionLaboral relacion);
	
	

	
		
}
