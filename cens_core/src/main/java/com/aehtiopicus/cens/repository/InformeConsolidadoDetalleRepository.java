package com.aehtiopicus.cens.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.InformeConsolidadoDetalle;


@Repository
public interface InformeConsolidadoDetalleRepository extends JpaRepository<InformeConsolidadoDetalle, Long> ,JpaSpecificationExecutor<InformeConsolidadoDetalle> {
	
	@Query("select det from InformeConsolidadoDetalle det  WHERE det.periodo = ?1 and det.informeMensualDetalle.relacionLaboral.cliente.id = ?2")
	public List<InformeConsolidadoDetalle> findByClienteAndPeriodo(Date periodo, Long id);
	
	@Query("select det from InformeConsolidadoDetalle det  WHERE det.periodo = ?1 and det.informeMensualDetalle.relacionLaboral.empleado.banco.nombre <> ?2")
	public List<InformeConsolidadoDetalle> findByPeriodoAndClienteDistintoGalicia(Date periodo, String banco);
	
	@Query(
		value= "select icd.* from informeconsolidadodetalle as icd " + 
			"inner join informeconsolidado_informeconsolidadodetalle as icicd on icicd.detalle_id = icd.id " + 
			"inner join informeconsolidado as ic on ic.id = icicd.informeconsolidado_id " + 
			"left join informemensualdetalle as imd on imd.id = informemensualdetalle_id " + 
			"left join relacionlaboral as rl on rl.id = relacionlaboral_id " + 
			"where (icd.empleado_id = ?1 or rl.empleado_id = ?1) and ic.estado = 'BORRADOR'", 
		nativeQuery=true)
	public List<InformeConsolidadoDetalle> obtenerDetallesAbiertosPorEmpleado(Long empleadoId);
}
