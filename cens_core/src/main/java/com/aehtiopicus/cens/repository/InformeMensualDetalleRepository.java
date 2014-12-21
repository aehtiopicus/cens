package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeMensualDetalle;


@Repository
public interface InformeMensualDetalleRepository extends JpaRepository<InformeMensualDetalle, Long> ,JpaSpecificationExecutor<InformeMensualDetalle> {
	

	
}
