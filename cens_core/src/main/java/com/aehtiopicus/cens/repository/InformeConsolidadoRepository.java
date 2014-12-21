package com.aehtiopicus.cens.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeConsolidado;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoEstadoEnum;
import com.aehtiopicus.cens.enumeration.InformeConsolidadoTipoEnum;


@Repository
public interface InformeConsolidadoRepository extends JpaRepository<InformeConsolidado, Long> ,JpaSpecificationExecutor<InformeConsolidado> {
	
	public InformeConsolidado findByPeriodo(Date periodo);

	public InformeConsolidado findByPeriodoAndTipo(Date periodo, InformeConsolidadoTipoEnum tipo);
	
	public List<InformeConsolidado> findByEstado(InformeConsolidadoEstadoEnum estado);
}
