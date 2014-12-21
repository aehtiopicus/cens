package com.aehtiopicus.cens.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeConsolidadoStub;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;


@Repository
public interface InformeConsolidadoStubRepository extends JpaRepository<InformeConsolidadoStub, Long> ,JpaSpecificationExecutor<InformeConsolidadoStub> {
	
	public InformeConsolidadoStub findByPeriodo(Date periodo);

	public InformeConsolidadoStub findByPeriodoAndTipo(Date periodo, InformeConsolidadoTipoEnum tipo);
	
}
