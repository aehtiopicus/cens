package com.aehtiopicus.cens.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.InformeMensual;
import com.aehtiopicus.cens.enumeration.InformeMensualEstadoEnum;


@Repository
public interface InformeMensualRepository extends JpaRepository<InformeMensual, Long> ,JpaSpecificationExecutor<InformeMensual> {
	
	public InformeMensual findByClienteAndPeriodo(Cliente cliente, Date periodo);
	
	public List<InformeMensual> findByPeriodoAndEstado(Date periodo, InformeMensualEstadoEnum estado);
	
	public List<InformeMensual> findByClienteOrderByPeriodoDesc(Cliente cliente, Pageable page);
	
	public List<InformeMensual> findByClienteAndEstado(Cliente cliente, InformeMensualEstadoEnum estado);
	
	public InformeMensual findByClienteAndPeriodoAndEstado(Cliente cliente, Date periodo, InformeMensualEstadoEnum estado);
}
