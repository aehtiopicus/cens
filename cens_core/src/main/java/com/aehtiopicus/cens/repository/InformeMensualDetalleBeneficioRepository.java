package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeMensualDetalleBeneficio;


@Repository
public interface InformeMensualDetalleBeneficioRepository extends JpaRepository<InformeMensualDetalleBeneficio, Long> ,JpaSpecificationExecutor<InformeMensualDetalleBeneficio> {
	

	
}
