package ar.com.midasconsultores.catalogodefiltros.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ar.com.midasconsultores.catalogodefiltros.domain.Filtro;
import ar.com.midasconsultores.catalogodefiltros.domain.Vehiculo;

@Transactional(propagation = Propagation.REQUIRED)
public interface VehiculoRepository  extends QueryDslPredicateExecutor<Vehiculo>, JpaRepository<Vehiculo, Long> {

	Page<Vehiculo> findByRepuestos(Filtro filtro, Pageable pageable);

	@Query(value = "SELECT DISTINCT v.modelo FROM Vehiculo v WHERE v.marca = :marca ORDER BY v.modelo")
	List<String> listModelosByMarca(@Param("marca") String marca);
	
}