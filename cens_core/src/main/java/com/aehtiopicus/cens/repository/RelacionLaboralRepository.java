package com.aehtiopicus.cens.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Cliente;
import com.aehtiopicus.cens.domain.RelacionLaboral;


@Repository
public interface RelacionLaboralRepository extends JpaRepository<RelacionLaboral, Long> ,JpaSpecificationExecutor<RelacionLaboral> {
	
	
	@Query("select rl from RelacionLaboral rl where rl.cliente = ?1 and (rl.fechaInicio < ?2) and (rl.fechaFin is null or rl.fechaFin >= ?3) order by rl.empleado.apellidos asc, rl.empleado.nombres asc")
	public List<RelacionLaboral> getRelacionesLaboralesByClienteAndPeriodo(Cliente cliente, Date proximoPeriodo, Date periodo);
	
	public List<RelacionLaboral> findByClienteAndFechaFinIsNull(Cliente cliente);
}
