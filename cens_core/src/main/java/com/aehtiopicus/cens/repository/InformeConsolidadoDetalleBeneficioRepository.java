package com.aehtiopicus.cens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeConsolidadoDetalleBeneficio;


@Repository
public interface InformeConsolidadoDetalleBeneficioRepository extends JpaRepository<InformeConsolidadoDetalleBeneficio, Long> ,JpaSpecificationExecutor<InformeConsolidadoDetalleBeneficio> {
	

}
