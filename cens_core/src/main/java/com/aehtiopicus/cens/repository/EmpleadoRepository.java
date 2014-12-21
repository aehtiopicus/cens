package com.aehtiopicus.cens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aehtiopicus.cens.domain.Empleado;
import com.aehtiopicus.cens.enumeration.EstadoEmpleado;


@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> ,JpaSpecificationExecutor<Empleado> {
	
	public Empleado findById(Long id);

	@Query("from Empleado e where e.estado = ?1 order by e.apellidos, e.nombres")
	public List<Empleado> findByEstadoOrderByApellidosAndNombres(EstadoEmpleado estado);
		
	@Query(nativeQuery=true, value="select max(legajo) from empleado")
	public Integer getLastLegajo();
	
	public List<Empleado> findByEstadoAndDniOrderByIdDesc(EstadoEmpleado estado, String dni);
	
	public List<Empleado> findByDniOrderByIdDesc(String dni);
}
